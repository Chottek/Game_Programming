package pl.fox.worldgeneration.field.entity;

import pl.fox.worldgeneration.Handler;

public abstract class Entity {

    protected Handler handler;

    protected float x;
    protected float y;

    protected int width;
    protected int height;

    public Entity(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

}
