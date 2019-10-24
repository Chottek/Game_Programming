package pl.fox.jumpphysics.entities.creatures;

import pl.fox.jumpphysics.Launcher;
import pl.fox.jumpphysics.input.KeyManager;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player{


    //JUMPS AND SLIDE TIMERS
    private static int jumpCooldown = 40, jumpTimer = jumpCooldown;
    private static int slideCooldown = 6, slideTimer = slideCooldown;
    private boolean isJumping = false, isLeft = false, isRight = false;
    private double fallSpd = 0, jumpSpd = 20, slideSpd = 5;
    private int lastMove = 0;

    //MOVING
    private float xMove, yMove;
    private float x, y;
    private final float SPEED = 8.0f;


    public Player(float x, float y){
        this.x = x;
        this.y = y;
    }


    private void getInput(){
        xMove = 0;
        // yMove = 0;

        if(KeyManager.left){
            xMove = -SPEED;
            lastMove = 0;
            isLeft = true;
        }
        if(KeyManager.right){
            xMove = SPEED;
            lastMove = 1;
            isRight = true;
        }

        if(jumpTimer >= jumpCooldown && KeyManager.keyJustPressed(KeyEvent.VK_W)){
            jumpTimer = 0;
            fallSpd = 0;
            jumpSpd = 10;
            isJumping = true;
        }

        if(!(KeyManager.left)){
            isLeft = false;
        }
        if(!(KeyManager.right)){
            isRight = false;
        }
    }

    private void move(){
        if(xMove > 0){
            if(x <= Launcher.width - 10)
                x += xMove;
        }

        if(xMove < 0){
            if(x >= 0)
                x += xMove;
        }

        if(yMove > 0){
            if(y < Launcher.height - 50)
                y += yMove;
        }

        if(yMove < 0){
            y += yMove;
        }

    }

    private void checkFieldBounds(){
        if(x >= Launcher.width - 10)
            x = Launcher.width - 20;
        if(x <= 0)
            x = 0;
    }


    private void slide(){
        if(!isLeft && lastMove == 0){
            x -= slideSpd;
            slideSpd--;
        }
        if(!isRight && lastMove == 1){
            x += slideSpd;
            slideSpd--;
        }
        if(slideSpd <= 0){
            lastMove = 2;
            isRight = true;
            isLeft = true;
            slideSpd = 5;
        }
    }

    private void jump(){
        jumpTimer++;


        if(isJumping){
            yMove = (float) -jumpSpd;
            jumpSpd -= 0.5;
        }

        if(jumpSpd <= 0)
            isJumping = false;

        if(!isJumping){
            yMove = (float) fallSpd;
            fallSpd += 0.5;
        }

        if(jumpTimer >= jumpCooldown){
            jumpTimer = 40;
            isJumping = false;
        }

    }




    public void update() {
        getInput();
        checkFieldBounds();
        move();
        jump();
        slide();
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) x, (int) y, 20, 20);
    }
}
