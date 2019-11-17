package pl.fox.flappyrect.entities;

import pl.fox.flappyrect.Launcher;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Walls {

    private ArrayList<Integer> x;
    private ArrayList<Integer> upLength;
    private ArrayList<Integer> downLength;
    private ArrayList<Rectangle> collisionBoundsUp;
    private ArrayList<Rectangle> collisionBoundsDown;
    private Rectangle playerCollisionBounds;
    private ArrayList<Boolean> isPointed;

    public Walls() {
        init();
    }

    public void init(){
        x = new ArrayList<>();
        upLength = new ArrayList<>();
        downLength = new ArrayList<>();
        collisionBoundsUp = new ArrayList<>();
        collisionBoundsDown = new ArrayList<>();
        playerCollisionBounds = new Rectangle((int) Player.x, (int) Player.y, 30, 20);
        isPointed = new ArrayList<>();
    }

    private void drawWall() {
        if (x.size() > 4)
            return;

        if (x.size() != 0 && x.get(x.size() - 1) >= Launcher.width - 200)
            return;

        Random rand = new Random();
        int uLen = rand.nextInt((Launcher.height / 2 + 100)) + 40;
        int dLen = Launcher.height - uLen - 100;

        x.add(Launcher.width + 20);
        upLength.add(uLen);
        downLength.add(dLen);
        collisionBoundsUp.add(new Rectangle(Launcher.width + 20, 0, 20, uLen));
        collisionBoundsDown.add(new Rectangle(Launcher.width + 20, Launcher.height, 20, -dLen));
        isPointed.add(false);
    }

    private void moveWalls() {
        for (int i = 0; i < x.size(); i++) {
            x.set(i, x.get(i) - 3);
            collisionBoundsUp.set(i, new Rectangle(x.get(i), 0, 20, upLength.get(i)));
            collisionBoundsDown.set(i, new Rectangle(x.get(i), Launcher.height - downLength.get(i), 20, downLength.get(i)));

            if (x.get(i) < -40) {
                x.remove(i);
                upLength.remove(i);
                downLength.remove(i);
                collisionBoundsUp.remove(i);
                collisionBoundsDown.remove(i);
                isPointed.remove(i);
            }
        }

    }

    private void checkCollision() {
        playerCollisionBounds = new Rectangle((int) Player.x, (int) Player.y, 30, 20);
        for (int i = 0; i < x.size(); i++) {
            if (playerCollisionBounds.intersects(collisionBoundsUp.get(i)) || playerCollisionBounds.intersects(collisionBoundsDown.get(i))) {
                Player.isDead = true;
            } else if (Player.x > x.get(i) && !isPointed.get(i)) {
                isPointed.set(i, true);
                Player.score++;
            }
        }
    }

    public void update() {
        drawWall();
        moveWalls();
        checkCollision();
    }

    public void render(Graphics g) {
        for (int i = 0; i < x.size(); i++) {
            if(!isPointed.get(i))
                g.setColor(Color.RED);
            else
                g.setColor(Color.GREEN);

            g.fillRect(x.get(i), 0, 20, upLength.get(i));
            g.fillRect(x.get(i), Launcher.height, 20, -downLength.get(i));
        }
    }
}
