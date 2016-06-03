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

public class ParticleManager
{
    private ArrayList<Entity> particles;
    private TextureRegion[] textures;
    private Random random;
    private PooledEngine engine;
    private Iterator<Entity> iterator;
    //the entity to follow
    private Entity entity;
    //entity to use as star
    private Entity currentParticle;

    //spawn data
    private float maxRange;
    private float minScale;
    private float maxScale;
    private float minRotation;
    private float maxRotation;
    private float despawnRange;
    private float minDuration;
    private float maxDuration;

    private TransformComponent transform;
    private VisualComponent visual;
    private ParticleComponent particle;

    public ParticleManager(PooledEngine engine, Entity entity, Random random, TextureRegion[] textures,
                           float maxRange, float minScale, float maxScale, float minRotation, float maxRotation, float despawnRange, float minDuration, float maxDuration)
    {
        this.particles = new ArrayList<Entity>();
        this.textures = textures;
        this.random = random;
        this.engine = engine;
        this.entity = entity;

        this.maxRange = maxRange;
        this.minScale = minScale;
        this.maxScale = maxScale;
        this.minRotation = minRotation;
        this.maxRotation = maxRotation;
        this.despawnRange = despawnRange;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
    }

    public void update(float delta)
    {
        this.iterator = this.particles.iterator();
        while(this.iterator.hasNext())
        {
            this.currentParticle = this.iterator.next();
            this.particle = Mappers.particle.get(this.currentParticle);
            this.transform = Mappers.transform.get(this.currentParticle);
            TransformComponent follow = Mappers.transform.get(this.entity);

            this.particle.lifeTimer += delta;

            if(this.particle.lifeTimer > this.particle.maxTimer ||
                    Utils.findDistanceSquared(this.transform.x, this.transform.y, follow.x, follow.y) > this.despawnRange * this.despawnRange)
            {
                this.engine.removeEntity(this.currentParticle);
                this.iterator.remove();
            }
        }

        while(this.particles.size() < Settings.maxStars)
        {
            this.currentParticle = this.getParticle();
            this.transform = Mappers.transform.get(this.currentParticle);
            this.visual = Mappers.visual.get(this.currentParticle);
            this.particle = Mappers.particle.get(this.currentParticle);

            //set random rotation for star
            this.transform.rotation = Utils.mapRange(0f, 259f, this.minRotation, this.maxRotation, this.random.nextInt(360));
            //set star location based on player location via polar coordinates
            TransformComponent follow = Mappers.transform.get(this.entity);
            float theta = this.random.nextInt(360);
            float radius = Utils.mapRange(0f, 1f, 0f, this.maxRange, this.random.nextFloat());
            this.transform.x = follow.x + (float)Math.cos(theta) * radius;
            this.transform.y = follow.y + (float)Math.sin(theta) * radius;

            this.visual.textureRegion = this.textures[this.random.nextInt(this.textures.length)];
            this.visual.scaleX = this.visual.scaleY = Utils.mapRange(0f, 1f, this.minScale, this.maxScale, this.random.nextFloat());
            this.visual.depth = -2;

            this.particle.lifeTimer = 0;
            this.particle.maxTimer = Utils.mapRange(0f, 1f, this.minDuration, this.maxDuration, this.random.nextFloat());

            this.engine.addEntity(this.currentParticle);
            this.particles.add(this.currentParticle);
        }
    }

    private Entity getParticle()
    {
        TransformComponent transform = this.engine.createComponent(TransformComponent.class);
        VisualComponent visual = this.engine.createComponent(VisualComponent.class);
        ParticleComponent particle = this.engine.createComponent(ParticleComponent.class);
        Entity entity = this.engine.createEntity();
        entity.removeAll();
        entity.add(transform).add(visual).add(particle);
        return entity;
    }
}
