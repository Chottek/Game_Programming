package pl.fox.worldgeneration.field.entity;

import pl.fox.worldgeneration.Handler;

import java.awt.*;

public class House extends Building {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    public House(Handler handler, float x, float y) {
        super(handler, x, y, WIDTH, HEIGHT);
    }

    public House(Handler handler, float x, float y, Color roofColor) {
        super(handler, x, y, WIDTH, HEIGHT);
        this.roofColor = roofColor;
    }

}
