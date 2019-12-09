package pl.fox.spaceinvaders.field;

import pl.fox.spaceinvaders.Handler;
import pl.fox.spaceinvaders.Launcher;
import pl.fox.spaceinvaders.graphics.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {

    private Handler handler;

    private ArrayList<Float> x;
    private ArrayList<Float> y;
    private ArrayList<Integer> shotX;
    private ArrayList<Integer> shotY;
    private ArrayList<Integer> shotTimer;

    private int direction;
    private float swarmSpeed;
    private int shipBoundsX = 30;
    private int shipBoundsY = 10;

    public Enemy(Handler handler) {
        this.handler = handler;
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
            if (getMissileBounds(i).intersects(handler.getGame().getPlayer().getCollisionBounds())) {
                handler.getGame().getPlayer().subLife();
                PlayerDeath.add(shotX.get(i), shotY.get(i) + 5);
                shotX.remove(i);
                shotY.remove(i);
                return;
            }
        }
    }

    public Rectangle getShipBounds(int index){
        return new Rectangle(x.get(index).intValue(), y.get(index).intValue(), 30, 10);
    }

    public Rectangle getMissileBounds(int index){
        return new Rectangle(shotX.get(index), shotY.get(index), 5, 10);
    }

    private void checkBulletHit() {
        for (int i = 0; i < x.size(); i++) {
            for (int j = 0; j < handler.getGame().getPlayer().getShotX().size(); j++) {
                if (handler.getGame().getPlayer().getShotCollisionBounds(j).intersects(getShipBounds(i))) {
                    handler.getGame().getPlayer().setScore(handler.getGame().getPlayer().getScore() +
                            (100 * handler.getGame().getPlayer().getLevel()));
                    Points.add(x.get(i).intValue() + 20, y.get(i).intValue(), 100 * handler.getGame().getPlayer().getLevel());
                    EnemyDeath.add(x.get(i).intValue(), y.get(i).intValue(), 30, 10);
                    x.remove(i);
                    y.remove(i);
                    if(handler.getGame().getPlayer().getAddOn() != 1){
                        handler.getGame().getPlayer().getShotX().remove(j);
                        handler.getGame().getPlayer().getShotY().remove(j);
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
            swarmSpeed = 0.8f + (float) handler.getGame().getPlayer().getLevel() / 4;
        else if (x.size() > 20 && x.size() <= 30)
            swarmSpeed = 1f + (float) handler.getGame().getPlayer().getLevel() / 4;
        else if (x.size() > 10 && x.size() <= 20)
            swarmSpeed = 1.2f + (float) handler.getGame().getPlayer().getLevel() / 4;
        else if (x.size() <= 10 && x.size() >= 2)
            swarmSpeed = 2f + (float) handler.getGame().getPlayer().getLevel() / 4;
        else if (x.size() < 2)
            swarmSpeed = 6f + (float) handler.getGame().getPlayer().getLevel() / 4;
    }

    private void shoot() {
        checkShootTimer();

        for (int i = 0; i < y.size(); i++) {
            Random rand = new Random();
            int draw = 0;
            if(handler.getGame().getPlayer().getLevel() == 1)
                draw = rand.nextInt(1000);
            else if (handler.getGame().getPlayer().getLevel() == 2)
                draw = rand.nextInt(800);
            else if (handler.getGame().getPlayer().getLevel() > 2)
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
        for(int i = 0; i < handler.getGame().getPlayer().getShotX().size(); i++){
            for(int j = 0; j < shotX.size(); j++){
                if(handler.getGame().getPlayer().getShotCollisionBounds(i).intersects(getMissileBounds(j))){
                    MissileImpact.add(handler.getGame().getPlayer().getShotX().get(i), handler.getGame().getPlayer().getShotY().get(i));
                    handler.getGame().getPlayer().getShotX().remove(i);
                    handler.getGame().getPlayer().getShotY().remove(i);
                    shotX.remove(j);
                    shotY.remove(j);
                    return;
                }
            }
        }
    }

    private void checkAlienWin() {
        for (int i = 0; i < y.size(); i++) {
            if (y.get(i) >= handler.getGame().getPlayer().getY() - 10){
                handler.getGame().getPlayer().setDead(true);
                PlayerDeath.removeAll();
                Points.removeAll();
            }
        }
    }

    private void checkPlayerWin(){
        if(x.size() == 0){
            handler.getGame().getPlayer().setHasWon(true);
            EnemyDeath.removeAll();
            Points.removeAll();
            NextLevel.init();
            shotX.clear();
            shotY.clear();
            shotTimer.clear();
            handler.getGame().getPMF().clear();
            handler.getGame().getPlayer().getShotX().clear();
            handler.getGame().getPlayer().getShotY().clear();
            handler.getGame().getPlayer().setLevel(handler.getGame().getPlayer().getLevel() + 1);
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
        switch(handler.getGame().getPlayer().getLevel()){
            case 1: g.setColor(Color.RED); break;
            case 2: g.setColor(Color.BLUE); break;
            case 3: g.setColor(Color.ORANGE); break;
            case 4: g.setColor(Color.MAGENTA); break;
            case 5: g.setColor(Color.GRAY); break;
            case 6: g.setColor(Color.GREEN); break;
        }

        for (int i = 0; i < x.size(); i++)
            g.fillRect(x.get(i).intValue(), y.get(i).intValue(), 30, 10);

        switch(handler.getGame().getPlayer().getLevel()){
            case 1: g.setColor(Color.GREEN); break;
            case 2: g.setColor(Color.RED); break;
            case 3: g.setColor(Color.BLUE); break;
            case 4: g.setColor(Color.PINK); break;
            case 5: g.setColor(Color.WHITE); break;
        }
        for (int j = 0; j < shotX.size(); j++)
            g.fillRect(shotX.get(j), shotY.get(j), 5, 10);
    }

}
