package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.utility.Utils;

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

        /*
        //dampen horizontal motion
        if(this.movement.dampenHorizontal)
        {
            float theta = (float) Math.toDegrees(Math.atan2(this.movement.velY, this.movement.velX)) - this.transform.rotation;
            float projection = -Utils.findLengthSquared(this.movement.velX, this.movement.velY) * theta;
            this.movement.velX += (float)Math.cos(Math.toRadians(this.transform.rotation)) * projection;
            this.movement.velY += (float)Math.sin(Math.toRadians(this.transform.rotation)) * projection;
        }
        */

        this.transform.rotation += this.movement.rotVel * dt;
    }
}
