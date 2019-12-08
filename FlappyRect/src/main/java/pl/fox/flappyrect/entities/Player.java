package pl.fox.flappyrect.entities;

import pl.fox.flappyrect.Launcher;
import pl.fox.flappyrect.input.KeyManager;

import java.awt.*;

public class Player {

    private float x, y;
    private boolean isJumping;
    private double fallSpeed, jumpSpeed;
    private int score;
    private boolean isDead;

    public Player(){
       init();
    }

    public void init(){
        x = 100f;
        y = Launcher.height / 2;

        isJumping = false;
        fallSpeed = 0;
        jumpSpeed = 0;
        score = 0;
        isDead = false;
    }

    private void isFalling(){
        if(!isJumping && !KeyManager.arrUp){
            y += fallSpeed;
            fallSpeed += 0.5;
        }
    }

    private void isJumping(){

        if(!isJumping && KeyManager.arrUp){
            isJumping = true;
            jumpSpeed = 10;
            fallSpeed = 0;
        }

        if(isJumping){
            y -= jumpSpeed;
            jumpSpeed -= 0.5;
        }

        if(jumpSpeed <= 0)
            isJumping = false;

        if(y + 10 <= 0){
            isJumping = false;
            jumpSpeed = 0;
            y = 10;
        }
    }

    private void checkDeath(){
        if(y > Launcher.height)
            isDead = true;
    }


    public void update(){
        isFalling();
        isJumping();
        checkDeath();
    }

    public void render(Graphics g){
        g.setColor(Color.YELLOW);
        g.fillRoundRect((int) x, (int) y, 30, 20, 10, 10);

        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.ITALIC, 14));
        g.drawString("Score: "+ score, 15, 15);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
