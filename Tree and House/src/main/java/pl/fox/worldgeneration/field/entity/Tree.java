package pl.fox.worldgeneration.field.entity;

import pl.fox.worldgeneration.Handler;

import java.awt.*;
import java.util.Random;

public class Tree {

    private final Handler handler;

    private final float x;
    private final float y;

    private final int width = 40;
    private final int height = 40;

    private final double angle;

    private int crownAlpha = 255;

    public Tree(Handler handler, float x, float y) {
        this.handler = handler;
        this.x = x;
        this.y = y;

        angle = new Random().nextDouble();
    }

    public void update() {
        if (handler.getPlayer().getBounds().intersects(getUsableBounds()) && crownAlpha > 0) {
           crownAlpha -= 10;
        }

        if (!handler.getPlayer().getBounds().intersects(getUsableBounds()) && crownAlpha < 255) {
            crownAlpha += 6;
        }

        if (crownAlpha < 0) crownAlpha = 0;
        if (crownAlpha > 255) crownAlpha = 255;
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(80, 20, 20));
        g.fillOval(getBounds().x, getBounds().y, getBounds().width, getBounds().height);

        var g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(10, 80, 80, crownAlpha));
        g2d.rotate(angle, (int) (x  - handler.getOffsetX()), (int) (y - handler.getOffsetY()));
        g2d.fillRect((int) (x - handler.getOffsetX() - width / 2), (int) (y - handler.getOffsetY() - height / 2), width, height);
        g2d.dispose();

        g.setColor(Color.YELLOW);
        g.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);

        g.setColor(Color.BLUE);
        g.drawRect(getUsableBounds().x, getUsableBounds().y, getUsableBounds().width, getUsableBounds().height);
    }



//    private int calculateOpacity() {
//        var entityX = (x - handler.getOffsetX() - width / 2);
//        var entityY = (y - handler.getOffsetY() - height / 2);
//
//        var playerX = (handler.getPlayer().getX() - 2 * handler.getPlayer().getBounds().width);
//        var playerY = (handler.getPlayer().getY() - 2 * handler.getPlayer().getBounds().height);
//
//        var value = Math.sqrt(Math.pow(entityX - playerX, 2) + Math.pow(entityY - playerY, 2));
//
//        if (value > 255) {
//            return 255;
//        }
//
//        return (int) value;
//    }

    public Rectangle getBounds() {
        return new Rectangle((int)(x - handler.getOffsetX() - (width - 20) / 2), (int) (y - handler.getOffsetY() - (height - 20) / 2), width - 20, height - 20);
    }

    public Rectangle getUsableBounds() {
        var offsetX = (int) (x - handler.getOffsetX() - width / 2 - 50);
        var offsetY = (int) (y - handler.getOffsetY() - height / 2 - 50);

        return new Rectangle(offsetX + 25,  offsetY + 25, width + 50, height + 50);
    }

    public boolean isCollidable() {
        return true;
    }

}
