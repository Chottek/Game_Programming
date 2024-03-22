package pl.fox.worldgeneration.field.entity;

import pl.fox.worldgeneration.Handler;

import java.awt.*;
import java.util.Random;

public class House {

    private final Handler handler;

    private final float x;
    private final float y;

    private final int width = 800;
    private final int height = 600;
    private final int wallWidth = 10;

    private final double angle;

    private int roofAlpha = 255;

    public House(Handler handler, float x, float y) {
        this.handler = handler;
        this.x = x;
        this.y = y;

        angle = new Random().nextDouble();
    }

    public void update() {
        if (handler.getPlayer().getBounds().intersects(getUsableBounds()) && roofAlpha > 0) {
            roofAlpha -= 10;
        }

        if (!handler.getPlayer().getBounds().intersects(getUsableBounds()) && roofAlpha < 255) {
            roofAlpha += 6;
        }

        if (roofAlpha < 0) roofAlpha = 0;
        if (roofAlpha > 255) roofAlpha = 255;
    }

    public void render(Graphics2D g) {
        var offsetX = (int) (x - handler.getOffsetX() - width / 2);
        var offsetY = (int) (y - handler.getOffsetY() - height / 2);

        g.setColor(new Color(200,200, 130));
        g.fillRect(offsetX, offsetY, width, height);

        g.setColor(Color.RED);
        g.fillRect(offsetX, offsetY, wallWidth, height);
        g.fillRect(offsetX + (width - wallWidth), offsetY, wallWidth, height);

        g.fillRect(offsetX, offsetY, width / 2 - 5 * wallWidth, wallWidth);
        g.fillRect(offsetX + (width / 2 + 5 * wallWidth), offsetY, width / 2 - 5 * wallWidth, wallWidth);

        g.fillRect(offsetX, offsetY + (height - wallWidth), width / 2 - 5 * wallWidth, wallWidth);
        g.fillRect(offsetX + (width / 2 + 5 * wallWidth), offsetY + (height - wallWidth), width / 2 - 5 * wallWidth, wallWidth);

        g.setColor(new Color(100, 10, 10, roofAlpha));
        g.fillRect(offsetX - 30, offsetY - 30, width + 60, height + 60);

        g.setColor(Color.BLUE);
        g.drawRect(getUsableBounds().x, getUsableBounds().y, getUsableBounds().width, getUsableBounds().height);
    }

    public java.util.List<Rectangle> getWallBounds() {
        var offsetX = (int) (x - handler.getOffsetX() - width / 2);
        var offsetY = (int) (y - handler.getOffsetY() - height / 2);

        return java.util.List.of(
                new Rectangle(offsetX, offsetY, wallWidth, height),
                new Rectangle(offsetX + (width - wallWidth), offsetY, wallWidth, height),
                new Rectangle(offsetX, offsetY, width / 2 - 5 * wallWidth, wallWidth),
                new Rectangle(offsetX + (width / 2 + 5 * wallWidth), offsetY, width / 2 - 5 * wallWidth, wallWidth),
                new Rectangle(offsetX, offsetY + (height - wallWidth), width / 2 - 5 * wallWidth, wallWidth),
                new Rectangle(offsetX + (width / 2 + 5 * wallWidth), offsetY + (height - wallWidth), width / 2 - 5 * wallWidth, wallWidth)
        );
    }

    public Rectangle getBounds() {
        var offsetX = (int) (x - handler.getOffsetX() - width / 2);
        var offsetY = (int) (y - handler.getOffsetY() - height / 2);

        return new Rectangle(offsetX, offsetY, width, height);
    }

    public Rectangle getUsableBounds() {
        var offsetX = (int) (x - handler.getOffsetX() - width / 2 - 200);
        var offsetY = (int) (y - handler.getOffsetY() - height / 2 - 200);

        return new Rectangle(offsetX + 100,  offsetY + 100, width + 200, height + 200);
    }

    public boolean isCollidable() {
        return true;
    }

}
