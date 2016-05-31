package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.game.components.VisualComponent;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem
{
    private TransformComponent transform;
    private VisualComponent visual;

    //list with all renderable items
    private ImmutableArray<Entity> entities;

    //visual tools to render
    private SpriteBatch batch;
    private OrthographicCamera camera;

    //constructor that takes the screens camera
    public RenderSystem (OrthographicCamera camera, int priority)
    {
        super(Family.all(VisualComponent.class, TransformComponent.class).get(), new depthComparator(), priority);

        this.batch = new SpriteBatch();
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.visual = Mappers.visual.get(entity);
        this.transform = Mappers.transform.get(entity);
        this.batch.draw(this.visual.textureRegion, this.transform.x - this.visual.originX, this.transform.y - this.visual.originY,
                this.visual.originX, this.visual.originY, this.visual.textureRegion.getRegionWidth(), this.visual.textureRegion.getRegionHeight(),
                this.visual.scaleX, this.visual.scaleY, this.transform.rotation);
    }

    //adds all entities that can be rendered
    @Override
    public void addedToEngine (Engine engine)
    {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, VisualComponent.class).get());
    }

    //disposes of spritebatch
    @Override
    public void removedFromEngine (Engine engine)
    {
        super.removedFromEngine(engine);
        this.batch.dispose();
    }

    @Override
    public void update (float dt)
    {
        //clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update camera
        this.camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);

        //begin batch and draw
        this.batch.begin();
        super.update(dt);
        batch.end();
    }

    private static class depthComparator implements Comparator<Entity>
    {
        @Override
        public int compare(Entity entity1, Entity entity2)
        {
            return (int)Math.signum(Mappers.visual.get(entity1).depth - Mappers.visual.get(entity2).depth);
        }
    }
}
