package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jae.spacedout.utility.Settings;

public class CameraComponent implements Component, Poolable
{
    public OrthographicCamera camera;
    public Viewport viewport;
    public Entity entity;

    //default constructor
    public CameraComponent()
    {

    }

    //constructor with data
    public CameraComponent(Entity entity)
    {
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.zoom = 1f;
        this.viewport = new StretchViewport(Settings.GAME_SCREEN_WIDTH, Settings.GAME_SCREEN_HEIGHT, this.camera);
        this.entity = entity;
    }

    //reset does nothing, must manually reset values
    @Override
    public void reset()
    {

    }
}
