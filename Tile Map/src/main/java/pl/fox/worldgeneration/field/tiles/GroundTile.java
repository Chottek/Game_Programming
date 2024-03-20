package pl.fox.worldgeneration.field.tiles;

import pl.fox.worldgeneration.Handler;

import java.awt.*;

public class GroundTile extends Tile {

    private final Handler handler;

    public GroundTile(Handler handler, float x, float y, int width, int height) {
        super(handler, 2, x, y, width, height, new Color(150, 75, 0));
        this.handler = handler;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), calculateOpacity()));
        g.fillRect((int) (x - handler.getOffsetX() - width / 2), (int) (y - handler.getOffsetY() - height / 2), width, height);
    }

    //Calculating opacity to see the outcome and proper positioning
    private int calculateOpacity() {
        var tileX = (x - handler.getOffsetX() - width / 2);
        var tileY = (y - handler.getOffsetY() - height / 2);

        var playerX = (handler.getPlayer().getX() - 2 * handler.getPlayer().getBounds().width);
        var playerY = (handler.getPlayer().getY() - 2 * handler.getPlayer().getBounds().height);

        var value = Math.sqrt(Math.pow(tileX - playerX, 2) + Math.pow(tileY - playerY, 2));
        if (value > 255) {
            return 255;
        }

        if (value < 100) {
            value = 0;
        }

        return (int) value;
    }
}
