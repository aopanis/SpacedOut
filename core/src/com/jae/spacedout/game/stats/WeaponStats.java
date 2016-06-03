package com.jae.spacedout.game.stats;

public class WeaponStats
{
    private static final WeaponStatHolder weaponKN1 = new WeaponStatHolder("ships/projectiles/laser.png", "KN1", 4f, 2,
            2000, 20, 2f, 400f, 1000f);

    private static final WeaponStatHolder[] weapons = {
            WeaponStats.weaponKN1
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
