package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class MovementComponent implements Component, Poolable
{
    public float velX = 0;
    public float velY = 0;
    public float rotVel = 0;
    public float maxVel = 0;

    //default constructor
    public MovementComponent()
    {

    }

    //reset function does nothing, must manually initialize body every time
    @Override
    public void reset()
    {
        this.velX = 0;
        this.velY = 0;
        this.rotVel = 0;
        this.maxVel = 0;
    }
}
