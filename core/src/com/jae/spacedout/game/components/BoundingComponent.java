package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.jae.spacedout.utility.Quadtree;

public class BoundingComponent implements Component, Poolable
{
    public Rectangle boundingBox;
    public Quadtree currentLeaf;
    public boolean shouldRegister = true;

    public BoundingComponent()
    {

    }

    @Override
    public void reset()
    {

    }
}
