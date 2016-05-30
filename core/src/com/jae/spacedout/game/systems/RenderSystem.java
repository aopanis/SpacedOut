package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.game.components.VisualComponent;

public class RenderSystem extends EntitySystem
{
    private TransformComponent transform;
    private VisualComponent visual;

    //list with all renderable items
    private ImmutableArray<Entity> entities;

    //visual tools to render
    private SpriteBatch batch;
    private OrthographicCamera camera;

    //constructor that takes the screens camera
    public RenderSystem (OrthographicCamera camera)
    {
        this.batch = new SpriteBatch();
        this.camera = camera;
    }

    //adds all entities that can be rendered
    @Override
    public void addedToEngine (Engine engine)
    {
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, VisualComponent.class).get());
    }

    @Override
    public void update (float dt)
    {
        this.camera.update();

        this.batch.begin();
        this.batch.setProjectionMatrix(camera.combined);

        //render all entities
        for (Entity entity : this.entities)
        {
            this.transform = Mappers.transform.get(entity);
            this.visual = Mappers.visual.get(entity);

            batch.draw(visual.textureRegion, transform.x - visual.originX, transform.y - visual.originY, visual.originX, visual.originY,
                    visual.textureRegion.getRegionWidth(), visual.textureRegion.getRegionHeight(),
                    visual.scaleX, visual.scaleY, transform.rotation);
        }

        batch.end();
    }
}
