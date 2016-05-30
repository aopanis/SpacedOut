package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TransformComponent implements Component, Poolable
{
    public float x = 0f;
    public float y = 0f;
    public float rotation = 0f;

    //default constructor
    public TransformComponent()
    {

    }

    //constructor with values
    public TransformComponent(float x, float y, float rotation)
    {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    @Override
    public void reset()
    {
        this.x = 0;
        this.y = 0;
        this.rotation = 0;
    }
}
