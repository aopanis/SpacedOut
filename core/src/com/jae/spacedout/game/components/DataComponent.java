package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class DataComponent implements Component, Poolable
{
    //NOTE TO INCLUDE MASS FOR LINEAR THRUST
    public float hitpoints = 0;
    public float linearThrust = 0;
    public float lateralThrust = 0;
    public float rotationalThrust = 0;

    //default constructor
    public DataComponent()
    {

    }

    //constructor with data
    public DataComponent(float hitpoints, float linearThrust, float lateralThrust, float rotationalThrust)
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
