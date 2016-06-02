package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.jae.spacedout.game.components.CameraComponent;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.game.components.VisualComponent;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem
{
    private TransformComponent transform;
    private VisualComponent visual;

    //visual tools to render
    private SpriteBatch batch;
    private OrthographicCamera camera;

    /**TESTING**/
    public BitmapFont FONT;
    /**TESTING**/

    //constructor that takes the screens camera
    public RenderSystem (int priority)
    {
        super(Family.all(VisualComponent.class, TransformComponent.class).get(), new depthComparator(), priority);

        this.batch = new SpriteBatch();

        /**TESTING**/
        this.FONT = new BitmapFont();
        /**TESTING**/
    }

    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.visual = Mappers.visual.get(entity);
        this.transform = Mappers.transform.get(entity);
        this.batch.setColor(this.visual.getColor());
        this.batch.draw(this.visual.textureRegion, this.transform.x - this.visual.originX, this.transform.y - this.visual.originY,
                this.visual.originX, this.visual.originY, this.visual.textureRegion.getRegionWidth(), this.visual.textureRegion.getRegionHeight(),
                this.visual.scaleX, this.visual.scaleY, this.transform.rotation);
    }

    //adds all entities that can be rendered
    @Override
    public void addedToEngine (Engine engine)
    {
        super.addedToEngine(engine);

        Entity entity = engine.getEntitiesFor(Family.all(CameraComponent.class).get()).get(0);
        this.camera = Mappers.camera.get(entity).camera;
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

        /**TESTING**/
        if(Gdx.input.isTouched())
        {
            Vector3 coords = this.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            this.FONT.draw(this.batch, coords.x + ", " + coords.y, coords.x, coords.y);
        }
        this.FONT.draw(this.batch, "(480, 270)", 480, 270);
        /**TESTING**/

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
