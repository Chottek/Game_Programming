package pl.fox.worldgeneration.field.objects;

import pl.fox.worldgeneration.Handler;

import java.awt.*;

public class Rock extends Collectible {

    public Rock(Handler handler, float x, float y, int quantity, double life) {
        super(handler, x, y, quantity, life);
        this.width = 10;
        this.height = 10;
    }

    @Override
    public void render(Graphics2D g) {
        if (!handler.getPlayer().getSightBounds().intersects(getBounds())) {
            return;
        }

        var offsetX = (int) (x - handler.getOffsetX() - width / 2);
        var offsetY = (int) (y - handler.getOffsetY() - height / 2);

        var g2d = (Graphics2D) g.create();
        g2d.setColor(Color.RED);
        g2d.rotate(-handler.getWorldRotation(), offsetX + 5, offsetY - 5);
        g2d.drawString("!", offsetX, offsetY);
        g2d.dispose();

        //g.rotate(angle, offsetX, offsetY);
        g.setColor(Color.DARK_GRAY);
        g.fillRoundRect(offsetX, offsetY, width, height, 10, 10);
        g.setColor(Color.BLACK);
        g.drawRoundRect(offsetX, offsetY, width, height, 10, 10);

        drawUsableBounds(g);
    }

    public Rectangle getBounds() {
        var offsetX = (int) (x - handler.getOffsetX() - width / 2);
        var offsetY = (int) (y - handler.getOffsetY() - height / 2);

        return new Rectangle(offsetX, offsetY, width, height);
    }

    public Rectangle getUsableBounds() {
        var offsetX = (int) (x - handler.getOffsetX() - width / 2 - 10);
        var offsetY = (int) (y - handler.getOffsetY() - height / 2 - 10);

        return new Rectangle(offsetX + 5,  offsetY + 5, width + 10, height + 10);
    }

}
