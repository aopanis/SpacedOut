package com.jae.spacedout.utility;

import com.badlogic.gdx.Input.Keys;

public class Settings
{
    //region screen

    public static final int GAME_SCREEN_WIDTH = 960;
    public static final int GAME_SCREEN_HEIGHT = 540;

    public static float minZoom = 0.5f;
    public static float maxZoom = 2.0f;
    public static float zoomSpeed = 0.5f;

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

    public static int shootWeapon = Keys.SPACE;

    //endregion key_maps

    //region graphical

    public static int maxStars = 40;
    public static float starMaxDuration = 5f;
    public static float starMinDuration = 2f;
    public static float starMinScale = 0.5f;
    public static float starMaxScale = 1.5f;

    //endregion graphical

    //region textures

    public static final String STAR_1 = "android/assets/environment/star1.png";
    public static final String STAR_2 = "android/assets/environment/star2.png";
    public static final String STAR_3 = "android/assets/environment/star3.png";
    public static final String STAR_4 = "android/assets/environment/star4.png";
    public static final String STAR_5 = "android/assets/environment/star5.png";
    public static final String STAR_6 = "android/assets/environment/star6.png";

    public static final String SHIP = "android/assets/debug/ship.png";

    public static final String LASER_1 = "android/assets/ships/projectiles/laser.png";
    public static final String LASER_2 = "android/assets/ships/projectiles/heavyLaser.png";

    //endregion textures

    //region gameplay

    public static boolean shouldLeadShots = true;

    //endregion gameplay
}
