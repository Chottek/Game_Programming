package pl.fox.drawline.field;


import pl.fox.drawline.Handler;
import pl.fox.drawline.Launcher;

import java.awt.*;
import java.util.ArrayList;

public class PlayerTwo extends Creature {

    public PlayerTwo(Handler handler, float x, float y) {
        super(handler);

        this.x = x;
        this.y = y;

        modules_X = new ArrayList<>();
        modules_Y = new ArrayList<>();

        lastX = 0;
        lastY = 0;

        isDown = true;
        isDead = false;

        score = 0;
    }

    private void getInput() {
        if (handler.getKeyManager().arrRight && !isLeft) {
            isRight = true;
            isUp = false;
            isDown = false;
        }
        if (handler.getKeyManager().arrLeft && !isRight) {
            isLeft = true;
            isUp = false;
            isDown = false;
        }
        if (handler.getKeyManager().arrUp && !isDown) {
            isUp = true;
            isLeft = false;
            isRight = false;
        }
        if (handler.getKeyManager().arrDown && !isUp) {
            isDown = true;
            isLeft = false;
            isRight = false;
        }
    }

    private void checkDeath() {
        if (isDead)
            return;

        for (int i = 0; i < modules_X.size(); i++)
            if (getCollisionBounds(x, y).intersects(getCollisionBounds(i))) {
                handler.getGame().getGameState().getDeathCircle().addCircle(x, y, Color.GREEN);
                isDead = true;
                break;
            }
    }

    private void checkHit() {
        if (isDead)
            return;

        for (int i = 0; i < handler.getPlayerOne().getModules_X().size(); i++)
            if (getCollisionBounds(x, y).intersects(handler.getGame().getGameState().getPlayerOne().getCollisionBounds(i))) {
                handler.getGame().getGameState().getDeathCircle().addCircle(x, y, Color.GREEN);
                isDead = true;
                break;
            }
    }

    private void checkFront() {
        if (getCollisionBounds(x, y).intersects(getCollisionBounds(handler.getPlayerOne().getX(), handler.getPlayerOne().getY()))) {
            handler.getGame().getGameState().getDeathCircle().addCircle(x, y, Color.YELLOW);
            isDead = true;
            handler.getPlayerOne().setDeath(true);
        }
    }

    public Rectangle getCollisionBounds(int index) {
        return new Rectangle(modules_X.get(index).intValue(), modules_Y.get(index).intValue(), 5, 5);
    }

    public void reinit() {
        x = Launcher.width - 100;
        y = 20;

        score = 0;
        isDown = true;
        isLeft = false;
        isRight = false;
        isUp = false;
        isDead = false;
        lastX = 0;
        lastY = 0;

        modules_X.clear();
        modules_Y.clear();
    }

    @Override
    public void update() {
        getInput();
        move();
        leaveTrace();
        checkFieldBounds();
        checkDeath();
        checkHit();
        checkFront();
        deleteTrace();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        if (!isDead)
            g.fillRect((int) x, (int) y, 5, 5);

        for (int i = 0; i < modules_X.size(); i++)
            g.fillRect(modules_X.get(i).intValue(), modules_Y.get(i).intValue(), 5, 5);

        if (isDead)
            g.drawString("Score: " + score, Launcher.width - 80, 15);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x){
        this.x = x;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Float> getModules_X() {
        return modules_X;
    }

    public ArrayList<Float> getModules_Y() {
        return modules_Y;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDeath(boolean is) {
        if (is)
            isDead = true;
        else
            isDead = false;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }


}
