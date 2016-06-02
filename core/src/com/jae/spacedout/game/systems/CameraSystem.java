package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.jae.spacedout.game.components.CameraComponent;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.utility.Settings;

public class CameraSystem extends IteratingSystem
{
    private CameraComponent camera;
    private TransformComponent transform;
    private final float cameraLerp = 5f;

    public CameraSystem(int priority)
    {
        super(Family.all(CameraComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.camera = Mappers.camera.get(entity);
        this.transform = Mappers.transform.get(this.camera.entity);
        this.camera.camera.position.x += (this.transform.x - this.camera.camera.position.x) * this.cameraLerp * dt;
        this.camera.camera.position.y += (this.transform.y - this.camera.camera.position.y) * this.cameraLerp * dt;

        //region zoom controls

        if(Gdx.input.isKeyPressed(Settings.zoomIn))
        {
            this.camera.camera.zoom -= Settings.zoomSpeed * dt;
            if(this.camera.camera.zoom < Settings.minZoom)
            {
                this.camera.camera.zoom = Settings.minZoom;
            }
        }
        if(Gdx.input.isKeyPressed(Settings.zoomOut))
        {
            this.camera.camera.zoom += Settings.zoomSpeed * dt;
            if(this.camera.camera.zoom > Settings.maxZoom)
            {
                this.camera.camera.zoom = Settings.maxZoom;
            }
        }

        //endregion zoom controls
    }

    public void updateViewport(int width, int height)
    {
        for(Entity entity : this.getEntities())
        {
            this.camera = Mappers.camera.get(entity);
            this.camera.viewport.update(width, height);
        }
    }
}
