package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.jae.spacedout.game.commands.ForceCommand;
import com.jae.spacedout.game.commands.ImpulseCommand;
import com.jae.spacedout.game.components.CommandComponent;
import com.jae.spacedout.game.components.DataComponent;
import com.jae.spacedout.game.components.InputComponent;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.PhysicsComponent;
import com.jae.spacedout.utility.Settings;

public class GameInputSystem extends IteratingSystem
{
    private CommandComponent command;
    private DataComponent data;
    private PhysicsComponent physics;

    public GameInputSystem(int priority)
    {
        super(Family.all(CommandComponent.class, PhysicsComponent.class, InputComponent.class, DataComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.command = Mappers.command.get(entity);
        this.data = Mappers.data.get(entity);
        this.physics = Mappers.physics.get(entity);

        if(Gdx.input.isKeyPressed(Settings.forward))
        {
            this.command.commands.add(new ForceCommand((float)(Math.cos(this.physics.body.getAngle() + Math.PI / 2) * -this.data.linearThrust),
                    (float)(Math.sin(this.physics.body.getAngle() + Math.PI / 2) * this.data.linearThrust)));
        }
        if(Gdx.input.isKeyPressed(Settings.back))
        {
            this.command.commands.add(new ForceCommand((float)(Math.cos(this.physics.body.getAngle() + Math.PI / 2) * -this.data.linearThrust),
                    (float)(Math.sin(this.physics.body.getAngle() + Math.PI / 2) * -this.data.linearThrust)));
        }
    }
}
