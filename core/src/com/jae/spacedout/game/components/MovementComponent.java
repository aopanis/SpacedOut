package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class MovementComponent implements Component, Poolable
{
    public float velX = 0f;
    public float velY = 0f;
    public float rotVel = 0f;

    //default constructor with all zero values
    public MovementComponent()
    {

    }

    //constructor to set items
    public MovementComponent(float velX, float velY, float rotVel)
    {
        this.velX = velX;
        this.velY = velY;
        this.rotVel = rotVel;
    }

    @Override
    public void reset()
    {
        this.velX = 0;
        this.velY = 0;
        this.rotVel = 0;
    }
}
