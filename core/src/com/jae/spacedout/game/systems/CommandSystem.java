package com.jae.spacedout.game.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ReflectionPool;
import com.jae.spacedout.game.commands.Command;
import com.jae.spacedout.game.components.CommandComponent;
import com.jae.spacedout.game.components.Mappers;

import java.util.Iterator;

public class CommandSystem extends IteratingSystem
{
    //command component in order to not reallocate
    private CommandComponent commandComponent;
    private Iterator<Command> iterator;
    private Command command;

    //pool for commands for memory reasons
    private CommandPool commandPool;
    public CommandPool getCommandPool()
    {
        return this.commandPool;
    }

    public CommandSystem(int priority)
    {
        super(Family.all(CommandComponent.class).get(), priority);

        this.commandPool = new CommandPool(50, 100);
    }

    @Override
    protected void processEntity(Entity entity, float dt)
    {
        this.commandComponent = Mappers.command.get(entity);
        this.iterator = this.commandComponent.commands.iterator();
        while(this.iterator.hasNext())
        {
            this.command = iterator.next();
            this.command.execute(entity);
            this.commandPool.free(this.command);
            iterator.remove();
        }
    }

    public <T extends Command> T createCommand (Class<T> commandType)
    {
        return this.commandPool.obtain(commandType);
    }

    public void clearPool()
    {
        this.commandPool.clear();
    }

    //pools for different command classes
    public class CommandPool
    {
        private ObjectMap<Class<?>, ReflectionPool> pools;
        private int initialSize;
        private int maxSize;

        public CommandPool (int initialSize, int maxSize)
        {
            this.pools = new ObjectMap<Class<?>, ReflectionPool>();
            this.initialSize = initialSize;
            this.maxSize = maxSize;
        }

        public <T> T obtain (Class<T> type)
        {
            //set pool to pool for that specific class
            ReflectionPool pool = pools.get(type);

            //if there is no existing pool, create a new one and add that class
            if (pool == null)
            {
                pool = new ReflectionPool(type, initialSize, maxSize);
                pools.put(type, pool);
            }

            return (T)pool.obtain();
        }

        public void free (Object object)
        {
            //make sure we're freeing an actual object
            if (object == null)
            {
                throw new IllegalArgumentException("object cannot be null.");
            }

            ReflectionPool pool = pools.get(object.getClass());

            if (pool == null)
            {
                return; // Ignore freeing an object that was never retained.
            }

            pool.free(object);
        }

        public void freeAll (Array objects)
        {
            if (objects == null)
            {
                throw new IllegalArgumentException("objects cannot be null.");
            }

            for (int i = 0, n = objects.size; i < n; i++)
            {
                Object object = objects.get(i);

                if (object == null)
                {
                    continue;
                }

                this.free(object);
            }
        }

        public void clear ()
        {
            for (Pool pool : this.pools.values())
            {
                pool.clear();
            }
        }
    }
}
