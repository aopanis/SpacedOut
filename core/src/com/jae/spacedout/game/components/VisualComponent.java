package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public class VisualComponent implements Component, Poolable
{
    public TextureRegion textureRegion = null;
    public float originX = 0f;
    public float originY = 0f;
    public float scaleX = 0f;
    public float scaleY = 0f;

    //default constructor
    public VisualComponent()
    {

    }

    //constructor with data
    public VisualComponent(TextureRegion textureRegion, float originX, float originY, float scaleX, float scaleY)
    {
        this.textureRegion = textureRegion;
        this.originX = originX;
        this.originY = originY;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public void reset()
    {
        this.textureRegion = null;
        this.originX = 0;
        this.originY = 0;
        this.scaleX = 0;
        this.scaleY = 0;
    }
}
