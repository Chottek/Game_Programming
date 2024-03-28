package pl.fox.worldgeneration.field.tiles;

import pl.fox.worldgeneration.Handler;

import java.awt.*;

public class WallTile extends Tile {

    public WallTile(Handler handler, float x, float y, int width, int height) {
        super(handler, 2, x, y, width, height, Color.WHITE);
    }


    @Override
    public boolean isCollidable() {
        return true;
    }
}
