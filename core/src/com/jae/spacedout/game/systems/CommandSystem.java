package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.jae.spacedout.game.commands.Command;
import com.jae.spacedout.game.components.CommandComponent;
import com.jae.spacedout.game.components.Mappers;

public class CommandSystem extends IteratingSystem
{
    private CommandComponent command;

    public CommandSystem(int priority)
    {
        super(Family.all(CommandComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.command = Mappers.command.get(entity);
        for (Command com : this.command.commands)
        {
            com.execute(entity);
        }
    }
}
