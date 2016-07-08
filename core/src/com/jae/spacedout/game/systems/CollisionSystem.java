package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.jae.spacedout.game.components.BoundingComponent;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.utility.Quadtree;

public class CollisionSystem extends IteratingSystem
{
    private BoundingComponent bounding;
    private Quadtree quadtree;

    public CollisionSystem(int priority)
    {
        super(Family.all(BoundingComponent.class).get(), priority);

        //initialize new quadtree with dimensions equal to arena size
        this.quadtree = new Quadtree(0, new Rectangle(0, 0, 10000, 10000), null);
    }

    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.bounding = Mappers.bounding.get(entity);

        if(this.bounding.shouldRegister)
        {
            this.quadtree.insert(entity);
            this.bounding.shouldRegister = false;
        }

        //move the entity within the quadtree
        this.quadtree.moveEntity(entity);
    }
}
