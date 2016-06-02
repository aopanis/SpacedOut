package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ParticleComponent implements Component, Poolable
{
    public float lifeTimer = 0;
    public float maxTimer = 0;

    //default constructor
    public ParticleComponent()
    {
        this.lifeTimer = 0;
        this.maxTimer = 0;
    }

    @Override
    public void reset()
    {
        this.lifeTimer = 0;
        this.maxTimer = 0;
    }
}
