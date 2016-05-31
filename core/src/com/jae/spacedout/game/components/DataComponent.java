package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class DataComponent implements Component, Poolable
{
    public int hitpoints = 0;
    public int linearThrust = 0;
    public int lateralThrust = 0;
    public int rotationalThrust = 0;

    //default constructor
    public DataComponent()
    {

    }

    //constructor with data
    public DataComponent(int hitpoints, int linearThrust, int lateralThrust, int rotationalThrust)
    {
        this.hitpoints = hitpoints;
        this.linearThrust = linearThrust;
        this.lateralThrust = lateralThrust;
        this.rotationalThrust = rotationalThrust;
    }

    @Override
    public void reset()
    {
        this.hitpoints = 0;
        this.linearThrust = 0;
        this.lateralThrust = 0;
        this.rotationalThrust = 0;
    }
}
