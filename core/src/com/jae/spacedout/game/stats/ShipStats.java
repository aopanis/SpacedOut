package com.jae.spacedout.game.stats;

import com.badlogic.gdx.math.Vector2;

public class ShipStats
{
    private static final Vector2[] JM1positions = { new Vector2(-3, 2), new Vector2(3, 2) };
    private static final ShipStatHolder shipJM1 = new ShipStatHolder("debug/ship.png", "JM1", 2, 2, 500, 50000,
            4000000, 3000000, 4000000, 500, ShipStats.JM1positions);

    public static final ShipStatHolder[] ships = {
            ShipStats.shipJM1
    };

    public static ShipStatHolder getStats(String name)
    {
        for (ShipStatHolder stats : ShipStats.ships)
        {
            if(stats.name.matches(name))
            {
                return stats;
            }
        }

        return ShipStats.shipJM1;
    }
}
