package com.jae.spacedout.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jae.spacedout.SpacedOut;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.game.components.VisualComponent;
import com.jae.spacedout.game.systems.MovementSystem;
import com.jae.spacedout.game.systems.RenderSystem;
import com.jae.spacedout.utility.Settings;

public class GameScreen implements Screen
{
    //ECS engine and game class
    private final PooledEngine engine;
    private final SpacedOut spacedOut;
    //camera and viewport
    private OrthographicCamera camera;
    private StretchViewport viewport;

    /** TESTING **/
    public Entity SHIP;
    public BitmapFont FONT;
    public SpriteBatch DEBUG;
    /** TESTING **/

    public GameScreen(PooledEngine engine, SpacedOut spacedOut)
    {
        this.engine = engine;
        this.spacedOut = spacedOut;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.viewport = new StretchViewport(Settings.GAME_SCREEN_WIDTH, Settings.GAME_SCREEN_HEIGHT, this.camera);

        /**TESTING**/
        this.FONT = new BitmapFont();
        this.SHIP = this.engine.createEntity();
        this.DEBUG = new SpriteBatch();
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        transform.x = 480;
        transform.y = 270;
        MovementComponent movement = engine.createComponent(MovementComponent.class);
        VisualComponent visual = engine.createComponent(VisualComponent.class);
        visual.textureRegion = new TextureRegion(new Texture(Gdx.files.internal("debug/ship.png")));
        visual.originX = visual.textureRegion.getRegionWidth() / 2;
        visual.originY = visual.textureRegion.getRegionHeight() / 2;
        this.SHIP.add(transform);
        this.SHIP.add(movement);
        this.SHIP.add(visual);
        this.engine.addEntity(this.SHIP);
        this.engine.addSystem(new MovementSystem());
        this.engine.addSystem(new RenderSystem(this.camera));
        /**TESTING**/
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        this.engine.update(delta);

        /**TESTING**/
        this.DEBUG.setProjectionMatrix(this.camera.combined);
        this.DEBUG.begin();
        this.FONT.draw(this.DEBUG, "(480, 270)", 480, 270);
        this.DEBUG.end();
        /**TESTING**/
    }

    @Override
    public void resize(int width, int height)
    {
        this.viewport.update(width, height);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }
}
