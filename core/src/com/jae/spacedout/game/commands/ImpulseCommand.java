package com.jae.spacedout.game.commands;

import com.badlogic.ashley.core.Entity;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.PhysicsComponent;

public class ImpulseCommand implements Command
{
    private float x;
    private float y;

    public ImpulseCommand(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void execute(Entity entity)
    {
        PhysicsComponent physics = Mappers.physics.get(entity);
        physics.body.applyLinearImpulse(this.x, this.y, physics.body.getPosition().x, physics.body.getPosition().y, true);
    }
}
