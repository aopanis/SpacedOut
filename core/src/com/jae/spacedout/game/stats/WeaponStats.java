package com.jae.spacedout.game.stats;

import com.jae.spacedout.utility.Settings;

public class WeaponStats
{
    private static final WeaponStatHolder weaponKN1 = new WeaponStatHolder(Settings.LASER_1, "KN1", 4f, 2,
            2000, 20, 1.6f, 5f, 600f, 2000f);

    private static final WeaponStatHolder weaponKY1 = new WeaponStatHolder(Settings.LASER_2, "KY1", 1.2f, 12,
            6000, 120, 2.4f, 10f, 400f, 2000f);

    public static final WeaponStatHolder[] weapons = {
            WeaponStats.weaponKN1,
            WeaponStats.weaponKY1
    };

    public static WeaponStatHolder getStats(String name)
    {
        for (WeaponStatHolder stats : WeaponStats.weapons)
        {
            if(stats.name.matches(name))
            {
                return stats;
            }
        }

        return WeaponStats.weaponKN1;
    }
}
