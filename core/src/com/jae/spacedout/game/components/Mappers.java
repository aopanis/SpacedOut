package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.ComponentMapper;

//all component mappers for easy access
public class Mappers
{
    //data
    public static final ComponentMapper<DataComponent> data = ComponentMapper.getFor(DataComponent.class);
    public static final ComponentMapper<TeamComponent> team = ComponentMapper.getFor(TeamComponent.class);

    //game logic
    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<MovementComponent> movement = ComponentMapper.getFor(MovementComponent.class);
    public static final ComponentMapper<EventComponent> command = ComponentMapper.getFor(EventComponent.class);
    public static final ComponentMapper<InputComponent> input = ComponentMapper.getFor(InputComponent.class);

    //rendering
    public static final ComponentMapper<VisualComponent> visual = ComponentMapper.getFor(VisualComponent.class);
    public static final ComponentMapper<CameraComponent> camera = ComponentMapper.getFor(CameraComponent.class);
    public static final ComponentMapper<ParticleComponent> particle = ComponentMapper.getFor(ParticleComponent.class);

    //ships
    public static final ComponentMapper<WeaponComponent> weapon = ComponentMapper.getFor(WeaponComponent.class);
    public static final ComponentMapper<BulletComponent> bullet = ComponentMapper.getFor(BulletComponent.class);
    public static final ComponentMapper<SteeringAIComponent> steeringAI = ComponentMapper.getFor(SteeringAIComponent.class);
    public static final ComponentMapper<BoundingComponent> bounding = ComponentMapper.getFor(BoundingComponent.class);
}