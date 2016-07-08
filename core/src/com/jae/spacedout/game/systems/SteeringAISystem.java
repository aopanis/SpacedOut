package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jae.spacedout.game.events.MoveEvent;
import com.jae.spacedout.game.events.RotateEvent;
import com.jae.spacedout.game.events.ShootEvent;
import com.jae.spacedout.game.components.EventComponent;
import com.jae.spacedout.game.components.InputComponent;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.SteeringAIComponent;
import com.jae.spacedout.game.components.Mappers;

import java.util.Random;

public class SteeringAISystem extends IteratingSystem
{
    private SteeringAIComponent steeringAI;
    private EventComponent command;
    private MovementComponent movement;

    private EventSystem commandSystem;
    private Random random;

    public SteeringAISystem(Random random, int priority)
    {
        super(Family.all(SteeringAIComponent.class, EventComponent.class, MovementComponent.class).exclude(InputComponent.class).get(), priority);

        this.random = random;
        this.priority = priority;
    }

    //runs through each entity with an ai component and steers in accordingly
    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.steeringAI = Mappers.steeringAI.get(entity);
        this.command = Mappers.command.get(entity);
        this.movement = Mappers.movement.get(entity);
        this.commandSystem = this.getEngine().getSystem(EventSystem.class);

        if(this.steeringAI.behavior != null)
        {
            this.steeringAI.behavior.calculateSteering(this.steeringAI.steeringAcceleration);
            this.applySteering(this.steeringAI.steeringAcceleration, entity, dt);
        }

        ShootEvent shoot = new ShootEvent();
        this.command.events.add(shoot);
    }

    private void applySteering(SteeringAcceleration<Vector2> steering, Entity entity, float dt)
    {
        if(!steering.linear.isZero())
        {
            MoveEvent command = this.commandSystem.createCommand(MoveEvent.class);
            command.x = steering.linear.x * dt;
            command.y = steering.linear.y * dt;
            this.command.events.add(command);
        }

        if(steering.angular != 0)
        {
            RotateEvent command = this.commandSystem.createCommand(RotateEvent.class);
            command.rotation = steering.angular * dt;
            this.command.events.add(command);
        }
        else
        {
            if (!steering.linear.isZero())
            {
                float newOrientation = this.steeringAI.vectorToAngle(steering.linear) - (float)Math.PI / 2;
                /*
                newOrientation = (float)Math.toDegrees(newOrientation) - Mappers.transform.get(steeringAI.entity).rotation;
                RotateEvent command = this.commandSystem.createCommand(RotateEvent.class);
                command.rotation = newOrientation;
                command.dt = dt;
                this.command.events.add(command);*/
                Mappers.transform.get(entity).rotation = newOrientation * MathUtils.radiansToDegrees;
            }
        }
    }
}
