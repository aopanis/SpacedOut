package com.jae.spacedout;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.jae.spacedout.screens.GameScreen;
import com.jae.spacedout.screens.SplashScreen;
import com.jae.spacedout.utility.Assets;

import java.util.Random;

public class SpacedOut extends Game
{
    //engine for ECS
    public PooledEngine engine;
    public Random random;
    public Assets assets;

    @Override
    public void create()
    {
        this.engine = new PooledEngine(10, 5000, 10, 5000);
        this.random = new Random();
        this.assets = new Assets();
        this.setScreen(new SplashScreen(this));
    }

    @Override
    public void render()
    {
        super.render();
    }
}
