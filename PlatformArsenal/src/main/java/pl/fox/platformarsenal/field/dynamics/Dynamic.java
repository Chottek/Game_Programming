package pl.fox.platformarsenal.field.dynamics;

import pl.fox.platformarsenal.Launcher;
import pl.fox.platformarsenal.field.creatures.Enemy;
import pl.fox.platformarsenal.field.Entity;
import pl.fox.platformarsenal.field.creatures.Player;
import pl.fox.platformarsenal.graphics.ShotCircle;

import java.util.Random;


public abstract class Dynamic extends Entity {

    protected float fallSpeed;
    protected float xMove, yMove;
    protected float pushBack;
    protected double pushBackAngle;


    public Dynamic(float x, float y, int width, int height) {
        super(x, y, width, height);

        fallSpeed = 1;
    }

    public void fall() {
        if (!checkEntityCollisions(0f, yMove)) {
            y += fallSpeed;
            fallSpeed += 0.5;
        } else
            fallSpeed = 0;
        if(checkDynamicEntityCollisions(0f, yMove)){
            y -= 0.1;
        }

    }

    public void pushBack() {
        if (pushBack > 0) {
            x += pushBack * Math.cos(pushBackAngle);
            if (!checkEntityCollisions(0f, yMove))
                y += pushBack * Math.sin(pushBackAngle);

            pushBack -= 0.5;
        }
    }

    public void checkPlayerHit(){
        if(pushBack > 0 && Player.lastX >= x && Player.lastX <= x + bounds.width && Player.lastY >= y && Player.lastY <= y + bounds.height){
            Player.pushBackSetter = pushBack;
            Player.pushBackAngleSetter = pushBackAngle;
        }
    }

    public void checkEnemyHit(){
        if(pushBack > 0 && Enemy.lastX >= x && Enemy.lastX <= x + bounds.width && Enemy.lastY >= y && Enemy.lastY <= y + bounds.height){
            Enemy.pushBackSetter = pushBack;
            Enemy.pushBackAngleSetter = pushBackAngle;
        }
    }

    public void checkFall() {
        yMove = fallSpeed;

        if(y > Launcher.height || y < -20){
            Random rand = new Random();
            x = rand.nextInt(Launcher.width);
            y = rand.nextInt(Launcher.height / 2);
        }
    }

    public void checkHit() {
        for (int i = 0; i < Player.bulletX.size(); i++) {
            if ((Player.bulletX.get(i) >= x && Player.bulletX.get(i) <= (x + bounds.width)
                    && Player.bulletY.get(i) >= y) && Player.bulletY.get(i) <= (y + bounds.height)) {
                ShotCircle.add((int) x, (int) y);
                pushBackAngle = Player.angle.get(i);
                fallSpeed = 0;
                pushBack = 10;
                Player.bulletX.remove(i);
                Player.bulletY.remove(i);
                Player.angle.remove(i);
                return;
            }
        }
        for (int j = 0; j < Enemy.bulletX.size(); j++) {
            if((Enemy.bulletX.get(j) >= x && Enemy.bulletX.get(j) <= (x + bounds.width)
                    && Enemy.bulletY.get(j) >= y && Enemy.bulletY.get(j) <= (y + bounds.height))){
                ShotCircle.add((int) x, (int) y);
                pushBackAngle = Enemy.angle.get(j);
                fallSpeed = 0;
                pushBack = 10;
                Enemy.bulletX.remove(j);
                Enemy.bulletY.remove(j);
                Enemy.angle.remove(j);
                return;
            }
        }
    }


}
