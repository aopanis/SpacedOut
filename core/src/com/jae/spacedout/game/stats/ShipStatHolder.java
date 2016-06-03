package com.jae.spacedout.game.stats;

import com.badlogic.gdx.math.Vector2;

public class ShipStatHolder
{
    public String texPath;
    public String name;
    public int weaponSlots;
    public int utilitySlots;
    public int baseHealth;
    public int baseMass;
    public int baseThrustLinear;
    public int baseThrustLateral;
    public int baseThrustRotational;
    public int baseOscillatorCapacity;
    public Vector2[] weaponPositions;

    public ShipStatHolder(String texPath, String name, int weaponSlots, int utilitySlots, int baseHealth, int baseMass,
                 int baseThrustLinear, int baseThrustLateral, int baseThrustRotational, int baseOscillatorCapacity, Vector2[] weaponPositions)
    {
        this.texPath = texPath;
        this.name = name;
        this.weaponSlots = weaponSlots;
        this.utilitySlots = utilitySlots;
        this.baseHealth = baseHealth;
        this.baseMass = baseMass;
        this.baseThrustLinear = baseThrustLinear;
        this.baseThrustLateral = baseThrustLateral;
        this.baseThrustRotational = baseThrustRotational;
        this.baseOscillatorCapacity = baseOscillatorCapacity;
        this.weaponPositions = weaponPositions;
    }
}
