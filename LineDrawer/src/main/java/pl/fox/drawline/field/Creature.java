package pl.fox.drawline.field;

import pl.fox.drawline.Handler;
import pl.fox.drawline.Launcher;

import java.awt.*;
import java.util.ArrayList;

public abstract class Creature {

    protected boolean isDead;
    protected float x, y;
    protected float lastX, lastY;
    protected final float SPEED = 5f;
    protected int deleter;
    protected int score;
    protected int points;

    protected ArrayList<Float> modules_X;
    protected ArrayList<Float> modules_Y;

    protected boolean isLeft, isRight, isUp, isDown;

    protected Handler handler;

    public Creature(Handler handler){
        this.handler = handler;
    }

    public abstract void update();

    public abstract void render(Graphics g);

    public void checkFieldBounds(){
        if(x < 0 || y < 0 || x > Launcher.width || y > Launcher.height)
            isDead = true;
    }


    public void move(){
        if(isDead)
            return;

        lastX = x;
        lastY = y;

        if(isLeft)
            x -= SPEED;
        if(isRight)
            x += SPEED;
        if(isUp)
            y -= SPEED;
        if(isDown)
            y += SPEED;

    }

    public void leaveTrace(){
        if(isDead)
            return;

        modules_X.add(lastX);
        modules_Y.add(lastY);

        deleter = modules_X.size() - 1;
    }

    public void deleteTrace(){
        if(isDead && modules_X.size() != 0){
            for(int i = 0; i < 4; i++){
                modules_X.remove(deleter);
                modules_Y.remove(deleter);
                score++;

                if(deleter > 0)
                    deleter--;
                else
                    break;
            }
        }
    }

    public Rectangle getCollisionBounds(float x, float y){
        return new Rectangle((int) x,(int) y, 5, 5);
    }
}
