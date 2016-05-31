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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jae.spacedout.SpacedOut;
import com.jae.spacedout.game.components.CameraComponent;
import com.jae.spacedout.game.components.CommandComponent;
import com.jae.spacedout.game.components.DataComponent;
import com.jae.spacedout.game.components.InputComponent;
import com.jae.spacedout.game.components.PhysicsComponent;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.game.components.VisualComponent;
import com.jae.spacedout.game.systems.CameraSystem;
import com.jae.spacedout.game.systems.CommandSystem;
import com.jae.spacedout.game.systems.GameInputSystem;
import com.jae.spacedout.game.systems.PhysicsSystem;
import com.jae.spacedout.game.systems.RenderSystem;
import com.jae.spacedout.utility.Settings;

public class GameScreen implements Screen
{
    //ECS engine and game class
    private final PooledEngine engine;
    private final SpacedOut spacedOut;
    //box2d world
    private World world;

    /** TESTING **/
    public Entity SHIP;
    public Entity CAMERA;
    /** TESTING **/

    public GameScreen(PooledEngine engine, SpacedOut spacedOut)
    {
        this.engine = engine;
        this.spacedOut = spacedOut;

        this.world = new World(new Vector2(0, 0), true);

        /**TESTING**/
        this.SHIP = this.engine.createEntity();
        this.CAMERA = this.engine.createEntity();

        TransformComponent transform = engine.createComponent(TransformComponent.class);
        transform.x = Settings.GAME_SCREEN_WIDTH / 2;
        transform.y = Settings.GAME_SCREEN_HEIGHT / 2;

        VisualComponent visual = engine.createComponent(VisualComponent.class);
        visual.textureRegion = new TextureRegion(new Texture(Gdx.files.internal("debug/ship.png")));
        visual.originX = visual.textureRegion.getRegionWidth() / 2;
        visual.originY = visual.textureRegion.getRegionHeight() / 2;

        PhysicsComponent physics = engine.createComponent(PhysicsComponent.class);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(Settings.GAME_SCREEN_WIDTH / 2, Settings.GAME_SCREEN_HEIGHT / 2);
        physics.body = this.world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        Vector2[] vertices = { new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2() };
        vertices[0].set(0, 7);
        vertices[1].set(-4, 0);
        vertices[2].set(-1, -7);
        vertices[3].set(1, -7);
        vertices[4].set(4, 0);
        shape.set(vertices);
        Fixture fixture = physics.body.createFixture(shape, 500f);
        shape.dispose();

        InputComponent input = engine.createComponent(InputComponent.class);

        DataComponent data = engine.createComponent(DataComponent.class);
        data.hitpoints = 500;
        data.linearThrust = 50000;
        data.lateralThrust = 20000;
        data.rotationalThrust = 40000;

        CommandComponent command = engine.createComponent(CommandComponent.class);

        this.SHIP.add(transform);
        this.SHIP.add(visual);
        this.SHIP.add(physics);
        this.SHIP.add(input);
        this.SHIP.add(data);
        this.SHIP.add(command);

        CameraComponent camera = engine.createComponent(CameraComponent.class);
        camera = new CameraComponent(this.SHIP);
        this.CAMERA.add(camera);

        this.engine.addEntity(this.SHIP);
        this.engine.addEntity(this.CAMERA);
        this.engine.addSystem(new CameraSystem(4));
        this.engine.addSystem(new RenderSystem(0));
        this.engine.addSystem(new GameInputSystem(1));
        this.engine.addSystem(new CommandSystem(2));
        this.engine.addSystem(new PhysicsSystem(this.world, 3));
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
