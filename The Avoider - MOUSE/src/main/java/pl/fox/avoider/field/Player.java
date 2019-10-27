package pl.fox.avoider.field;

import pl.fox.avoider.Game;
import pl.fox.avoider.Launcher;
import pl.fox.avoider.display.Display;
import pl.fox.avoider.input.MouseManager;

import java.awt.*;

public class Player {

    public static int x, y;
    private int lastX, lastY;
    private final int SIZE = 20;
    public static int life = 3;
    public static int score;

    private int moveTimer = 0;

    public static boolean isDead;


    public Player() {
        init();
    }

    public void init() {
        try {
            Robot cyborg = new Robot();
            cyborg.mouseMove(Display.getScreenWidth() / 2, Display.getScreenHeight() / 2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        moveTimer = 4;
        score = 0;
        life = 3;
        isDead = false;
    }

    private void setMouseCoords() {
        lastX = x;
        lastY = y;

        x = MouseManager.mouseX;
        y = MouseManager.mouseY;
    }

    private void checkMoveTimer() {
        if (x == lastX && y == lastY) {
            if (Game.ticks % 60 == 0)
                moveTimer--;
        } else
            moveTimer = 4;
    }


    public void update() {
        setMouseCoords();
        checkMoveTimer();
        checkScore();
        checkFieldBounds();
        checkLife();
    }

    public void render(Graphics g) {
        g.setFont(new Font("Arial", Font.ITALIC, 14));
        g.setColor(Color.GREEN);

        if (!((MouseManager.mouseX >= 0 && MouseManager.mouseX <= 100) && (MouseManager.mouseY >= 0 && MouseManager.mouseY <= 20)))
            g.drawString("SCORE: " + score, 10, 15);

        g.drawString("Life:" + life, 10, Launcher.height - 10);

        g.setColor(Color.RED);

        g.setFont(new Font("Arial", Font.ITALIC, 20));
        if (moveTimer < 4)
            g.drawString("MOVE! " + moveTimer, 400, 20);

        g.fillRect(x, y, SIZE, SIZE);
    }

    private void checkFieldBounds() {
        if (x <= 0)
            x = 0;
        if (x >= Launcher.width - SIZE)
            x = Launcher.width - SIZE;
        if (y <= 0)
            y = 0;
        if (y  >= Launcher.height - SIZE)
            y = Launcher.height - SIZE;
    }

    private void checkScore() {
        if (Game.ticks % 4 == 0)
            score++;
    }

    private void checkLife() {
        if (life <= 0 || moveTimer <= 0)
            isDead = true;
    }
}
