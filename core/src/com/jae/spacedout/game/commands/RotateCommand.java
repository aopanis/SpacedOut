package com.jae.spacedout.game.commands;

import com.badlogic.ashley.core.Entity;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.TransformComponent;

public class RotateCommand implements Command
{
    private float rotation;

    public RotateCommand(float rotation)
    {
        this.rotation = rotation;
    }

    @Override
    public void execute(Entity entity)
    {
        TransformComponent transform = Mappers.transform.get(entity);
        transform.rotation += this.rotation;
    }
}
