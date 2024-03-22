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
        g.setColor(color);
        g.fillRect((int) (x - handler.getOffsetX() - width / 2), (int) (y - handler.getOffsetY() - height / 2), width, height);
    }

}
