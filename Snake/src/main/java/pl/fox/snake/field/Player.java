package pl.fox.snake.field;

import pl.fox.snake.Launcher;
import pl.fox.snake.input.KeyManager;

import java.awt.*;

public class Player {


    private final int B_WIDTH = 700;
    private final int B_HEIGHT = 400;
    private final int MODULE_SIZE = 10;
    private final int ALL_MODULES = B_WIDTH*B_HEIGHT;
    private int[] snakex;
    private int[] snakey;

    private int snakeLenght;
    private boolean isDead;

    private boolean isUp, isDown, isLeft, isRight;

    private int score;


    public void init(){
        score = 0;
        snakeLenght = 3;
        snakex = new int[ALL_MODULES];
        snakey = new int[ALL_MODULES];

        for (int i = 0; i < snakeLenght; i++) {
            snakex[i] = 50 - (i * 10);
            snakey[i] = 50;
        }

        isDead = false;
        isRight = true;
        isUp = false;
        isLeft = false;
        isDown = false;

    }

    private void getInput(){
        if((KeyManager.right || KeyManager.arrRight) && !isLeft){
            isUp = false;
            isDown = false;
            isLeft = false;
            isRight = true;
        }

        if((KeyManager.left || KeyManager.arrLeft) && !isRight){
            isUp = false;
            isDown = false;
            isLeft = true;
            isRight = false;
        }

        if((KeyManager.up || KeyManager.arrUp) && !isDown){
            isUp = true;
            isDown = false;
            isLeft = false;
            isRight = false;
        }

        if((KeyManager.down || KeyManager.arrDown) && !isUp){
            isUp = false;
            isDown = true;
            isLeft = false;
            isRight = false;
        }
    }

    private void move() {
        for (int i = snakeLenght; i > 0; i--) {
            snakex[i] = snakex[(i - 1)];
            snakey[i] = snakey[(i - 1)];
        }

        if(isLeft)
            snakex[0] -= MODULE_SIZE;

        if(isRight)
            snakex[0] += MODULE_SIZE;

        if(isUp)
            snakey[0] -= MODULE_SIZE;

        if(isDown)
            snakey[0] += MODULE_SIZE;
    }

    private void checkCollision() {

        for (int i = snakeLenght; i > 0; i--)
            if ((i > 3) && (snakex[0] == snakex[i]) && (snakey[0] == snakey[i])) isDead = true;

            if (snakey[0] < 0 || snakey[0] >= B_HEIGHT) isDead = true;
            if (snakex[0] < 0 || snakex[0] >= B_WIDTH)  isDead = true;
    }


    public void update() {
        getInput();
        move();
        checkCollision();
    }

    public void render(Graphics g) {
        g.setFont(new Font("Arial", Font.ITALIC, 14));
        g.setColor(Color.GREEN);

        if(!((snakex[0] >= 0 && snakex[0] <= 100) && (snakey[0] >= 0 && snakey[0] <= 20)))
            g.drawString("SCORE: " + score, 10, 15);

        g.setColor(Color.RED);
        for(int i = 0; i < snakeLenght; i++) {
            if(i == 0)
                g.fillRoundRect(snakex[i], snakey[i], MODULE_SIZE, MODULE_SIZE, 5, 5);

            else{
                g.setColor(Color.GREEN);
                g.drawRect(snakex[i], snakey[i], MODULE_SIZE, MODULE_SIZE);
            }

        }
    }

    public int[] getSnakex() {
        return snakex;
    }

    public void setSnakex(int[] snakex) {
        this.snakex = snakex;
    }

    public int[] getSnakey() {
        return snakey;
    }

    public void setSnakey(int[] snakey) {
        this.snakey = snakey;
    }

    public int getSnakeLenght() {
        return snakeLenght;
    }

    public void setSnakeLenght(int snakeLenght) {
        this.snakeLenght = snakeLenght;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
