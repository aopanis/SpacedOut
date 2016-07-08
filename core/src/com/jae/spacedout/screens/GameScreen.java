package com.jae.spacedout.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jae.spacedout.SpacedOut;
import com.jae.spacedout.game.components.CameraComponent;
import com.jae.spacedout.game.components.EventComponent;
import com.jae.spacedout.game.components.DataComponent;
import com.jae.spacedout.game.components.InputComponent;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.SteeringAIComponent;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.game.components.VisualComponent;
import com.jae.spacedout.game.components.WeaponComponent;
import com.jae.spacedout.game.stats.ShipStats;
import com.jae.spacedout.game.stats.WeaponStatHolder;
import com.jae.spacedout.game.stats.WeaponStats;
import com.jae.spacedout.game.systems.BulletSystem;
import com.jae.spacedout.game.systems.CameraSystem;
import com.jae.spacedout.game.systems.EventSystem;
import com.jae.spacedout.game.systems.ParticleManager;
import com.jae.spacedout.game.systems.ShipInputSystem;
import com.jae.spacedout.game.systems.MovementSystem;
import com.jae.spacedout.game.systems.RenderSystem;
import com.jae.spacedout.game.systems.SteeringAISystem;
import com.jae.spacedout.game.systems.WeaponSystem;
import com.jae.spacedout.utility.Settings;

