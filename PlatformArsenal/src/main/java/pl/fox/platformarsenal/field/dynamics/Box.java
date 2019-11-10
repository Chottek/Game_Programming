package pl.fox.platformarsenal.field.dynamics;

import java.awt.*;

public class Box extends Dynamic {

    private Color color;

    public Box(float x, float y, int width, int height, Color color) {
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
        pushBack();
        checkPlayerHit();
        checkEnemyHit();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x,(int) y, bounds.width, bounds.height);

        g.setColor(new Color(255, 255, 255));
        g.drawLine((int) x,(int) y, (int) x + bounds.width, (int) y + bounds.height);
        g.drawLine((int) x, (int) y + bounds.height, (int) x + bounds.width,(int) y);

        g.setColor(Color.WHITE);
        g.drawRect((int) x,(int) y, bounds.width, bounds.height);
    }
}
