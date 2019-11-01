package pl.fox.spaceinvaders.field;

import pl.fox.spaceinvaders.Launcher;
import pl.fox.spaceinvaders.graphics.EnemyDeath;
import pl.fox.spaceinvaders.graphics.PlayerDeath;
import pl.fox.spaceinvaders.input.KeyManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player {

    public static float x;
    public static float y;

    private final float SPEED = 4.0f;

    public static final int boundsX = 50;

    public static boolean isDead;
    public static boolean hasWon;

    private boolean isReloading;
    private int reloadTimer, reloadCooldown;

    public static int life = 3;

    public static int level;
    public static int score;

    public static int addOn;
    private int addOnCounter = 0;

    public static ArrayList<Integer> shotX;
    public static ArrayList<Integer> shotY;

    public Player() {
        shotX = new ArrayList<>();
        shotY = new ArrayList<>();
        init();
    }


    public void init() {
        addOnCounter = 0;
        hasWon = false;
        addOn = -1;
        score = 0;
        life = 3;
        level = 1;
        isDead = false;
        isReloading = false;
        x = (Launcher.width - boundsX) / 2;
        y = Launcher.width - 50;

        reloadCooldown = 40;

    }

    private void getInput() {
        if (KeyManager.left || KeyManager.arrLeft)
            x -= SPEED;

        if (KeyManager.right || KeyManager.arrRight)
            x += SPEED;

        if (!isReloading && (KeyManager.keyJustPressed(KeyEvent.VK_SPACE) || KeyManager.keyJustPressed(KeyEvent.VK_UP))) {
            shotX.add((int) x + (boundsX / 2));
            shotY.add((int) y);
            isReloading = true;
            reloadTimer = 0;
        }
    }

    private void checkReloadingTimer() {
        if (isReloading) {
            reloadTimer++;
            if (reloadTimer >= reloadCooldown) {
                reloadTimer = 40;
                isReloading = false;
            }
        }
    }

    private void moveShot() {
        for (int i = 0; i < shotX.size(); i++)
            shotY.set(i, shotY.get(i) - 10);
    }

    private void checkDeath() {
        if (life <= 0) {
            isDead = true;
            EnemyDeath.removeAll();
            PlayerDeath.removeAll();
            shotX.clear();
            shotY.clear();
        }
    }

    private void useAddOn() {
        if (addOn == 0 && life < 4) {
            life++;
            addOn = -1;
        } else if (addOn == 1) {
            if (KeyManager.keyJustPressed(KeyEvent.VK_SPACE) || (KeyManager.keyJustPressed(KeyEvent.VK_UP)))
                addOnCounter++;

            if (addOnCounter == 2) {
                addOn = -1;
                addOnCounter = 0;
            }
        } else if (addOn == 2) {
            if (KeyManager.keyJustPressed(KeyEvent.VK_SPACE) || (KeyManager.keyJustPressed(KeyEvent.VK_UP))) {
                addOnCounter++;
                reloadCooldown = 40;
                isReloading = false;
            }

            if (addOnCounter == 8) {
                addOn = -1;
                addOnCounter = 0;
            }
        }
    }

    private void checkFieldBounds() {
        if (x <= 0)
            x = 0;
        else if (x + boundsX >= Launcher.width)
            x = Launcher.width - boundsX;
    }

    public void update() {
        getInput();
        checkFieldBounds();
        moveShot();
        checkReloadingTimer();
        checkDeath();
        useAddOn();
    }

    public void render(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 14));

        g.setColor(Color.GREEN);
        g.drawString("SCORE: " + score, 10, 15);

        for (int i = 0; i < life; i++)
            g.drawString("[_]", Launcher.width - (20 * i) - 20, 15);

        g.setColor(Color.YELLOW);
        for (int i = 0; i < shotX.size(); i++)
            g.fillRect(shotX.get(i), shotY.get(i), 5, 10);


        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, 50, 15);

        if (addOn == 1 && addOnCounter < 2)
            g.drawString("PENETRATING MISSILE", 10, Launcher.height - 20);
        if (addOn == 2)
            g.drawString("QUICKSHOTER", 10, Launcher.height - 20);
    }
}
