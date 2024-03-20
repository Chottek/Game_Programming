package pl.fox.worldgeneration.field.tiles;

import pl.fox.worldgeneration.Handler;

import java.awt.*;

public class RoadTile extends Tile {

    public RoadTile(Handler handler, float x, float y, int width, int height) {
        super(handler, 1,  x, y, width, height, Color.DARK_GRAY);
    }

}
