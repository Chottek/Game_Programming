package pl.fox.worldgeneration.field.tiles;

import pl.fox.worldgeneration.Handler;

import java.awt.*;

public abstract class Tile {

    private final Handler handler;

    protected final int id;
    protected final float x;
    protected final float y;
    protected final int width;
    protected final int height;

    protected final Color color;

    protected Tile(Handler handler, int id, float x, float y, int width, int height, Color color) {
        this.handler = handler;
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect((int) (x - handler.getOffsetX() - width / 2), (int) (y - handler.getOffsetY() - height / 2), width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)(x - handler.getOffsetX() - width / 2), (int) (y - handler.getOffsetY() - height / 2), width, height);
    }

    public boolean isCollidable() {
        return false;
    }

    public int getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
