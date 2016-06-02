package com.jae.spacedout.game.commands;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.MovementComponent;

public class MoveCommand implements Command, Poolable
{
    public float x;
    public float y;

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
        movement.velX += this.x;
        movement.velY += this.y;
    }

    @Override
    public void reset()
    {
        this.x = 0;
        this.y = 0;
    }
}
