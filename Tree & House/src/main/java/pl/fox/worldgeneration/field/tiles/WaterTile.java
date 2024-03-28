package pl.fox.worldgeneration.field.tiles;

import pl.fox.worldgeneration.Handler;

import java.awt.*;

public class WaterTile extends Tile {

    public WaterTile(Handler handler, float x, float y, int width, int height) {
        super(handler, 3,  x, y, width, height, Color.CYAN);
    }

}
