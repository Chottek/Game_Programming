package pl.fox.platformarsenal.field.creatures;

import pl.fox.platformarsenal.Launcher;
import pl.fox.platformarsenal.graphics.JumpIndicator;
import pl.fox.platformarsenal.graphics.ShotCircle;
import pl.fox.platformarsenal.input.KeyManager;
import pl.fox.platformarsenal.input.MouseManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Player extends Creature {

    private final float SPEED = 8f;
    private boolean isLeft = false, isRight = false;
    private double slideSpd = 5;
    private int lastMove = 0;

    private int reloadTimer, reloadCooldown;

    public static int points;

    public static boolean isFallen;

    public static ArrayList<Double> bulletX;
    public static ArrayList<Double> bulletY;
    public static ArrayList<Double> angle;

    public static float pushBackSetter;
    public static double pushBackAngleSetter;

    public static float lastX, lastY;

    public Player(float x, float y) {
        super(x, y, 20, 20);

        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 20;
        bounds.height = 20;

        init();
    }


    private void init() {
        isFallen = false;

        bulletX = new ArrayList<>();
        bulletY = new ArrayList<>();
        angle = new ArrayList<>();

        x = 500;
        y = 100;

        points = 0;

        fallSpeed = 1;
        isJumping = false;

        reloadTimer = 0;
        reloadCooldown = 10;

        life = 100;
    }

    private void getInput() {
        xMove = 0;
        yMove = fallSpeed;

        if(isJumping)
            JumpIndicator.add((int) x + 10, (int) y + 10, new Color(0, 226, 27));

        if (KeyManager.left || KeyManager.arrLeft) {
            lastMove = -1;
            isLeft = true;
            isRight = false;
            xMove = -SPEED;
        }
        if (KeyManager.right || KeyManager.arrRight) {
            lastMove = 1;
            isLeft = false;
            isRight = true;
            xMove = SPEED;
        }

        if ((KeyManager.keyJustPressed(KeyEvent.VK_W) || (KeyManager.keyJustPressed(KeyEvent.VK_UP)))) {
            jumpSpeed = 10;
            fallSpeed = 0;
            isJumping = true;
        }

        if (!((KeyManager.left || KeyManager.arrLeft) || (KeyManager.right || KeyManager.arrRight))) {
            isLeft = false;
            isRight = false;
        }

        lastX = x;
        lastY = y;
    }

    private void slide() {
        if (!isLeft && lastMove == -1) {
            x -= slideSpd;
            slideSpd--;
        }
        if (!isRight && lastMove == 1) {
            x += slideSpd;
            slideSpd--;
        }
        if (slideSpd <= 0) {
            lastMove = 0;
            slideSpd = 5;
        }
    }

    private void checkIfHasFallen() {
        if (isFallen) {
            x = Launcher.width / 2;
            y = Launcher.height / 2;
            life = 100;
            isFallen = false;
        }

        if (x < -20 || x > Launcher.width || y < -20 || y > Launcher.height || life <= 0)
            isFallen = true;
    }

    private void checkEnemyBulletHit(){
        for (int i = 0; i < Enemy.bulletX.size(); i++) {
            if ((Enemy.bulletX.get(i) >= x && Enemy.bulletX.get(i) <= (x + 20)
                    && Enemy.bulletY.get(i) >= y) && Enemy.bulletY.get(i) <= (y + 20)) {
                ShotCircle.add((int) x, (int) y);
                pushBackAngle = Enemy.angle.get(i);
                pushBack = 10;
                Enemy.bulletX.remove(i);
                Enemy.bulletY.remove(i);
                Enemy.angle.remove(i);
                life -= 20;
                return;
            }
        }
    }


    private void calculateAngle() {
        if (reloadTimer < reloadCooldown) {
            reloadTimer++;
            return;
        }

        if (MouseManager.isClicked) {
            reloadTimer = 0;
            bulletX.add((double) x);
            bulletY.add((double) y);

            double shotAngle = Math.atan2(y - MouseManager.mouseY, x - MouseManager.mouseX) - Math.PI;
            angle.add(shotAngle);
        }
    }


    private void moveBullets() {
        for (int i = 0; i < bulletX.size(); i++) {
            bulletX.set(i, bulletX.get(i) + (16 * Math.cos(angle.get(i))));
            bulletY.set(i, bulletY.get(i) + (16 * Math.sin(angle.get(i))));

            if (bulletX.get(i) < 0 || bulletX.get(i) > Launcher.width || bulletY.get(i) < 0 || bulletY.get(i) > Launcher.height) {
                bulletX.remove(i);
                bulletY.remove(i);
                angle.remove(i);
            }
        }
    }

    private void pushBack() {
        if(pushBackSetter > 0){
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

    @Override
    public void update() {
        getInput();
        move();
        slide();
        calculateAngle();
        moveBullets();
        checkIfHasFallen();
        checkEnemyBulletHit();
        pushBack();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.ITALIC, 15));
        g.drawString("Player: " + points, Launcher.width - 80, 20);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        AffineTransform backup = g2d.getTransform();

        g2d.setColor(Color.GREEN);
        g2d.fillRoundRect((int) x, (int) y, 20, 20, 10, 10);

        g2d.setColor(Color.YELLOW);
        g2d.drawOval(MouseManager.mouseX - 4, MouseManager.mouseY - 4, 8, 8);

        for (int i = 0; i < bulletX.size(); i++) {
            AffineTransform a = AffineTransform.getRotateInstance(angle.get(i), bulletX.get(i), bulletY.get(i));
            g2d.setTransform(a);
            g2d.setColor(Color.orange);
            g2d.fillRect(bulletX.get(i).intValue(), bulletY.get(i).intValue(), 7, 2);
        }

        g2d.setTransform(backup);

        g.setColor(Color.RED);
        g.fillRoundRect((int) x - 10, (int) y - 20, 40, 5, 10, 10);
        g.setColor(Color.GREEN);
        g.fillRoundRect((int) x - 10, (int) y - 20, (40 * life) / 100, 5, 10, 10);

        g.setColor(new Color(255, 215, 0));
        if(points > Enemy.points)
            g.fillOval((int) x + 5, (int) y + 5, 10, 10);
    }
}