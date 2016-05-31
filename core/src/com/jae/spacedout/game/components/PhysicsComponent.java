package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;

public class PhysicsComponent implements Component, Poolable
{
    public Body body;

    //default constructor
    public PhysicsComponent()
    {

    }

    //reset function does nothing, must manually initialize body every time
    @Override
    public void reset()
    {

    }
}
