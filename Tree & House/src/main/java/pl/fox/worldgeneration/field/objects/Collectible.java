package pl.fox.worldgeneration.field.objects;

import pl.fox.worldgeneration.Handler;
import pl.fox.worldgeneration.field.entity.Entity;

import java.awt.*;

public abstract class Collectible extends Entity {

    protected double life = -1;
    protected int quantity;

    public Collectible(Handler handler, float x, float y, int quantity) {
        super(handler, x, y, 10, 25);
        this.quantity = quantity;
        this.usableBoundsOffset = 5;
    }

    public Collectible(Handler handler, float x, float y, int quantity, double life) {
        super(handler, x, y, 10, 25);
        this.quantity = quantity;
        this.usableBoundsOffset = 5;
        this.life = life;
    }

    protected void drawNotification(Graphics2D g) {
        var offsetX = (int) (x - handler.getOffsetX() - width / 2);
        var offsetY = (int) (y - handler.getOffsetY() - height / 2);

        var g2d = (Graphics2D) g.create();
        g2d.setColor(Color.RED);
        g2d.rotate(-handler.getWorldRotation(), offsetX + 5, offsetY - 5);
        g2d.drawString("!", offsetX, offsetY);
        g2d.dispose();
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public double getLife() {
        return life;
    }

    public void setLife(double life) {
        this.life = life;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " x " + quantity;
    }

}
