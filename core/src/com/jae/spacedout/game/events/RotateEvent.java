package com.jae.spacedout.game.events;

import com.badlogic.ashley.core.Entity;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.TransformComponent;

public class RotateEvent implements Event
{
    public float rotation;
    public float dt;

    //default constructor
    public RotateEvent()
    {

    }

    public RotateEvent(float rotation)
    {
        this.rotation = rotation;
    }

    @Override
    public void execute(Entity entity)
    {
        TransformComponent transform = Mappers.transform.get(entity);
        MovementComponent movement = Mappers.movement.get(entity);

        if(this.rotation > movement.maxRotVel * dt)
        {
            this.rotation = movement.maxRotVel * dt;
        }
        else if(this.rotation < -movement.maxRotVel * dt)
        {
            this.rotation = -movement.maxRotVel * dt;
        }

        transform.rotation += this.rotation;
    }
}
