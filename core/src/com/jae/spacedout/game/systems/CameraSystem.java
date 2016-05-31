package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.jae.spacedout.game.components.CameraComponent;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.TransformComponent;

public class CameraSystem extends IteratingSystem
{
    private CameraComponent camera;
    private TransformComponent transform;

    public CameraSystem(int priority)
    {
        super(Family.all(CameraComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.camera = Mappers.camera.get(entity);
        this.transform = Mappers.transform.get(this.camera.entity);
        this.camera.camera.position.set(this.transform.x, this.transform.y, 0);
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
