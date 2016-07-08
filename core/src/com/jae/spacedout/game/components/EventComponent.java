package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.jae.spacedout.game.events.Event;

import java.util.ArrayList;

public class EventComponent implements Component, Poolable
{
    public ArrayList<Event> events = new ArrayList<Event>(64);

    //default constructor
    public EventComponent()
    {

    }

    @Override
    public void reset()
    {
        events = new ArrayList<Event>(64);
    }
}
