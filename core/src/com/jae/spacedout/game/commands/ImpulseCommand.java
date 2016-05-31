package com.jae.spacedout.game.commands;

import com.badlogic.ashley.core.Entity;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.PhysicsComponent;

public class ImpulseCommand implements Command
{
    public void execute(Entity entity, float x, float y)
    {
        PhysicsComponent physics = Mappers.physics.get(entity);
        physics.body.applyLinearImpulse(x, y, physics.body.getPosition().x, physics.body.getPosition().y, true);
    }
}
