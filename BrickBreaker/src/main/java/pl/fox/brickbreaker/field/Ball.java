package pl.fox.brickbreaker.field;


import pl.fox.brickbreaker.Game;
import pl.fox.brickbreaker.Launcher;
import pl.fox.brickbreaker.input.KeyManager;
import pl.fox.brickbreaker.utils.Convert;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Ball {

    static float x, y;
    public static double angle;
    private float speed = 8.0f;
    private boolean isPushed;
    public static final int SIZE = 10;

    public Ball() {
        isPushed = false;
        x = Launcher.width / 2;
        y = Launcher.height / 2;

        angle = 0.5;
    }

    private void checkPlayerTouch() {
        if (x >= Player.x - SIZE && x <= Player.x + 60 &&
                y >= Player.y - SIZE && y <= Player.y + 20) {
            y -= 3;
            if (KeyManager.arrLeft || KeyManager.left) {
                angle = Convert.angleHor(angle) + 0.5;
            } else if (KeyManager.arrRight || KeyManager.right) {
                angle = Convert.angleHor(angle) - 0.5;
            } else
                angle = Convert.angleHor(angle);
        }

    }

    private void checkPush() {
        if (KeyManager.space)
            isPushed = true;

        if(KeyManager.keyJustPressed(KeyEvent.VK_R)){
            isPushed = false;
            y = Launcher.width / 2;
        }
    }

    private void checkFieldBounds() {
        if (x >= Launcher.width - 2 * SIZE)
            angle = Convert.angleVert(angle);
        else if (x <= SIZE)
            angle = Convert.angleVert(angle);
        else if (y <= SIZE)
            angle = Convert.angleHor(angle);

        if(y >= Launcher.height){
            y = Launcher.height / 2;
            isPushed = false;
        }

    }

    private void move() {
        if (!isPushed)
            return;

        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);
    }

    public void update() {
        checkPush();
        move();
        checkFieldBounds();
        checkPlayerTouch();
    }

    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval((int) x - SIZE, (int) y - SIZE, 2 * SIZE, 2 * SIZE);


        if(!isPushed && Game.ticks / 12 % 2 == 0){
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press SPACE to push", 200, Launcher.height / 2 + 100);
        }
    }
}
