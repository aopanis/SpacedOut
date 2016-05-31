package com.jae.spacedout;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.jae.spacedout.screens.GameScreen;

public class SpacedOut extends Game
{
    //engine for ECS
    PooledEngine engine;

    @Override
    public void create()
    {
        this.engine = new PooledEngine(10, 5000, 10, 5000);
        this.setScreen(new GameScreen(this.engine, this));
    }

    @Override
    public void render()
    {
        super.render();
    }
}
