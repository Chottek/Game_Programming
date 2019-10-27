package pl.fox.avoider.field;

import pl.fox.avoider.Game;
import pl.fox.avoider.Launcher;
import pl.fox.avoider.utils.Convert;


import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Ball {

    private Random rand;

    private ArrayList<Integer> ballx;
    private ArrayList<Integer> bally;
    private ArrayList<Double> angle;
    private ArrayList<Float> speed;
    private ArrayList<Color> color;
    private int ballCount;
    private int lifeTakerCooldown;
    private boolean isLifeTaken;


    public Ball() {
        rand = new Random();
        ballx = new ArrayList<>();
        bally = new ArrayList<>();
        angle = new ArrayList<>();
        speed = new ArrayList<>();
        color = new ArrayList<>();

        init();
    }

    public void init() {
        isLifeTaken = false;
        lifeTakerCooldown = 0;
        ballCount = 0;
        ballx.clear();
        bally.clear();
        angle.clear();
        speed.clear();

        addBalls();
    }


    private void addBalls() {
        int draw = rand.nextInt(500) + 1;

        if (ballCount == 0 || draw == 200) {
            ballx.add(rand.nextInt(400) + 10);
            bally.add(rand.nextInt(400) + 10);
            angle.add(0.1 + (0.7 - 0.1) * rand.nextDouble());
            speed.add(3f + (10f - 3f) * rand.nextFloat());

            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();

            color.add(new Color(r, g, b));

            ballCount++;
        }
    }


    public void update() {
        checkFieldBounds();
        moveBall();
        checkLifeTaker();
        checkPlayerTouch();
        checkBallTouch();
        addBalls();
    }

    public void render(Graphics g) {
        for (int i = 0; i < ballx.size(); i++) {
            g.setColor(color.get(i));
            g.fillOval(ballx.get(i), bally.get(i), 20, 20);
        }
    }

    private void moveBall() {
        int x;
        int y;
        for (int i = 0; i < ballx.size(); i++) {
            x = ballx.get(i);
            x += speed.get(i) * Math.cos(angle.get(i));
            ballx.set(i, x);
        }
        for (int j = 0; j < bally.size(); j++) {
            y = bally.get(j);
            y += speed.get(j) * Math.sin(angle.get(j));
            bally.set(j, y);
        }
    }

    private void checkLifeTaker() {
        if (isLifeTaken && lifeTakerCooldown <= 0) {
            lifeTakerCooldown = 0;
        }
        if (Game.ticks % 60 == 0)
            lifeTakerCooldown++;

        if (lifeTakerCooldown >= 3)
            isLifeTaken = false;

    }

    private void checkFieldBounds() {
        for (int i = 0; i < ballx.size(); i++) {
            if (ballx.get(i) <= 0) {
                Convert.angleVert(angle, i);
                ballx.set(i, 1);
            }
            if (ballx.get(i) >= Launcher.width - 20) {
                Convert.angleVert(angle, i);
                ballx.set(i, Launcher.width - 21);

            }
            if (bally.get(i) <= 0) {
                bally.set(i, 2);
                Convert.angleHor(angle, i);
            }
            if (bally.get(i) >= Launcher.height - 20) {
                Convert.angleHor(angle, i);
                bally.set(i, Launcher.height - 21);
            }
        }
    }

    private void checkPlayerTouch() {
        for (int i = 0; i < ballx.size(); i++) {
            if (!isLifeTaken && (ballx.get(i) + 20 >= Player.x && ballx.get(i) + 20 <= Player.x + 20
                    && bally.get(i) + 20 >= Player.y && bally.get(i) + 20 <= Player.y + 20)) {

                int change = bally.get(i);
                bally.set(i, change + 5);
                Convert.angleVert(angle, i);
                isLifeTaken = true;
                Player.life--;
                break;
            }
        }
    }

    private void checkBallTouch() {
        for (int i = 0; i < ballx.size(); i++) {
            for (int j = 0; j < ballx.size(); j++) {
                if (ballx.get(i) >= ballx.get(j) && ballx.get(i) <= ballx.get(j) + 20 &&
                        bally.get(j) >= bally.get(i) && bally.get(j) <= bally.get(i) + 20) {
                    Convert.angleVert(angle, i);
                    Convert.angleVert(angle, j);
                    break;
                }
            }
        }
    }

}
