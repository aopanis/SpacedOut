package com.jae.spacedout.game.commands;

import com.badlogic.ashley.core.Entity;

public interface Command
{
    //all commands must have an execute method with only the entity as a paramenter
    public void execute(Entity entity);
}
