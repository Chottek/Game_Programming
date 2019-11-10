package pl.fox.platformarsenal.field.creatures;

import pl.fox.platformarsenal.Launcher;
import pl.fox.platformarsenal.graphics.JumpIndicator;
import pl.fox.platformarsenal.graphics.ShotCircle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Enemy extends Creature {

    public static int points;

    public static ArrayList<Double> bulletX;
    public static ArrayList<Double> bulletY;
    public static ArrayList<Double> angle;

    public static float lastX, lastY;

    private int shotCooldown, shotTimer;

    public static float pushBackSetter;
    public static double pushBackAngleSetter;

    public Enemy(float x, float y, int width, int height) {
        super(x, y, width, height);

        bulletX = new ArrayList<>();
        bulletY = new ArrayList<>();
        angle = new ArrayList<>();

        fallSpeed = 1;
        shotCooldown = 40;
        shotTimer = 0;
    }

    private void checkHit() {
        for (int i = 0; i < Player.bulletX.size(); i++) {
            if ((Player.bulletX.get(i) >= x && Player.bulletX.get(i) <= (x + 20)
                    && Player.bulletY.get(i) >= y) && Player.bulletY.get(i) <= (y + 20)) {
                ShotCircle.add((int) x, (int) y);
                pushBackAngle = Player.angle.get(i);
                System.out.println(Math.toIntExact((long) pushBackAngle));
                pushBack = 10;
                Player.bulletX.remove(i);
                Player.bulletY.remove(i);
                Player.angle.remove(i);
                life -= 20;
                return;
            }
        }
    }

    private void checkIfIsFallen() {
        if (x < -20 || x > Launcher.width || y < -20 || y > Launcher.height || life <= 0) {
            Player.points++;
            x = Launcher.width / 2 - 20;
            y = 100;
            life = 100;
        }

        if (Player.isFallen) {
            points++;
        }
    }

    private void checkFall() {
        yMove = fallSpeed;
    }

    private void pushBack() {
        if (pushBackSetter > 0) {
            pushBack = pushBackSetter;
            pushBackAngle = pushBackAngleSetter;
            pushBackSetter = 0;
            pushBackAngleSetter = 0;
        }

        if (pushBack > 0) {
            x += pushBack * Math.cos(pushBackAngle);

            if (!checkEntityCollisions(0f, yMove))
                y += pushBack * Math.sin(pushBackAngle);
            pushBack -= 0.5;
        }
    }

    private void fallThis() {
        if (!checkEntityCollisions(0f, yMove)) {
            y += fallSpeed;
            fallSpeed += 0.5;
        } else {
            fallSpeed = 0;
            y -= 1;
        }
    }

    private void shoot() {
        if (shotTimer < shotCooldown)
            shotTimer++;

        if (shotTimer == shotCooldown) {
            bulletX.add((double) x);
            bulletY.add((double) y);
            double shotAngle = Math.atan2(y - (Player.lastY + 10), x - (Player.lastX + 10)) - Math.PI;
            angle.add(shotAngle);
            shotTimer = 0;
        }
    }

    private void moveBullets() {
        for (int j = 0; j < bulletX.size(); j++) {
            bulletX.set(j, bulletX.get(j) + 14 * Math.cos(angle.get(j)));
            bulletY.set(j, bulletY.get(j) + 14 * Math.sin(angle.get(j)));

            if (bulletX.get(j) < -10 || bulletX.get(j) > Launcher.width ||
                    bulletY.get(j) < -10 || bulletY.get(j) > Launcher.height) {
                bulletX.remove(j);
                bulletY.remove(j);
                angle.remove(j);
            }
        }
    }

    private void moveThis() {

        if (isJumping)
            JumpIndicator.add((int) x + 10, (int) y + 10, new Color(255, 0, 0));

        if (Math.abs(Player.lastX - x) < 200 && Math.abs(Player.lastX - x) > 10) {
            if (x >= Launcher.width - 50)
                x -= 6f;
            if (x <= 50)
                x += 6f;
            if (x > Player.lastX)
                x += 6f;
            if (Player.lastX > x && x <= 40)
                x += 6f;
            else if (Player.lastX > x && x > 40)
                x -= 6f;


        } else if (Math.abs(Player.lastX - x) > 210) {
            if (x >= Player.lastX)
                x -= 6f;
            else if (Player.lastX > x || x <= 30)
                x += 6f;
        } else if (Math.abs(Player.lastX - x) <= 10) {
            if (Player.lastX > x)
                x += 10f;
            else if (x > Player.lastX)
                x -= 10f;
        }


        if (Math.abs(Player.lastY - y) < 80 && (y > 100)) {
            if (Player.lastY > y)
                isJumping = true;
            if (y > Player.lastY)
                isJumping = true;
        }
        if (y > Launcher.height - 20)
            isJumping = true;

        lastX = x;
        lastY = y;
    }

    @Override
    public void update() {
        checkHit();
        checkFall();
        pushBack();
        fallThis();
        checkIfIsFallen();
        shoot();
        moveBullets();
        moveThis();
        //  jumpThis();
        jump();
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.ITALIC, 15));
        g.drawString("Enemy: " + points, Launcher.width - 80, 60);

        g.setColor(Color.RED);
        g.fillRoundRect((int) x, (int) y, 20, 20, 10, 10);

        g.setColor(Color.RED);
        g.fillRoundRect((int) x - 10, (int) y - 20, 40, 5, 10, 10);
        g.setColor(Color.GREEN);
        g.fillRoundRect((int) x - 10, (int) y - 20, (40 * life) / 100, 5, 10, 10);

        AffineTransform backup = g2d.getTransform();

        for (int i = 0; i < bulletX.size(); i++) {
            AffineTransform a = AffineTransform.getRotateInstance(angle.get(i), bulletX.get(i), bulletY.get(i));
            g2d.setTransform(a);
            g2d.setColor(Color.orange);
            g2d.fillRect(bulletX.get(i).intValue(), bulletY.get(i).intValue(), 7, 2);
        }


        g2d.setTransform(backup);

        g.setColor(new Color(255, 215, 0));
        if (Player.points < points)
            g.fillOval((int) x + 5, (int) y + 5, 10, 10);
    }
}
