package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.jae.spacedout.game.commands.Command;
import com.jae.spacedout.game.commands.MoveCommand;
import com.jae.spacedout.game.commands.RotateCommand;
import com.jae.spacedout.game.components.CommandComponent;
import com.jae.spacedout.game.components.DataComponent;
import com.jae.spacedout.game.components.InputComponent;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.utility.Settings;

public class ShipInputSystem extends IteratingSystem
{
    private CommandComponent command;
    private DataComponent data;
    private TransformComponent transform;

    private CommandSystem commandSystem;
    private Command addedCommand;

    public ShipInputSystem(int priority)
    {
        super(Family.all(CommandComponent.class, MovementComponent.class, InputComponent.class, DataComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.command = Mappers.command.get(entity);
        this.data = Mappers.data.get(entity);
        this.transform = Mappers.transform.get(entity);

        this.commandSystem = this.getEngine().getSystem(CommandSystem.class);

        if(Gdx.input.isKeyPressed(Settings.forward))
        {
            this.command.commands.add(new MoveCommand((float)(Math.cos(Math.toRadians(this.transform.rotation + 90)) * this.data.linearThrust * dt),
                    (float)(Math.sin(Math.toRadians(this.transform.rotation + 90)) * this.data.linearThrust * dt)));
        }
        if(Gdx.input.isKeyPressed(Settings.back))
        {
            this.command.commands.add(new MoveCommand((float)(Math.cos(Math.toRadians(this.transform.rotation + 90)) * -this.data.linearThrust * dt),
                    (float)(Math.sin(Math.toRadians(this.transform.rotation + 90)) * -this.data.linearThrust * dt)));
        }
        if(Gdx.input.isKeyPressed(Settings.left))
        {
            this.command.commands.add(new RotateCommand(this.data.rotationalThrust * dt));
        }
        if(Gdx.input.isKeyPressed(Settings.right))
        {
            this.command.commands.add(new RotateCommand(-this.data.rotationalThrust * dt));
        }
        if(Gdx.input.isKeyPressed(Settings.strafeLeft))
        {
            this.command.commands.add(new MoveCommand((float)(Math.cos(Math.toRadians(this.transform.rotation + 180)) * this.data.linearThrust * dt),
                    (float)(Math.sin(Math.toRadians(this.transform.rotation + 180)) * this.data.linearThrust * dt)));
        }
        if(Gdx.input.isKeyPressed(Settings.strafeRight))
        {
            this.command.commands.add(new MoveCommand((float)(Math.cos(Math.toRadians(this.transform.rotation + 180)) * -this.data.linearThrust * dt),
                    (float)(Math.sin(Math.toRadians(this.transform.rotation + 180)) * -this.data.linearThrust * dt)));
        }
    }
}
