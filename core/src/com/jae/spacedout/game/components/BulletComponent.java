package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.jae.spacedout.game.stats.WeaponStatHolder;

public class BulletComponent implements Component, Poolable
{
    public Entity owner;
    public float maxDistance;
    public int damage;

    //default constructor
    public BulletComponent()
    {

    }

    @Override
    public void reset()
    {

    }
}
