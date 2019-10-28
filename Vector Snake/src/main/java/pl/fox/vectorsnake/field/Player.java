package pl.fox.vectorsnake.field;

import pl.fox.vectorsnake.Launcher;
import pl.fox.vectorsnake.input.KeyManager;

import java.awt.*;
import java.util.ArrayList;

public class Player {

    private final int MODULE_SIZE = 10;
    private final float SPEED = 4.0f;


    public static ArrayList<Integer> moduleX;
    public static ArrayList<Integer> moduleY;

    private double angle;

    public static int snakeLenght;
    public static int score;
    public static boolean isDead;


    public Player() {
        moduleX = new ArrayList<>();
        moduleY = new ArrayList<>();

        init();
    }


    public void init() {
        snakeLenght = 6;
        angle = 0.1;

        moduleX.clear();
        moduleY.clear();

        for (int i = 0; i < snakeLenght; i++) {
            moduleX.add(240);
            moduleY.add(200);
        }

        score = 0;
        isDead = false;
    }

    private void checkCollision() {
        for (int i = 9; i < moduleX.size(); i++) {
            if (moduleX.get(0) >= moduleX.get(i) && moduleX.get(0) <= moduleX.get(i) + MODULE_SIZE
                    && moduleY.get(0) >= moduleY.get(i) && moduleY.get(0) <= moduleY.get(i) + MODULE_SIZE)
                isDead = true;
        }
    }

    private void checkFieldBounds() {
        if (moduleX.get(0) <= 0)
            moduleX.set(0, Launcher.width);

        else if (moduleX.get(0) >= Launcher.width)
            moduleX.set(0, 0);

        if (moduleY.get(0) <= 0)
            moduleY.set(0, Launcher.height);

        else if (moduleY.get(0) >= Launcher.height)
            moduleY.set(0, 0);
    }

    private void getInput() {
        if (KeyManager.left || KeyManager.arrLeft)
            angle -= 0.15;

        if (KeyManager.right || KeyManager.arrRight)
            angle += 0.15;

    }

    private void move() {
        int x = moduleX.get(0);
        int y = moduleY.get(0);

        x += SPEED * Math.cos(angle);
        y += SPEED * Math.sin(angle);

        moduleX.set(0, x);
        moduleY.set(0, y);

        for (int i = snakeLenght - 1; i > 0; i--) {
            moduleX.set(i, moduleX.get(i - 1));
            moduleY.set(i, moduleY.get(i - 1));
        }
    }


    public void update() {
        getInput();
        move();
        checkFieldBounds();
        checkCollision();
    }

    public void render(Graphics g) {
        g.setFont(new Font("Arial", Font.ITALIC, 14));
        g.setColor(Color.GREEN);

        if (!((moduleX.get(0) >= 0 && moduleX.get(0) <= 100) && (moduleY.get(0) >= 0 && moduleY.get(0) <= 20)))
            g.drawString("SCORE: " + score, 10, 15);

        g.setColor(Color.RED);
        for (int i = 0; i < moduleX.size(); i++) {
            if (i == 0)
                g.fillOval(moduleX.get(0), moduleY.get(0), MODULE_SIZE, MODULE_SIZE);
            else {
                g.setColor(Color.GREEN);
                g.drawOval(moduleX.get(i), moduleY.get(i), MODULE_SIZE, MODULE_SIZE);
            }

        }
    }
}
