package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.jae.spacedout.game.commands.Command;

import java.util.ArrayList;

public class CommandComponent implements Component, Poolable
{
    public ArrayList<Command> commands = new ArrayList<Command>(64);

    //default constructor
    public CommandComponent()
    {

    }

    @Override
    public void reset()
    {
        commands = new ArrayList<Command>(64);
    }
}
