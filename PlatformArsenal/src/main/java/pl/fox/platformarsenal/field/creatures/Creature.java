package pl.fox.platformarsenal.field.creatures;

import pl.fox.platformarsenal.field.Entity;

public abstract class Creature extends Entity {


    protected int life;

    protected float xMove, yMove;
    protected float fallSpeed;
    protected float jumpSpeed;
    protected boolean isJumping;
    protected float pushBack;
    protected double pushBackAngle;
    protected int points;

    public Creature(float x, float y, int width, int height) {
        super(x, y, width, height);
        xMove = 0;
        yMove = 0;

        points = 0;
        life = 100;
    }

    public void move() {
        if (!checkEntityCollisions(xMove, 0f)) moveX();

        if (!checkEntityCollisions(0f, yMove)) fall();
        else y -= 1;


        jump();
    }


    public void fall() {
        yMove = fallSpeed;

        if (isJumping) {
            fallSpeed = 1;
            return;
        }

        if (yMove > 0) {
            y += yMove;
            fallSpeed += 0.5;
        }

        if (checkEntityCollisions(0f, yMove))
            fallSpeed = 1;
    }

    public void jump() {
        if (isJumping) {
            y -= jumpSpeed;
            jumpSpeed -= 0.5;
            fallSpeed = 0;
        }

        if (jumpSpeed <= 0) {
            jumpSpeed = 10;
            fallSpeed = 1;
            isJumping = false;
        }

    }

    public void moveX() {
        if (xMove > 0 || xMove < 0)
            x += xMove;
    }
}
