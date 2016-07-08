package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.jae.spacedout.game.components.BulletComponent;
import com.jae.spacedout.game.components.DataComponent;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.MovementComponent;
import com.jae.spacedout.game.components.TeamComponent;
import com.jae.spacedout.game.components.WeaponComponent;

public class BulletSystem extends IteratingSystem
{
    private BulletComponent bullet;
    private DataComponent shipData;
    private TeamComponent team;

    public BulletSystem(int priority)
    {
        super(Family.all(BulletComponent.class, MovementComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.bullet = Mappers.bullet.get(entity);
        WeaponComponent weaponComponent = Mappers.weapon.get(this.bullet.owner);
        this.shipData = Mappers.data.get(weaponComponent.owner);
        this.team = Mappers.team.get(weaponComponent.owner);

        if(this.bullet.distaceTraveled > this.bullet.stats.maxDistance)
        {
            this.getEngine().removeEntity(entity);
        }
    }
}
