package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.jae.spacedout.game.components.DataComponent;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.game.components.VisualComponent;
import com.jae.spacedout.game.components.WeaponComponent;
import com.jae.spacedout.utility.Assets;

public class WeaponSystem extends IteratingSystem
{
    private WeaponComponent weapon;
    private DataComponent data;
    private TransformComponent transform;
    Assets assets;

    public WeaponSystem(Assets assets, int priority)
    {
        super(Family.all(WeaponComponent.class).get(), priority);

        this.assets = assets;
    }

    @Override
    public void processEntity(Entity entity, float dt)
    {
        this.weapon = Mappers.weapon.get(entity);
        this.data = Mappers.data.get(entity);
        this.transform = Mappers.transform.get(entity);

        for(int i = 0; i < this.weapon.stats.length; i++)
        {
            if(this.weapon.stats[i] != null)
            {
                if(this.weapon.shouldShoot[i] && this.weapon.shotTimer[i] > (1f / this.weapon.stats[i].maxRoundsPerSecond))
                {
                    float offsetX = this.data.stats.weaponPositions[i].x * (float)Math.cos(Math.toRadians(this.transform.rotation)) -
                            this.data.stats.weaponPositions[i].y * (float)Math.sin(Math.toRadians(this.transform.rotation));
                    float offsetY = this.data.stats.weaponPositions[i].x * (float)Math.sin(Math.toRadians(this.transform.rotation)) +
                            this.data.stats.weaponPositions[i].y * (float)Math.cos(Math.toRadians(this.transform.rotation));
                    float spawnX = this.transform.x + offsetX;
                    float spawnY = this.transform.y + offsetY;
                    TransformComponent transformComponent = this.data.engine.createComponent(TransformComponent.class);
                    transformComponent.x = spawnX;
                    transformComponent.y = spawnY;
                    transformComponent.rotation = this.transform.rotation;

                    VisualComponent visualComponent = this.data.engine.createComponent(VisualComponent.class);
                    visualComponent.textureRegion = this.assets.getRegion(this.weapon.stats[i].bulletTexPath);
                    visualComponent.setColor(Color.RED);
                    visualComponent.originY = 2;
                    visualComponent.depth = -2;

                    Entity bullet = this.data.engine.createEntity();
                    bullet.add(transformComponent).add(visualComponent);
                    this.data.engine.addEntity(bullet);

                    this.weapon.shotTimer[i] = 0;
                }

                this.weapon.shouldShoot[i] = false;
                this.weapon.shotTimer[i] += dt;
            }
        }
    }
}
