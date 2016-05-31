package com.jae.spacedout.game.commands;

import com.badlogic.ashley.core.Entity;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.PhysicsComponent;

public class ForceCommand implements Command
{
    private float x;
    private float y;

    public ForceCommand(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(Entity entity)
    {
        PhysicsComponent physics = Mappers.physics.get(entity);
        physics.body.applyForceToCenter(this.x, this.y, true);
    }
}
