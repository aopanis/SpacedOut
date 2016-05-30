package com.jae.spacedout.utility;

import com.badlogic.gdx.Input.Keys;

public class Settings
{
    //region screen

    public static final int GAME_SCREEN_WIDTH = 960;
    public static final int GAME_SCREEN_HEIGHT = 540;

    private static float minZoom = 0.1f;
    public static float getMinZoom()
    {
        return Settings.minZoom;
    }
    public static void setMinZoom(float minZoom)
    {
        Settings.minZoom = minZoom;
    }
    private static float maxZoom = 2.0f;
    public static float getMaxZoom()
    {
        return Settings.maxZoom;
    }
    public static void setMaxZoom(float maxZoom)
    {
        Settings.maxZoom = maxZoom;
    }
    private static float zoomSpeed = 0.2f;
    public static float getZoomSpeed()
    {
        return Settings.zoomSpeed;
    }
    public static void setZoomSpeed(float zoomSpeed)
    {
        Settings.zoomSpeed = zoomSpeed;
    }

    //endregion screen

    //region key_maps

    public static int zoomIn = Keys.I;
    public static int zoomOut = Keys.O;

    public static int forward = Keys.W;
    public static int back = Keys.S;
    public static int left = Keys.A;
    public static int right = Keys.D;
    public static int strafeLeft = Keys.Q;
    public static int strafeRight = Keys.E;

    //endregion key_maps
}
