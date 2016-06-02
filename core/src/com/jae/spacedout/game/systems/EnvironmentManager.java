package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jae.spacedout.game.components.Mappers;
import com.jae.spacedout.game.components.ParticleComponent;
import com.jae.spacedout.game.components.TransformComponent;
import com.jae.spacedout.game.components.VisualComponent;
import com.jae.spacedout.utility.Settings;
import com.jae.spacedout.utility.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class EnvironmentManager
{
    private ArrayList<Entity> activeStars;
    private ArrayList<Entity> inactiveStars;
    private TextureRegion[] textures;
    private Random random;
    private PooledEngine engine;
    private Iterator<Entity> iterator;
    //the entity to follow
    private Entity entity;
    //entity to use as star
    private Entity star;

    private TransformComponent transform;
    private VisualComponent visual;
    private ParticleComponent particle;

    public EnvironmentManager(PooledEngine engine, Entity entity, Random random, TextureRegion[] textures)
    {
        this.activeStars = new ArrayList<Entity>();
        this.inactiveStars = new ArrayList<Entity>();
        this.textures = textures;
        this.random = random;
        this.engine = engine;
        this.entity = entity;
    }

    public void update(float delta)
    {
        this.iterator = this.activeStars.iterator();
        while(this.iterator.hasNext())
        {
            this.star = this.iterator.next();
            this.particle = Mappers.particle.get(this.star);
            this.particle.lifeTimer += delta;

            this.transform = Mappers.transform.get(this.star);
            TransformComponent player = Mappers.transform.get(this.entity);

            if(this.particle.lifeTimer > this.particle.maxTimer ||
                    Utils.findDistanceSquared(this.transform.x, this.transform.y, player.x, player.y) > Settings.GAME_SCREEN_HEIGHT * Settings.GAME_SCREEN_WIDTH)
            {
                this.inactiveStars.add(this.star);
                this.engine.removeEntity(this.star);
                this.iterator.remove();
            }
        }

        while(this.activeStars.size() < Settings.maxStars)
        {
            this.star = this.getStar();
            this.transform = Mappers.transform.get(this.star);
            this.visual = Mappers.visual.get(this.star);
            this.particle = Mappers.particle.get(this.star);

            //set random rotation for star
            this.transform.rotation = this.random.nextInt(360);
            //set star location based on player location
            TransformComponent player = Mappers.transform.get(this.entity);
            this.transform.x = player.x + this.random.nextInt((int)((float)Settings.GAME_SCREEN_WIDTH * 1.5f)) - (Settings.GAME_SCREEN_WIDTH * 0.75f);
            this.transform.y = player.y + this.random.nextInt((int)((float)Settings.GAME_SCREEN_HEIGHT * 1.5f)) - (Settings.GAME_SCREEN_HEIGHT * 0.75f);

            this.visual.textureRegion = this.textures[this.random.nextInt(this.textures.length)];
            this.visual.scaleX = this.visual.scaleY = Utils.mapRange(0, 1, Settings.starMinScale, Settings.starMaxScale, this.random.nextFloat());

            this.particle.lifeTimer = 0;
            this.particle.maxTimer = Utils.mapRange(0f, 1f, Settings.starMinDuration, Settings.starMaxDuration, this.random.nextFloat());

            this.activeStars.add(this.star);
            this.engine.addEntity(this.star);
        }
    }

    private Entity getStar()
    {
        TransformComponent transform = this.engine.createComponent(TransformComponent.class);
        VisualComponent visual = this.engine.createComponent(VisualComponent.class);
        ParticleComponent particle = this.engine.createComponent(ParticleComponent.class);

        if(inactiveStars.size() > 0)
        {
            for(Entity entity : this.inactiveStars)
            {
                if(entity != null)
                {
                    entity.add(transform).add(visual).add(particle);
                    this.inactiveStars.remove(entity);
                    return entity;
                }
            }
        }

        Entity entity = this.engine.createEntity();
        entity.add(transform).add(visual).add(particle);
        return entity;
    }
}
