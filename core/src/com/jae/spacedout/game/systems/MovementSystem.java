package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.TransformComponent;

public class MovementSystem extends IteratingSystem
{
    TransformComponent transform;
    MovementComponent movement;

    public MovementSystem()
    {
        super(Family.all(TransformComponent.class, MovementComponent.class).get());
    }

    //moves all items by their velocities
    public void processEntity(Entity entity, float dt)
    {
        this.transform = Mappers.transform.get(entity);
        this.movement = Mappers.movement.get(entity);

        this.transform.x += this.movement.velX * dt;
        this.transform.y += this.movement.velY * dt;
        this.transform.rotation += this.movement.rotVel * dt;
    }
}
