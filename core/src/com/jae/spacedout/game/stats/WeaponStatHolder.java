package com.jae.spacedout.game.stats;

public class WeaponStatHolder
{
    public String bulletTexPath;
    public String name;
    public float maxRoundsPerSecond;
    public int energyRequirementPerShot;
    public int baseMass;
    public int baseDamage;
    public float accuracy;
    public float rangeOfMotion;
    public float roundVelocity;
    public float maxDistance;

    public WeaponStatHolder(String bulletTexPath, String name, float maxRoundsPerSecond, int energyRequirementPerShot,
                            int baseMass, int baseDamage, float accuracy, float rangeOfMotion, float roundVelocity, float maxDistance)
    {
        this.bulletTexPath = bulletTexPath;
        this.name = name;
        this.maxRoundsPerSecond = maxRoundsPerSecond;
        this.energyRequirementPerShot = energyRequirementPerShot;
        this.baseMass = baseMass;
        this.baseDamage = baseDamage;
        this.accuracy = accuracy;
        this.rangeOfMotion = rangeOfMotion;
        this.roundVelocity = roundVelocity;
        this.maxDistance = maxDistance;
    }
}
