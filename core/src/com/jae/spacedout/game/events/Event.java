package com.jae.spacedout.game.events;

import com.badlogic.ashley.core.Entity;

public interface Event
{
    //all events must have an execute method with only the entity as a paramenter
    public void execute(Entity entity);
}
