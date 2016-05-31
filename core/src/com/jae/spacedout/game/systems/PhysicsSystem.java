package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.PhysicsComponent;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.utility.Utils;

public class PhysicsSystem extends IteratingSystem
{
    private PhysicsComponent physics;
    private TransformComponent transform;

    //box2d world
    private World world;

    //for even world steps
    private static final float MAX_STEP_TIME = 1/40f;
    private float accumulator = 0f;

    public PhysicsSystem(World world, int priority)
    {
        super(Family.all(PhysicsComponent.class, TransformComponent.class).get(), priority);

        this.world = world;
    }

    @Override
    public void update(float dt)
    {
        this.accumulator += Math.min(dt, 0.25f);
        if(this.accumulator >= PhysicsSystem.MAX_STEP_TIME)
        {
            this.world.step(PhysicsSystem.MAX_STEP_TIME, 6, 2);
            this.accumulator -= PhysicsSystem.MAX_STEP_TIME;
            super.update(dt);
        }
    }

    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.transform = Mappers.transform.get(entity);
        this.physics = Mappers.physics.get(entity);
        this.transform.x = this.physics.body.getPosition().x;
        this.transform.y = this.physics.body.getPosition().y;
        this.transform.rotation = Utils.normalizeAngle((float)Math.toDegrees(this.physics.body.getAngle()));
    }
}
