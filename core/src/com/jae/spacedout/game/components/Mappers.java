package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.ComponentMapper;

//all component mappers for easy access
public class Mappers
{
    //data
    public static final ComponentMapper<DataComponent> data = ComponentMapper.getFor(DataComponent.class);

    //game logic
    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<MovementComponent> movement = ComponentMapper.getFor(MovementComponent.class);
    public static final ComponentMapper<CommandComponent> command = ComponentMapper.getFor(CommandComponent.class);
    public static final ComponentMapper<InputComponent> input = ComponentMapper.getFor(InputComponent.class);

    //rendering
    public static final ComponentMapper<VisualComponent> visual = ComponentMapper.getFor(VisualComponent.class);
    public static final ComponentMapper<CameraComponent> camera = ComponentMapper.getFor(CameraComponent.class);
    public static final ComponentMapper<ParticleComponent> particle = ComponentMapper.getFor(ParticleComponent.class);
}