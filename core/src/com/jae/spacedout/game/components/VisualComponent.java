package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public class VisualComponent implements Component, Poolable
{
    public TextureRegion textureRegion = null;
    public float originX = 0f;
    public float originY = 0f;
    public float scaleX = 1f;
    public float scaleY = 1f;
    public int depth = 0;
    private Color color = Color.WHITE;
    public void setColor(Color color)
    {
        this.color = color.cpy();
    }
    public Color getColor()
    {
        return this.color.cpy();
    }

    //default constructor
    public VisualComponent()
    {

    }

    //constructor with data
    public VisualComponent(TextureRegion textureRegion, float originX, float originY, float scaleX, float scaleY, int depth, Color color)
    {
        this.textureRegion = textureRegion;
        this.originX = originX;
        this.originY = originY;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.depth = depth;
        this.color = color;
    }

    @Override
    public void reset()
    {
        this.textureRegion = null;
        this.originX = 0;
        this.originY = 0;
        this.scaleX = 1;
        this.scaleY = 1;
        this.color = Color.WHITE;
    }
}
