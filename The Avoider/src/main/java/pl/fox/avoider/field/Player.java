package pl.fox.avoider.field;

import pl.fox.avoider.Game;
import pl.fox.avoider.Launcher;
import pl.fox.avoider.input.KeyManager;

import java.awt.*;

public class Player {

    public static float x;
    public static float y;
    private final float SPEED = 5.0f;
    private final int SIZE = 20;
    public static int life = 3;
    public static int score;

    private int moveTimer = 0;

    public static boolean isDead;


    public Player(float x, float y) {
        this.x = x;
        this.y = y;

        init();
    }


    public void init() {
        moveTimer = 4;
        score = 0;
        life = 3;
        isDead = false;
        x = Launcher.width / 2;
        y = Launcher.height / 2;
    }


    private void move() {
        if (KeyManager.up || KeyManager.arrUp) {
            y -= SPEED;
            moveTimer = 5;
        }
        if (KeyManager.down || KeyManager.arrDown) {
            y += SPEED;
            moveTimer = 5;
        }
        if (KeyManager.left || KeyManager.arrLeft) {
            x -= SPEED;
            moveTimer = 5;
        }
        if (KeyManager.right || KeyManager.arrRight) {
            x += SPEED;
            moveTimer = 5;
        }

        if (Game.ticks % 60 == 0)
            moveTimer--;

    }

    public void update() {
        checkScore();
        move();
        checkFieldBounds();
        checkLife();
    }

    public void render(Graphics g) {
        g.setFont(new Font("Arial", Font.ITALIC, 14));
        g.setColor(Color.GREEN);

        if (!((x >= 0 && x <= 100) && (y >= 0 && y <= 20)))
            g.drawString("SCORE: " + score, 10, 15);

        g.drawString("Life:" + life, 10, Launcher.height - 10);

        g.setColor(Color.RED);

        g.setFont(new Font("Arial", Font.ITALIC, 20));
        if (moveTimer < 4)
            g.drawString("MOVE! " + moveTimer, 400, 20);

        g.fillRect((int) x, (int) y, SIZE, SIZE);
    }

    private void checkFieldBounds() {
        if (x <= 0)
            x = 0;
        if (x + SIZE >= Launcher.width)
            x = Launcher.width - SIZE;
        if (y <= 0)
            y = 0;
        if (y + SIZE >= Launcher.height)
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
