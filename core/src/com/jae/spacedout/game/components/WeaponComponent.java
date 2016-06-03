package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.jae.spacedout.game.stats.WeaponStatHolder;

public class WeaponComponent implements Component, Poolable
{
    public Entity owner;
    public WeaponStatHolder[] stats;
    public boolean[] shouldShoot;
    public float[] shotTimer;

    //default constructor
    public WeaponComponent()
    {

    }

    @Override
    public void reset()
    {

    }
}
