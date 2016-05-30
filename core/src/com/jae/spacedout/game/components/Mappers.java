package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.ComponentMapper;

//all component mappers for easy access
public class Mappers
{
    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<MovementComponent> movement = ComponentMapper.getFor(MovementComponent.class);
    public static final ComponentMapper<VisualComponent> visual = ComponentMapper.getFor(VisualComponent.class);
}