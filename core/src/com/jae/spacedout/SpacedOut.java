package com.jae.spacedout;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.jae.spacedout.game.systems.MovementSystem;

public class SpacedOut extends Game
{
    //engine for ECS
    PooledEngine engine;
    //all systems to be added to engien
    MovementSystem movementSystem;

    @Override
    public void create()
    {
        this.engine = new PooledEngine(10, 5000, 10, 5000);
        this.movementSystem = new MovementSystem();
        this.engine.addSystem(this.movementSystem);
    }

    @Override
    public void render()
    {
        super.render();
    }
}
