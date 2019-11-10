package pl.fox.platformarsenal.field.dynamics;

import java.awt.*;

public class Wheel extends Dynamic {

    private Color color;

    public Wheel(float x, float y, int width, int height, Color color) {
        super(x, y, width, height);
        this.color = color;

        bounds.x = 0;
        bounds.y = 0;
        bounds.width = width;
        bounds.height = height;

        xMove = 0;
        yMove = 0;
    }


    @Override
    public boolean isDynamic(){
        return true;
    }

    @Override
    public void update() {
        fall();
        checkFall();
        checkHit();
        checkPlayerHit();
        checkEnemyHit();
        pushBack();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval((int) x,(int) y, bounds.width, bounds.height);
        g.setColor(Color.WHITE);
        g.drawOval((int) x,(int) y, bounds.width, bounds.height);
    }
}
