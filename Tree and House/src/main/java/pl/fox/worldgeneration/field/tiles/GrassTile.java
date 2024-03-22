package pl.fox.worldgeneration.field.tiles;

import pl.fox.worldgeneration.Handler;

import java.awt.*;

public class GrassTile extends Tile {

    public GrassTile(Handler handler, float x, float y, int width, int height) {
        super(handler, 3,  x, y, width, height, new Color(0, 133, 65));
    }

}
