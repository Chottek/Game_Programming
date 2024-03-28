package pl.fox.worldgeneration.field.entity;

import pl.fox.worldgeneration.Handler;

import java.awt.*;
import java.util.Random;

public class Warehouse extends Building {

    public Warehouse(Handler handler, float x, float y) {
        super(handler, x, y, 1200, 700);
        this.roofLeapOver = 0;
        this.roofColor = new Color(100, 100, 100);
        this.floorColor = Color.LIGHT_GRAY;
    }

}
