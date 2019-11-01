package pl.fox.spaceinvaders.field;

import pl.fox.spaceinvaders.Launcher;
import pl.fox.spaceinvaders.graphics.Addons;
import pl.fox.spaceinvaders.graphics.Points;

import java.awt.*;
import java.util.Random;

public class AlienShip {


    private float x;
    private final float y = 10f;
    private int direction;
    private boolean isDrawn;

    private int startTimer = 0;

    private final int boundsX = 40;
    private final int boundsY = 20;

    private Addons addons = new Addons();

    private void drawShip() {
        if (startTimer < 600)
            startTimer++;

        if (startTimer < 600)
            return;

        if (isDrawn)
            return;

        Random rand = new Random();
        int a = rand.nextInt(700);
        if (a == 1) {
            direction = rand.nextInt(2);
            switch (direction) {
                case 0:
                    x = -20;
                    break;
                case 1:
                    x = Launcher.width + 20;
                    break;
            }
            isDrawn = true;
        }
    }

    private void moveShip() {
        if (direction == 0)
            x += 2.5f;
        else
            x -= 2.5f;

        if ((direction == 0 && x >= Launcher.width + 20) || (direction == 1 && x < -20))
            isDrawn = false;
    }

    private void checkHit() {
        for (int j = 0; j < Player.shotX.size(); j++) {
            if (Player.shotX.get(j) >= x && Player.shotX.get(j) < x + boundsX
                    && Player.shotY.get(j) >= y && Player.shotY.get(j) <= y + boundsY) {
                Player.score += (400 * Player.level);

                addons.draw();
                addons.x.add((int) x + 20);
                addons.y.add((int) y);

                Points.add((int) x + 20, (int) y, 400 * Player.level);
                isDrawn = false;
                Player.shotX.remove(j);
                Player.shotY.remove(j);
                return;
            }
        }
    }

    public void update() {
        drawShip();
        moveShip();
        checkHit();

        addons.update();
    }

    public void render(Graphics g) {
        g.setColor(Color.yellow);
        if (isDrawn)
            g.fillRoundRect((int) x, (int) y, 40, 20, 5, 5);

        addons.render(g);
    }

    public void clearStartTimer() {
        this.startTimer = 0;
    }

    public void uncheckDrawn(){
        this.isDrawn = false;
    }

}
