package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.jae.spacedout.game.components.BulletComponent;
import com.jae.spacedout.game.components.DataComponent;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.game.components.VisualComponent;
import com.jae.spacedout.game.components.WeaponComponent;
import com.jae.spacedout.utility.Assets;
import com.jae.spacedout.utility.Utils;

import java.util.Random;

public class WeaponSystem extends IteratingSystem
{
    private Assets assets;
    private Random random;

    private WeaponComponent weapon;
    private TransformComponent transform;

    public WeaponSystem(Assets assets, Random random, int priority)
    {
        super(Family.all(WeaponComponent.class).get(), priority);

        this.assets = assets;
        this.random = random;
    }

    @Override
    public void processEntity(Entity entity, float dt)
    {
        this.weapon = Mappers.weapon.get(entity);
        this.transform = Mappers.transform.get(entity);

        for(int i = 0; i < this.weapon.stats.length; i++)
        {
            if(this.weapon.stats[i] != null)
            {
                if(this.weapon.shouldShoot[i] && this.weapon.shotTimer[i] > (1f / this.weapon.stats[i].maxRoundsPerSecond))
                {
                    this.weapon.engine.addEntity(this.getBullet(i));

                    this.weapon.shotTimer[i] = 0;
                }

                this.weapon.shouldShoot[i] = false;
                this.weapon.shotTimer[i] += dt;
            }
        }
    }

    private Entity getBullet(int i)
    {
        //create bullet component
        BulletComponent bulletComponent = this.weapon.engine.createComponent(BulletComponent.class);
        bulletComponent.distaceTraveled = 0;
        bulletComponent.owner = this.weapon.owner;
        bulletComponent.stats = this.weapon.stats[i];

        //create transform component
        TransformComponent transformComponent = this.weapon.engine.createComponent(TransformComponent.class);
        float offsetX = this.weapon.offset[i].x * (float)Math.cos(Math.toRadians(this.transform.rotation)) -
                this.weapon.offset[i].y * (float)Math.sin(Math.toRadians(this.transform.rotation));
        float offsetY = this.weapon.offset[i].x * (float)Math.sin(Math.toRadians(this.transform.rotation)) +
                this.weapon.offset[i].y * (float)Math.cos(Math.toRadians(this.transform.rotation));
        transformComponent.x = this.transform.x + offsetX;
        transformComponent.y = this.transform.y + offsetY;
        //rotation will include target leading and accuracy
        transformComponent.rotation = this.transform.rotation + this.getRotationModifier(i) +
                Utils.mapRange(0f, 1f, -this.weapon.stats[i].accuracy, this.weapon.stats[i].accuracy, this.random.nextFloat());

        //create visual component
        VisualComponent visualComponent = this.weapon.engine.createComponent(VisualComponent.class);
        visualComponent.textureRegion = this.assets.getRegion(this.weapon.stats[i].bulletTexPath);
        visualComponent.setColor(Color.RED);
        visualComponent.originX = visualComponent.textureRegion.getRegionWidth() / 2;
        visualComponent.originY = visualComponent.textureRegion.getRegionHeight() / 2;
        visualComponent.depth = -2;

        //create movement component
        MovementComponent movementComponent = this.weapon.engine.createComponent(MovementComponent.class);
        movementComponent.velX = (float)Math.cos(Math.toRadians(transformComponent.rotation + 90f)) * this.weapon.stats[i].roundVelocity;
        movementComponent.velY = (float)Math.sin(Math.toRadians(transformComponent.rotation + 90f)) * this.weapon.stats[i].roundVelocity;
        movementComponent.velX += Mappers.movement.get(this.weapon.owner).velX;
        movementComponent.velY += Mappers.movement.get(this.weapon.owner).velY;

        Entity bullet = this.weapon.engine.createEntity();
        bullet.add(bulletComponent).add(transformComponent).add(visualComponent).add(movementComponent);

        return bullet;
    }

    private float getRotationModifier(int i)
    {
        MovementComponent targetMovement = Mappers.movement.get(this.weapon.target);
        TransformComponent targetPosition = Mappers.transform.get(this.weapon.target);
        MovementComponent ownerMovement = Mappers.movement.get(this.weapon.owner);

        if(!this.weapon.shouldLeadShots || targetMovement == null)
        {
            return 0f;
        }

        float modifier;

        //region shot lead calculation

        float roundVelocity = this.weapon.stats[i].roundVelocity;
        float relativeX = Mappers.transform.get(this.weapon.target).x - Mappers.transform.get(this.weapon.owner).x;
        float relativeY = Mappers.transform.get(this.weapon.target).y - Mappers.transform.get(this.weapon.owner).y;
        float relativeMagnitude = (float)Math.sqrt(relativeX * relativeX + relativeY * relativeY);
        float velocityMagnitude = (float)Math.sqrt(targetMovement.velX * targetMovement.velX + targetMovement.velY * targetMovement.velY);
        float thetaRadians = (float)Math.atan2(relativeX * targetMovement.velY - targetMovement.velY * relativeX,
                targetMovement.velX * relativeX + targetMovement.velY * relativeY );

        float a = (targetMovement.velX * targetMovement.velX + targetMovement.velY * targetMovement.velY) -
                roundVelocity * roundVelocity;
        float b = -2 * (float)Math.cos(thetaRadians) * relativeMagnitude * velocityMagnitude;
        float c = relativeMagnitude * relativeMagnitude;
        float delta = (float)Math.sqrt((b * b) - 4 * a * c);
        float time = -(b + delta) / (2 * a);

        float predictX = targetPosition.x + targetMovement.velX * time;
        float predictY = targetPosition.y + targetMovement.velY * time;
        float differenceX = predictX - this.transform.x;
        float differenceY = predictY - this.transform.y;
        modifier = (float)Math.toDegrees(Math.atan2(differenceY, differenceX));

        //endregion shot lead calculation

        //limit the angle's modifier to the weapon's range of motion
        float rangeOfMotion = this.weapon.stats[i].rangeOfMotion;
        if(modifier > rangeOfMotion)
        {
            modifier = rangeOfMotion;
        }
        else if(modifier < -rangeOfMotion)
        {
            modifier = -rangeOfMotion;
        }

        return 0f;//modifier;
    }
}
