package pl.fox.worldgeneration.field.entity;

import pl.fox.worldgeneration.Handler;
import pl.fox.worldgeneration.field.Player;

import java.awt.*;

public abstract class Entity {

    protected Handler handler;

    protected float x;
    protected float y;

    protected int width;
    protected int height;

    protected double angle = 0;

    protected int usableBoundsOffset = 5;

    public Entity(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update() {}

    public void render(Graphics2D g) {}

    protected void drawUsableBounds(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.drawRect(getUsableBounds().x, getUsableBounds().y, getUsableBounds().width, getUsableBounds().height);
    }

    public Rectangle getBounds() {
        var offsetX = (int) (x - handler.getOffsetX() - width / 2);
        var offsetY = (int) (y - handler.getOffsetY() - height / 2);

        return new Rectangle(offsetX, offsetY, width, height);
    }

    public Rectangle getUsableBounds() {
        var offsetX = (int) (x - handler.getOffsetX() - width / 2);
        var offsetY = (int) (y - handler.getOffsetY() - height / 2);

        return new Rectangle(offsetX - usableBoundsOffset,
                offsetY - usableBoundsOffset,
                width + 2 * usableBoundsOffset,
                height + 2 * usableBoundsOffset
        );
    }

    public void handleCollisionWithPlayer(Player p) {
        if (p.getBounds().intersects(this.getBounds())) {
            p.handleCollision();
        }
    }

    public boolean isCollidable() {
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("[");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append("]");

        return sb.toString();
    }

    public float getX() {
        return x;
    }

    public float getY() { return y; }
}
