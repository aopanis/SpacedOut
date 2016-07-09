package com.jae.spacedout.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jae.spacedout.SpacedOut;
import com.jae.spacedout.utility.Settings;

public class DesktopLauncher
{
    public static void main(String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Settings.GAME_SCREEN_WIDTH;
        config.height = Settings.GAME_SCREEN_HEIGHT;
        config.resizable = true;
        new LwjglApplication(new SpacedOut(), config);
    }
}
