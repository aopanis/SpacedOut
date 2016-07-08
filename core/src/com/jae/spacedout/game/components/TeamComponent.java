package com.jae.spacedout.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TeamComponent implements Component, Poolable
{
    public TeamComponent.team team;

    public TeamComponent()
    {
        this.team = this.team.neutral;
    }

    @Override
    public void reset()
    {

    }

    public enum team
    {
        team1,
        team2,
        team3,
        team4,
        neutral,
        destructable
    }
}
