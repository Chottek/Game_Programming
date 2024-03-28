package pl.fox.worldgeneration.field.entity;

import pl.fox.worldgeneration.Handler;

import java.awt.*;
import java.util.Random;

public class Tree extends Entity {

    private int crownAlpha = 255;

    public Tree(Handler handler, float x, float y) {
        super(handler, x, y, 40, 40);
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.usableBoundsOffset = 50;
        this.angle = new Random().nextDouble();
    }

    public void update() {
        if (handler.getPlayer().getBounds().intersects(getUsableBounds())) {
            if (crownAlpha > 0) crownAlpha -= 10;
        } else {
            if (crownAlpha < 255) crownAlpha += 6;
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

        drawUsableBounds(g);
    }


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
