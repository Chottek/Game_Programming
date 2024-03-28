package pl.fox.worldgeneration.field.objects;

import pl.fox.worldgeneration.Handler;

import java.awt.*;

public class Wood extends Collectible {

    private final int width = 10;
    private final int height = 25;


    public Wood(Handler handler, float x, float y, int quantity, double life) {
        super(handler, x, y, quantity, life);
        this.usableBoundsOffset = 5;
    }

    public void render(Graphics2D g) {
        if (!handler.getPlayer().getSightBounds().intersects(getBounds())) {
            return;
        }

        var offsetX = (int) (x - handler.getOffsetX() - width / 2);
        var offsetY = (int) (y - handler.getOffsetY() - height / 2);

        drawNotification(g);

        g.setColor(new Color(100, 60, 10));
        g.fillRect(offsetX, offsetY, width, height);

        drawUsableBounds(g);
    }

}
