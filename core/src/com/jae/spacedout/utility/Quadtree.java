package com.jae.spacedout.utility;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jae.spacedout.game.components.BoundingComponent;
import com.jae.spacedout.game.components.Mappers;

import java.util.ArrayList;

public class Quadtree
{
    private static final int MAX_OBJECTS_PER_NODE = 20;
    private static final int MAX_NODE_LEVELS = 10;

    private int level;
    private ArrayList<Entity> entities;
    private Rectangle bounds;
    //quadrants are defined counter-clockwise, with 0 being top right
    private Quadtree[] nodes;
    private Quadtree parentNode;

    public Quadtree(int level, Rectangle bounds, Quadtree parentNode)
    {
        this.level = level;
        this.bounds = bounds;

        this.entities = new ArrayList<Entity>();
        this.nodes = new Quadtree[4];
        this.parentNode = parentNode;
    }

    //clears all entities from quadtree
    public void clear()
    {
        this.entities.clear();

        for(int i = 0; i < this.nodes.length; i++)
        {
            if(nodes[i] != null)
            {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    //splits node into four subnodes
    private void split()
    {
        int subWidth = (int)(this.bounds.getWidth() / 2);
        int subHeight = (int)(this.bounds.getHeight() / 2);
        int x = (int)this.bounds.getX();
        int y = (int)this.bounds.getY();

        this.nodes[0] = new Quadtree(this.level + 1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight), this);
        this.nodes[1] = new Quadtree(this.level + 1, new Rectangle(x, y + subHeight, subWidth, subHeight), this);
        this.nodes[2] = new Quadtree(this.level + 1, new Rectangle(x, y, subWidth, subHeight), this);
        this.nodes[3] = new Quadtree(this.level + 1, new Rectangle(x + subWidth, y, subWidth, subHeight), this);
    }

    //gets the index in which an object should be contained
    private int getIndex(Entity entity)
    {
        //get rectangle from entity
        Rectangle rectangle = Mappers.bounding.get(entity).boundingBox;

        //default index, -1 means that object must be part of parent node
        int index = -1;

        //the length of the diagonal of the rectangle (in case of rotation)
        float diagonalLength = Utils.findLength(rectangle.getWidth() / 2, rectangle.getHeight() / 2);

        //used to determine if objects fits in left/right and top/bottom nodes
        float horizontalMidpoint = this.bounds.getX() + (this.bounds.getWidth() / 2);
        float verticalMidpoint = this.bounds.getY() + (this.bounds.getHeight() / 2);

        //tests whether rectangle fits in top or bottom nodes
        boolean fitsInTop = (rectangle.getY() - diagonalLength > verticalMidpoint);
        boolean fitsInBottom = (rectangle.getY() + diagonalLength < verticalMidpoint);

        //tests left quadrants
        if(rectangle.getX() + diagonalLength < horizontalMidpoint)
        {
            if(fitsInTop)
            {
                index = 1;
            }
            else if(fitsInBottom)
            {
                index = 2;
            }
        }
        //tests right quadrants
        else if(rectangle.getX() - diagonalLength > horizontalMidpoint)
        {
            if(fitsInTop)
            {
                index = 0;
            }
            else if(fitsInBottom)
            {
                index = 3;
            }
        }

        return index;
    }

    //determines whether this entire node contains the input entity
    private boolean containsEntity(Entity entity)
    {
        Rectangle rectangle = Mappers.bounding.get(entity).boundingBox;
        float diagonal = Utils.findLength(rectangle.getWidth() / 2, rectangle.getHeight() / 2);

        return (rectangle.getX() - diagonal > this.bounds.getX()) &&
                (rectangle.getX() + diagonal < this.bounds.getX() + this.bounds.getWidth()) &&
                (rectangle.getY() - diagonal > this.bounds.getY()) &&
                (rectangle.getY() + diagonal < this.bounds.getY() + this.bounds.getHeight());
    }

    //inserts the object into a suitable node
    public void insert(Entity entity)
    {
        BoundingComponent bounding = Mappers.bounding.get(entity);

        //if this node has subnodes, insert into a subnode
        if(nodes[0] != null)
        {
            int index = this.getIndex(entity);

            //if it fits into a subnode, add it to that subnode
            if(index != -1)
            {
                this.nodes[index].insert(entity);
            }
        }

        //if the object does not fit into a subnode, add it to this node
        this.entities.add(entity);
        bounding.currentLeaf = this;

        //if there are more entities than the entity threshold and there are ample levels for nodes, create and populate new nodes
        if(this.entities.size() > Quadtree.MAX_OBJECTS_PER_NODE && this.level < Quadtree.MAX_NODE_LEVELS)
        {
            if(this.nodes[0] == null)
            {
                this.split();
            }

            for(int i = 0; i < this.entities.size(); i++)
            {
                int index = this.getIndex(this.entities.get(i));

                //if the object does not belong in this node, add it to the new one
                if(index != -1)
                {
                    this.nodes[index].insert(this.entities.remove(i));
                    i--;
                }
            }
        }
    }

    //cleans out all empty parent nodes
    private void clean()
    {
        if(this.nodes[0] != null)
        {
            if(this.nodes[0].isEmpty() &&
                    this.nodes[1].isEmpty() &&
                    this.nodes[2].isEmpty() &&
                    this.nodes[3].isEmpty())
            {
                this.nodes[0] = null;
                this.nodes[1] = null;
                this.nodes[2] = null;
                this.nodes[3] = null;

                //now try to clean the parent
                if(this.parentNode != null && this.isEmpty())
                {
                    this.parentNode.clean();
                }
            }
        }
        //if this is a leaf, try to clean the parent
        else if(this.parentNode != null && this.isEmpty())
        {
            this.parentNode.clean();
        }
    }

    //determines whether node is empty
    private boolean isEmpty()
    {
        return this.entities.isEmpty();
    }

    //moves the object to its correct node (from this parent node out)
    private void moveEntityInternal(Entity entity, Quadtree oldParent)
    {
        //checks if the entity is still contained within this node
        if(this.containsEntity(entity))
        {
            //then checks if the entity is contained within any child nodes
            if(this.nodes[0] != null)
            {
                int index = this.getIndex(entity);

                //if the new node is not this one, move the object and clean
                if(index != -1)
                {
                    this.nodes[index].insert(entity);
                    oldParent.entities.remove(entity);
                    oldParent.clean();
                }
            }
        }
        //if it does not fit in here, try to fit it upwards
        else
        {
            try
            {
                this.parentNode.moveEntity(entity);
            }
            catch (NullPointerException e)
            {
                System.out.println("Tried to relocate to null parent node");
            }
        }
    }

    //moves the object to its correct node (from its old node out)
    public void moveEntity(Entity entity)
    {
        BoundingComponent bounding = Mappers.bounding.get(entity);

        if(bounding.currentLeaf != null)
        {
            bounding.currentLeaf.moveEntityInternal(entity, bounding.currentLeaf);
        }
        else
        {
            this.moveEntityInternal(entity, this);
        }
    }

    //returns all objects that the given entity could potentially collide with
    public ArrayList<Entity> getNeighbors(ArrayList<Entity> entities, Entity entity)
    {
        int index = this.getIndex(entity);
        if(index != -1 && nodes[0] != null)
        {
            nodes[index].getNeighbors(entities, entity);
        }

        entities.addAll(this.entities);
        return entities;
    }
}
