package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.ComponentMapper;

//all component mappers for easy access
public class Mappers
{
    //game logic
    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<PhysicsComponent> physics = ComponentMapper.getFor(PhysicsComponent.class);

    //rendering
    public static final ComponentMapper<VisualComponent> visual = ComponentMapper.getFor(VisualComponent.class);

    //instruction
    public static final ComponentMapper<CommandComponent> command = ComponentMapper.getFor(CommandComponent.class);
}