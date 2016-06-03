package com.jae.spacedout.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jae.spacedout.SpacedOut;
import com.jae.spacedout.game.components.CameraComponent;
import com.jae.spacedout.game.components.CommandComponent;
import com.jae.spacedout.game.components.DataComponent;
import com.jae.spacedout.game.components.InputComponent;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.game.components.VisualComponent;
import com.jae.spacedout.game.components.WeaponComponent;
import com.jae.spacedout.game.stats.ShipStats;
import com.jae.spacedout.game.stats.WeaponStatHolder;
import com.jae.spacedout.game.stats.WeaponStats;
import com.jae.spacedout.game.systems.CameraSystem;
import com.jae.spacedout.game.systems.CommandSystem;
import com.jae.spacedout.game.systems.ParticleManager;
import com.jae.spacedout.game.systems.ShipInputSystem;
import com.jae.spacedout.game.systems.MovementSystem;
import com.jae.spacedout.game.systems.RenderSystem;
import com.jae.spacedout.game.systems.WeaponSystem;
import com.jae.spacedout.utility.Settings;

import java.util.Random;
import java.util.Set;

public class GameScreen implements Screen
{
    //ECS engine and game class
    private final PooledEngine engine;
    private final SpacedOut spacedOut;

    //environment generator
    private ParticleManager environmentManager;

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
        visual.textureRegion = this.spacedOut.assets.getRegion(ShipStats.getStats("JM1").texPath);
        visual.originX = visual.textureRegion.getRegionWidth() / 2;
        visual.originY = visual.textureRegion.getRegionHeight() / 2;
        visual.setColor(Color.WHITE);

        MovementComponent movement = engine.createComponent(MovementComponent.class);
        movement.maxVel = 200;

        InputComponent input = engine.createComponent(InputComponent.class);

        DataComponent data = engine.createComponent(DataComponent.class);
        data.stats = ShipStats.getStats("JM1");
        data.engine = this.engine;

        CommandComponent command = engine.createComponent(CommandComponent.class);

        WeaponComponent weapon = engine.createComponent(WeaponComponent.class);
        weapon.shouldShoot = new boolean[data.stats.weaponSlots];
        weapon.shotTimer = new float[data.stats.weaponSlots];
        weapon.stats = new WeaponStatHolder[data.stats.weaponSlots];
        for(int i = 0; i < data.stats.weaponSlots; i++)
        {
            weapon.shouldShoot[i] = false;
            weapon.shotTimer[i] = 0f;
            weapon.stats[i] = WeaponStats.getStats("KN1");
        }
        weapon.owner = this.SHIP;

        this.SHIP.add(transform).add(visual).add(movement).add(input).add(data).add(command).add(weapon);

        CameraComponent camera = engine.createComponent(CameraComponent.class);
        camera.camera = new OrthographicCamera();
        camera.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.camera.zoom = 1f;
        camera.viewport = new StretchViewport(Settings.GAME_SCREEN_WIDTH, Settings.GAME_SCREEN_HEIGHT, camera.camera);
        camera.entity = this.SHIP;
        this.CAMERA.add(camera);

        this.engine.addEntity(this.SHIP);
        this.engine.addEntity(this.CAMERA);
        this.engine.addSystem(new CameraSystem(5));
        this.engine.addSystem(new RenderSystem(0));
        this.engine.addSystem(new ShipInputSystem(1));
        this.engine.addSystem(new CommandSystem(2));
        this.engine.addSystem(new MovementSystem(3));
        this.engine.addSystem(new WeaponSystem(this.spacedOut.assets, 4));
        /**TESTING**/

        TextureRegion[] textures = new TextureRegion[6];
        textures[0] = this.spacedOut.assets.getRegion(Settings.STAR_1);
        textures[1] = this.spacedOut.assets.getRegion(Settings.STAR_2);
        textures[2] = this.spacedOut.assets.getRegion(Settings.STAR_3);
        textures[3] = this.spacedOut.assets.getRegion(Settings.STAR_4);
        textures[4] = this.spacedOut.assets.getRegion(Settings.STAR_5);
        textures[5] = this.spacedOut.assets.getRegion(Settings.STAR_6);
        this.environmentManager = new ParticleManager(this.engine, this.SHIP, this.random, textures,
                Settings.GAME_SCREEN_WIDTH, Settings.starMinScale, Settings.starMaxScale, 0f, 360f, Settings.GAME_SCREEN_WIDTH * 1.2f,
                Settings.starMinDuration, Settings.starMaxDuration);
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        this.engine.update(delta);
        this.environmentManager.update(delta);
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
