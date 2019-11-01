package pl.fox.spaceinvaders.field;

import pl.fox.spaceinvaders.Launcher;
import pl.fox.spaceinvaders.graphics.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {

    private ArrayList<Float> x;
    private ArrayList<Float> y;
    private ArrayList<Integer> shotX;
    private ArrayList<Integer> shotY;
    private ArrayList<Integer> shotTimer;

    private int direction;
    private float swarmSpeed;
    private int shipBoundsX = 30;
    private int shipBoundsY = 10;

    public Enemy() {
        init();
    }

    public void init() {
        x = new ArrayList<>();
        y = new ArrayList<>();

        shotX = new ArrayList<>();
        shotY = new ArrayList<>();
        shotTimer = new ArrayList<>();

        direction = 1;
        swarmSpeed = 0.5f;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                x.add(j * 50f + 40);
                y.add(i * 40f + 20);
                shotTimer.add(0);
            }

        }
    }

    public void initNextLevel(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                x.add(j * 50f + 40);
                y.add(i * 40f + 20);
                shotTimer.add(0);
            }
        }
    }

    private void moveSwarm() {
        for (int i = 0; i < x.size(); i++) {
            if (direction == 1)
                x.set(i, x.get(i) + swarmSpeed);
            else
                x.set(i, x.get(i) - swarmSpeed);
        }
    }

    private void checkSwarmBounds() {
        for (int i = 0; i < x.size(); i++) {
            if (x.get(i) <= 0 || x.get(i) + shipBoundsX >= Launcher.width) {
                for (int j = 0; j < y.size(); j++)
                    y.set(j, y.get(j) + 10);
                if (direction == 1)
                    direction = 0;
                else
                    direction = 1;
                return;
            }
        }
    }

    private void checkPlayerHit() {
        for (int i = 0; i < shotX.size(); i++) {
            if (shotX.get(i) >= Player.x && shotX.get(i) <= Player.x + Player.boundsX
                    && shotY.get(i) >= Player.y && shotY.get(i) <= Player.y + 10) {
                Player.life--;
                PlayerDeath.add(shotX.get(i), shotY.get(i) + 5);
                shotX.remove(i);
                shotY.remove(i);
                return;
            }
        }
    }

    private void checkBulletHit() {
        for (int i = 0; i < x.size(); i++) {
            for (int j = 0; j < Player.shotX.size(); j++) {
                if (Player.shotX.get(j) >= x.get(i) && Player.shotX.get(j) < x.get(i) + shipBoundsX
                        && Player.shotY.get(j) >= y.get(i) && Player.shotY.get(j) <= y.get(i) + shipBoundsY) {
                    Player.score += (100 * Player.level);
                    Points.add(x.get(i).intValue() + 20, y.get(i).intValue(), 100 * Player.level);
                    EnemyDeath.add(x.get(i).intValue(), y.get(i).intValue(), 30, 10);
                    x.remove(i);
                    y.remove(i);
                    if(Player.addOn != 1){
                        Player.shotX.remove(j);
                        Player.shotY.remove(j);
                    }

                    return;
                }
            }

        }
    }

    private void setSwarmSpeed() {
        if (x.size() > 35)
            swarmSpeed = 0.5f;
        else if (x.size() > 30 && x.size() <= 34)
            swarmSpeed = 0.8f + (float) Player.level / 4;
        else if (x.size() > 20 && x.size() <= 30)
            swarmSpeed = 1f + (float) Player.level / 4;
        else if (x.size() > 10 && x.size() <= 20)
            swarmSpeed = 1.2f + (float) Player.level / 4;
        else if (x.size() <= 10 && x.size() >= 2)
            swarmSpeed = 2f + (float) Player.level / 4;
        else if (x.size() < 2)
            swarmSpeed = 6f + (float) Player.level / 4;
    }

    private void shoot() {
        checkShootTimer();

        for (int i = 0; i < y.size(); i++) {
            Random rand = new Random();
            int draw = 0;
            if(Player.level == 1)
                draw = rand.nextInt(1000);
            else if (Player.level == 2)
                draw = rand.nextInt(800);
            else if (Player.level > 2)
                draw = rand.nextInt(600);

            if(draw == 2 && shotTimer.get(i) == 100) {
                shotX.add(x.get(i).intValue() + shipBoundsX);
                shotY.add(y.get(i).intValue());
                shotTimer.set(i, 0);
                return;
            }
        }
    }

    private void checkShootTimer() {
        for (int i = 0; i < shotTimer.size(); i++) {
            if (shotTimer.get(i) < 100)
                shotTimer.set(i, shotTimer.get(i) + 1);
        }
    }

    private void moveMissiles() {
        if (shotY.size() == 0)
            return;

        for (int i = 0; i < shotY.size(); i++) {
            shotY.set(i, shotY.get(i) + 5);

            if (shotY.get(i) >= Launcher.height) {
                shotX.remove(i);
                shotY.remove(i);
            }
        }
    }

    private void checkMissilesImpact() {
        for(int i = 0; i < Player.shotX.size(); i++){
            for(int j = 0; j < shotX.size(); j++){
                if(Player.shotX.get(i) >= shotX.get(j) && Player.shotX.get(i) <= shotX.get(j) + 5
                    && Player.shotY.get(i) >= shotY.get(j) && Player.shotY.get(i) <= shotY.get(j) + 10){
                    MissileImpact.add(Player.shotX.get(i), Player.shotY.get(i));
                    Player.shotX.remove(i);
                    Player.shotY.remove(i);
                    shotX.remove(j);
                    shotY.remove(j);
                    return;
                }
            }
        }
    }

    private void checkAlienWin() {
        for (int i = 0; i < y.size(); i++) {
            if (y.get(i) >= Player.y - 10){
                Player.isDead = true;
                PlayerDeath.removeAll();
                Points.removeAll();
            }
        }
    }

    private void checkPlayerWin(){
        if(x.size() == 0){
            Player.hasWon = true;
            EnemyDeath.removeAll();
            Points.removeAll();
            NextLevel.init();
            shotX.clear();
            shotY.clear();
            shotTimer.clear();
            Player.shotX.clear();
            Player.shotY.clear();
            Player.level++;
        }

    }

    public void update() {
        moveSwarm();
        checkSwarmBounds();
        checkBulletHit();
        shoot();
        moveMissiles();
        checkMissilesImpact();
        checkPlayerHit();
        setSwarmSpeed();
        checkAlienWin();
        checkPlayerWin();
    }

    public void render(Graphics g) {
        if(Player.level == 1)
             g.setColor(Color.RED);
        else if(Player.level == 2)
            g.setColor(Color.BLUE);
        else if(Player.level == 3)
            g.setColor(Color.YELLOW);
        else if(Player.level == 4)
            g.setColor(Color.MAGENTA);

        for (int i = 0; i < x.size(); i++)
            g.fillRect(x.get(i).intValue(), y.get(i).intValue(), 30, 10);

        g.setColor(Color.GREEN);
        for (int j = 0; j < shotX.size(); j++)
            g.fillRect(shotX.get(j), shotY.get(j), 5, 10);
    }

}