import java.util.Random;

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
    public Entity AI_SHIP;
    public Entity CAMERA;
    private FPSLogger logger = new FPSLogger();
    /** TESTING **/

    public GameScreen(PooledEngine engine, SpacedOut spacedOut, Random random)
    {
        this.engine = engine;
        this.spacedOut = spacedOut;

        this.random = random;

        /**TESTING**/
        this.SHIP = this.engine.createEntity();
        this.AI_SHIP = this.engine.createEntity();
        this.CAMERA = this.engine.createEntity();

        //region SHIP

        TransformComponent transform = engine.createComponent(TransformComponent.class);
        transform.x = Settings.GAME_SCREEN_WIDTH / 2;
        transform.y = Settings.GAME_SCREEN_HEIGHT / 2;

        VisualComponent visual = engine.createComponent(VisualComponent.class);
        visual.textureRegion = this.spacedOut.assets.getRegion(ShipStats.getStats("JM1").texPath);
        visual.originX = visual.textureRegion.getRegionWidth() / 2;
        visual.originY = visual.textureRegion.getRegionHeight() / 2;
        visual.setColor(Color.WHITE);

        DataComponent data = engine.createComponent(DataComponent.class);
        data.stats = ShipStats.getStats("JM1");
        data.engine = this.engine;

        MovementComponent movement = engine.createComponent(MovementComponent.class);
        movement.maxVel = 200;
        movement.maxRotVel = data.stats.baseThrustRotational / data.stats.baseMass;
        movement.maxAcc = data.stats.baseThrustLinear / data.stats.baseMass;
        movement.maxRotAcc = data.stats.baseThrustRotational / data.stats.baseMass;

        InputComponent input = engine.createComponent(InputComponent.class);

        EventComponent command = engine.createComponent(EventComponent.class);

        WeaponComponent weapon = engine.createComponent(WeaponComponent.class);
        weapon.shouldShoot = new boolean[data.stats.weaponSlots];
        weapon.shotTimer = new float[data.stats.weaponSlots];
        weapon.stats = new WeaponStatHolder[data.stats.weaponSlots];
        for(int i = 0; i < data.stats.weaponSlots; i++)
        {
            weapon.shouldShoot[i] = false;
            weapon.shotTimer[i] = 0f;
        }
        weapon.stats[0] = WeaponStats.getStats("KY1");
        weapon.stats[1] = WeaponStats.getStats("KY1");
        weapon.owner = this.SHIP;
        weapon.engine = this.engine;
        weapon.offset = data.stats.weaponPositions;
        weapon.target = this.AI_SHIP;
        weapon.shouldLeadShots = Settings.shouldLeadShots;

        SteeringAIComponent steering = engine.createComponent(SteeringAIComponent.class);
        steering.entity = this.SHIP;
        steering.steeringAcceleration = new SteeringAcceleration<Vector2>(new Vector2(0, 0), 0f);

        this.SHIP.add(transform).add(visual).add(movement).add(input).add(data).add(command).add(weapon).add(steering);

        //endregion SHIP

        //region AI_SHIP

        TransformComponent aiTransform = engine.createComponent(TransformComponent.class);
        transform.x = Settings.GAME_SCREEN_WIDTH / 4;
        transform.y = Settings.GAME_SCREEN_HEIGHT / 4;

        VisualComponent aiVisual = engine.createComponent(VisualComponent.class);
        aiVisual.textureRegion = this.spacedOut.assets.getRegion(ShipStats.getStats("JM1").texPath);
        aiVisual.originX = aiVisual.textureRegion.getRegionWidth() / 2;
        aiVisual.originY = aiVisual.textureRegion.getRegionHeight() / 2;
        aiVisual.setColor(Color.WHITE);

        DataComponent aiData = engine.createComponent(DataComponent.class);
        aiData.stats = ShipStats.getStats("JM1");
        aiData.engine = this.engine;

        MovementComponent aiMovement = engine.createComponent(MovementComponent.class);
        aiMovement.maxVel = 200;
        aiMovement.maxRotVel = aiData.stats.baseThrustRotational / aiData.stats.baseMass;
        aiMovement.maxAcc = aiData.stats.baseThrustLinear / aiData.stats.baseMass;
        aiMovement.maxRotAcc = aiData.stats.baseThrustRotational / aiData.stats.baseMass;

        EventComponent aiCommand = engine.createComponent(EventComponent.class);

        WeaponComponent aiWeapon = engine.createComponent(WeaponComponent.class);
        aiWeapon.shouldShoot = new boolean[aiData.stats.weaponSlots];
        aiWeapon.shotTimer = new float[aiData.stats.weaponSlots];
        aiWeapon.stats = new WeaponStatHolder[aiData.stats.weaponSlots];
        for(int i = 0; i < aiData.stats.weaponSlots; i++)
        {
            aiWeapon.shouldShoot[i] = false;
            aiWeapon.shotTimer[i] = 0f;
        }
        aiWeapon.stats[0] = WeaponStats.getStats("KY1");
        aiWeapon.stats[1] = WeaponStats.getStats("KY1");
        aiWeapon.owner = this.AI_SHIP;
        aiWeapon.engine = this.engine;
        aiWeapon.offset = aiData.stats.weaponPositions;
        aiWeapon.target = this.SHIP;
        aiWeapon.shouldLeadShots = true;

        SteeringAIComponent aiSteering = engine.createComponent(SteeringAIComponent.class);
        aiSteering.entity = this.AI_SHIP;
        aiSteering.behavior = new Seek<Vector2>(aiSteering, steering);
        aiSteering.steeringAcceleration = new SteeringAcceleration<Vector2>(new Vector2(0, 0), 0f);

        this.AI_SHIP.add(aiTransform).add(aiVisual).add(aiMovement).add(aiData).add(aiCommand).add(aiWeapon).add(aiSteering);

        //endregion AI_SHIP

        CameraComponent camera = engine.createComponent(CameraComponent.class);
        camera.camera = new OrthographicCamera();
        camera.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.camera.zoom = 1f;
        camera.viewport = new StretchViewport(Settings.GAME_SCREEN_WIDTH, Settings.GAME_SCREEN_HEIGHT, camera.camera);
        camera.entity = this.SHIP;
        this.CAMERA.add(camera);

        this.engine.addEntity(this.SHIP);
        this.engine.addEntity(this.AI_SHIP);
        this.engine.addEntity(this.CAMERA);
        this.engine.addSystem(new RenderSystem(0));
        this.engine.addSystem(new ShipInputSystem(1));
        this.engine.addSystem(new SteeringAISystem(this.random, 2));
        this.engine.addSystem(new EventSystem(3));
        this.engine.addSystem(new MovementSystem(4));
        this.engine.addSystem(new BulletSystem(5));
        this.engine.addSystem(new WeaponSystem(this.spacedOut.assets, this.random, 6));
        this.engine.addSystem(new CameraSystem(7));
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
        this.logger.log();

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
