package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.jae.spacedout.game.stats.ShipStatHolder;

public class DataComponent implements Component, Poolable
{
    public ShipStatHolder stats;
    public PooledEngine engine;
    public Entity targetEntity;

    //default constructor
    public DataComponent()
    {

    }

    @Override
    public void reset()
    {

    }
}
