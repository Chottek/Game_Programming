package pl.fox.worldgeneration.field.entity;

import pl.fox.worldgeneration.Handler;

public class MovingEntity extends Entity {

    protected float speed = 8.0F;

    public MovingEntity(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
    }

    public float getSpeed() {
        return speed;
    }
}
