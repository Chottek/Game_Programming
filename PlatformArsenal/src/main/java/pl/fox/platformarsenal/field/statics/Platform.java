package pl.fox.platformarsenal.field.statics;

import java.awt.*;

public class Platform extends StaticEntity {

    private Color color;

    public Platform(float x, float y, int width, int height, Color color){
        super(x, y, width, height);
        this.color = color;

        bounds.x = 0;
        bounds.y = 0;
        bounds.width = width;
        bounds.height = height;
    }

    @Override
    public void update(){

    }

    @Override
    public void render(Graphics g){
        g.setColor(color);
        g.fillRoundRect((int) x, (int) y, bounds.width, bounds.height, 10, 10);
        g.setColor(Color.WHITE);
        g.drawRoundRect((int) x, (int) y, bounds.width, bounds.height, 10, 10);
    }

}
