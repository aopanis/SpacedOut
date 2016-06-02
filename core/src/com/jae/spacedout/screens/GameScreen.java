package com.jae.spacedout.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jae.spacedout.SpacedOut;
import com.jae.spacedout.game.components.CameraComponent;
import com.jae.spacedout.game.components.CommandComponent;
import com.jae.spacedout.game.components.DataComponent;
import com.jae.spacedout.game.components.InputComponent;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.game.components.VisualComponent;
import com.jae.spacedout.game.systems.CameraSystem;
import com.jae.spacedout.game.systems.CommandSystem;
import com.jae.spacedout.game.systems.EnvironmentManager;
import com.jae.spacedout.game.systems.ShipInputSystem;
import com.jae.spacedout.game.systems.MovementSystem;
import com.jae.spacedout.game.systems.RenderSystem;
import com.jae.spacedout.utility.Settings;

import java.util.Random;

public class GameScreen implements Screen
{
    //ECS engine and game class
    private final PooledEngine engine;
    private final SpacedOut spacedOut;

    //environment generator
    private EnvironmentManager manager;

    //random number generator
    private final Random random;

    /** TESTING **/
    public Entity SHIP;
    public Entity CAMERA;
    /** TESTING **/

    public GameScreen(PooledEngine engine, SpacedOut spacedOut, Random random)
    {
        this.engine = engine;
        this.spacedOut = spacedOut;

        this.random = random;

        /**TESTING**/
        this.SHIP = this.engine.createEntity();
        this.CAMERA = this.engine.createEntity();

        TransformComponent transform = engine.createComponent(TransformComponent.class);
        transform.x = Settings.GAME_SCREEN_WIDTH / 2;
        transform.y = Settings.GAME_SCREEN_HEIGHT / 2;

        VisualComponent visual = engine.createComponent(VisualComponent.class);
        visual.textureRegion = this.spacedOut.assets.getRegion(Settings.DEBUG_SHIP);
        visual.originX = visual.textureRegion.getRegionWidth() / 2;
        visual.originY = visual.textureRegion.getRegionHeight() / 2;
        visual.setColor(Color.WHITE);

        MovementComponent movement = engine.createComponent(MovementComponent.class);
        movement.maxVel = 200;

        InputComponent input = engine.createComponent(InputComponent.class);

        DataComponent data = engine.createComponent(DataComponent.class);
        data.hitpoints = 500;
        data.linearThrust = 80;
        data.lateralThrust = 40;
        data.rotationalThrust = 80;

        CommandComponent command = engine.createComponent(CommandComponent.class);

        this.SHIP.add(transform);
        this.SHIP.add(visual);
        this.SHIP.add(movement);
        this.SHIP.add(input);
        this.SHIP.add(data);
        this.SHIP.add(command);

        CameraComponent camera = engine.createComponent(CameraComponent.class);
        camera.camera = new OrthographicCamera();
        camera.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.camera.zoom = 1f;
        camera.viewport = new StretchViewport(Settings.GAME_SCREEN_WIDTH, Settings.GAME_SCREEN_HEIGHT, camera.camera);
        camera.entity = this.SHIP;
        this.CAMERA.add(camera);

        this.engine.addEntity(this.SHIP);
        this.engine.addEntity(this.CAMERA);
        this.engine.addSystem(new CameraSystem(4));
        this.engine.addSystem(new RenderSystem(0));
        this.engine.addSystem(new ShipInputSystem(1));
        this.engine.addSystem(new CommandSystem(2));
        this.engine.addSystem(new MovementSystem(3));
        /**TESTING**/

        TextureRegion[] textures = new TextureRegion[6];
        textures[0] = this.spacedOut.assets.getRegion(Settings.STAR_1);
        textures[1] = this.spacedOut.assets.getRegion(Settings.STAR_2);
        textures[2] = this.spacedOut.assets.getRegion(Settings.STAR_3);
        textures[3] = this.spacedOut.assets.getRegion(Settings.STAR_4);
        textures[4] = this.spacedOut.assets.getRegion(Settings.STAR_5);
        textures[5] = this.spacedOut.assets.getRegion(Settings.STAR_6);
        this.manager = new EnvironmentManager(this.engine, this.SHIP, this.random, textures);
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        this.engine.update(delta);
        this.manager.update(delta);
    }

    @Override
    public void resize(int width, int height)
    {
        this.engine.getSystem(CameraSystem.class).updateViewport(width, height);
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
