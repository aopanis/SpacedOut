package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.jae.spacedout.game.components.BulletComponent;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.utility.Utils;

public class MovementSystem extends IteratingSystem
{
    MovementComponent movement;
    TransformComponent transform;
    BulletComponent bullet;

    public MovementSystem(int priority)
    {
        super(Family.all(MovementComponent.class, TransformComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.movement = Mappers.movement.get(entity);
        this.transform = Mappers.transform.get(entity);

        transform.x += movement.velX * dt;
        transform.y += movement.velY * dt;

        transform.rotation += movement.rotVel * dt;
        transform.rotation = Utils.normalizeAngle(transform.rotation);

        this.bullet = Mappers.bullet.get(entity);
        if(this.bullet != null)
        {
            float distanceSquared = Utils.findLength(movement.velX * dt, movement.velY * dt);
            this.bullet.distaceTraveled += distanceSquared;
        }
    }
}