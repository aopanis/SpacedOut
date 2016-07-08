package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

//entity with AI MUST HAVE movement, transform, command
public class SteeringAIComponent implements Component, Poolable, Steerable<Vector2>
{
    public Entity entity;
    private boolean tagged;

    public SteeringBehavior<Vector2> behavior;
    //output acceleration
    public SteeringAcceleration<Vector2> steeringAcceleration;

    //default constructor
    public SteeringAIComponent()
    {

    }

    @Override
    public Vector2 getLinearVelocity()
    {
        MovementComponent movement = Mappers.movement.get(this.entity);
        return new Vector2(movement.velX, movement.velY);
    }

    @Override
    public float getAngularVelocity()
    {
        MovementComponent movement = Mappers.movement.get(this.entity);
        return movement.rotVel;
    }

    @Override
    public float getBoundingRadius()
    {
        return 0;
    }

    @Override
    public boolean isTagged()
    {
        return this.tagged;
    }

    @Override
    public void setTagged(boolean tagged)
    {
        this.tagged = tagged;
    }

    //speed threshold below which velocity is considered 0

    @Override
    public float getZeroLinearSpeedThreshold()
    {
        return 0.001f;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value)
    {
        System.out.println("Don't set zero linear speed threshold.");
    }

    @Override
    public float getMaxLinearSpeed()
    {
        MovementComponent movement = Mappers.movement.get(this.entity);
        return movement.maxVel;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed)
    {
        MovementComponent movement = Mappers.movement.get(this.entity);
        movement.maxVel = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration()
    {
        MovementComponent movement = Mappers.movement.get(this.entity);
        return movement.maxAcc;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration)
    {
        MovementComponent movement = Mappers.movement.get(this.entity);
        movement.maxAcc = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed()
    {
        MovementComponent movement = Mappers.movement.get(this.entity);
        return movement.maxRotVel;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed)
    {
        MovementComponent movement = Mappers.movement.get(this.entity);
        movement.maxRotVel = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration()
    {
        MovementComponent movement = Mappers.movement.get(this.entity);
        return movement.maxRotAcc;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration)
    {
        MovementComponent movement = Mappers.movement.get(this.entity);
        movement.maxRotAcc = maxAngularAcceleration;
    }

    @Override
    public Vector2 getPosition()
    {
        TransformComponent transform = Mappers.transform.get(this.entity);
        return new Vector2(transform.x, transform.y);
    }

    @Override
    public float getOrientation()
    {
        TransformComponent transform = Mappers.transform.get(entity);
        return (float)Math.toRadians(transform.rotation);
    }

    @Override
    public void setOrientation(float orientation)
    {
        TransformComponent transform = Mappers.transform.get(entity);
        transform.rotation = (float)Math.toDegrees(orientation);
    }

    @Override
    public float vectorToAngle(Vector2 vector)
    {
        return (float)Math.atan2(vector.y, vector.x);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle)
    {
        outVector.x = (float)Math.cos(angle);
        outVector.y = (float)Math.sin(angle);
        return outVector;
    }

    @Override
    public Location<Vector2> newLocation()
    {
        System.out.println("https://github.com/libgdx/gdx-ai/blob/master/tests/src/com/badlogic/gdx/ai/tests/steer/box2d/Box2dLocation.java");
        return null;
    }

    @Override
    public void reset()
    {

    }
}
