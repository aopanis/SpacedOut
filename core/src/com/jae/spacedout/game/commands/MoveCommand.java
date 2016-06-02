package com.jae.spacedout.game.commands;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.MovementComponent;

public class MoveCommand implements Command, Poolable
{
    public float x;
    public float y;
    private float oldVelX;
    private float oldVelY;

    //default constructor
    public MoveCommand()
    {
        this.x = 0;
        this.y = 0;
    }

    public MoveCommand(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(Entity entity)
    {
        MovementComponent movement = Mappers.movement.get(entity);

        this.oldVelX = movement.velX;
        this.oldVelY = movement.velY;

        movement.velX += this.x;
        movement.velY += this.y;

        if(movement.velX * movement.velX + movement.velY * movement.velY > movement.maxVel * movement.maxVel)
        {
            movement.velX = this.oldVelX;
            movement.velY = this.oldVelY;
        }
    }

    @Override
    public void reset()
    {
        this.x = 0;
        this.y = 0;
    }
}
