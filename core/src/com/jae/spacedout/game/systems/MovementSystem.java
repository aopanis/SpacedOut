package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.TransformComponent;

public class MovementSystem extends IteratingSystem
{
    private MovementComponent movement;
    private TransformComponent transform;

    public MovementSystem(int priority)
    {
        super(Family.all(MovementComponent.class, TransformComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.movement = Mappers.movement.get(entity);
        this.transform = Mappers.transform.get(entity);

        this.transform.x += this.movement.velX * dt;
        this.transform.y += this.movement.velY * dt;

        System.out.println(Math.sqrt(this.movement.velX * this.movement.velX + this.movement.velY * this.movement.velY));

        this.transform.rotation += this.movement.rotVel * dt;
    }
}
