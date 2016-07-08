package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.jae.spacedout.game.events.MoveEvent;
import com.jae.spacedout.game.events.RotateEvent;
import com.jae.spacedout.game.events.ShootEvent;
import com.jae.spacedout.game.components.EventComponent;
import com.jae.spacedout.game.components.DataComponent;
import com.jae.spacedout.game.components.InputComponent;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.utility.Settings;

public class ShipInputSystem extends IteratingSystem
{
    private EventComponent command;
    private DataComponent data;
    private TransformComponent transform;

    private EventSystem commandSystem;

    public ShipInputSystem(int priority)
    {
        super(Family.all(EventComponent.class, MovementComponent.class, InputComponent.class, DataComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.command = Mappers.command.get(entity);
        this.data = Mappers.data.get(entity);
        this.transform = Mappers.transform.get(entity);
        this.commandSystem = this.getEngine().getSystem(EventSystem.class);

        //region movement control

        if(Gdx.input.isKeyPressed(Settings.forward))
        {
            MoveEvent command = this.commandSystem.createCommand(MoveEvent.class);
            command.x = (float)(Math.cos(Math.toRadians(this.transform.rotation + 90)) * (this.data.stats.baseThrustLinear / this.data.stats.baseMass) * dt);
            command.y = (float)(Math.sin(Math.toRadians(this.transform.rotation + 90)) * (this.data.stats.baseThrustLinear / this.data.stats.baseMass) * dt);
            this.command.events.add(command);
        }
        if(Gdx.input.isKeyPressed(Settings.back))
        {
            MoveEvent command = this.commandSystem.createCommand(MoveEvent.class);
            command.x = (float)(Math.cos(Math.toRadians(this.transform.rotation + 90)) * -(this.data.stats.baseThrustLinear / this.data.stats.baseMass) * dt);
            command.y = (float)(Math.sin(Math.toRadians(this.transform.rotation + 90)) * -(this.data.stats.baseThrustLinear / this.data.stats.baseMass) * dt);
            this.command.events.add(command);
        }
        if(Gdx.input.isKeyPressed(Settings.left))
        {
            RotateEvent command = this.commandSystem.createCommand(RotateEvent.class);
            command.rotation = (this.data.stats.baseThrustRotational / this.data.stats.baseMass) * dt;
            command.dt = dt;
            this.command.events.add(command);
        }
        if(Gdx.input.isKeyPressed(Settings.right))
        {
            RotateEvent command = this.commandSystem.createCommand(RotateEvent.class);
            command.rotation = -(this.data.stats.baseThrustRotational / this.data.stats.baseMass) * dt;
            command.dt = dt;
            this.command.events.add(command);
        }
        if(Gdx.input.isKeyPressed(Settings.strafeLeft))
        {
            MoveEvent command = this.commandSystem.createCommand(MoveEvent.class);
            command.x = (float)(Math.cos(Math.toRadians(this.transform.rotation + 180)) * (this.data.stats.baseThrustLateral / this.data.stats.baseMass) * dt);
            command.y = (float)(Math.sin(Math.toRadians(this.transform.rotation + 180)) * (this.data.stats.baseThrustLateral / this.data.stats.baseMass) * dt);
            this.command.events.add(command);
        }
        if(Gdx.input.isKeyPressed(Settings.strafeRight))
        {
            MoveEvent command = this.commandSystem.createCommand(MoveEvent.class);
            command.x = (float)(Math.cos(Math.toRadians(this.transform.rotation + 180)) * -(this.data.stats.baseThrustLateral / this.data.stats.baseMass) * dt);
            command.y = (float)(Math.sin(Math.toRadians(this.transform.rotation + 180)) * -(this.data.stats.baseThrustLateral / this.data.stats.baseMass) * dt);
            this.command.events.add(command);
        }

        //endregion movement control

        if(Gdx.input.isKeyPressed(Settings.shootWeapon))
        {
            ShootEvent command = this.commandSystem.createCommand(ShootEvent.class);
            this.command.events.add(command);
        }
    }
}
