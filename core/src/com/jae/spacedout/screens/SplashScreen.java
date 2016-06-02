package com.jae.spacedout.screens;

import com.badlogic.gdx.Screen;
import com.jae.spacedout.SpacedOut;

public class SplashScreen implements Screen
{
    private SpacedOut spacedOut;

    public SplashScreen(SpacedOut spacedOut)
    {
        this.spacedOut = spacedOut;
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        if(this.spacedOut.assets.update())
        {
            this.spacedOut.setScreen(new GameScreen(this.spacedOut.engine, this.spacedOut, this.spacedOut.random));
        }
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }
}
