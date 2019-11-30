package pl.fox.vectorsnake.field;

import pl.fox.vectorsnake.Handler;
import pl.fox.vectorsnake.Launcher;
import pl.fox.vectorsnake.graphics.PlayerDeath;
import pl.fox.vectorsnake.input.KeyManager;

import java.awt.*;
import java.util.ArrayList;

public class Player {

    private final int MODULE_SIZE = 10;
    private float SPEED;

    private ArrayList<Float> moduleX;
    private ArrayList<Float> moduleY;

    private double angle;

    private int snakeLength;
    private int score;
    private boolean isDead;
    private Rectangle scoreBounds = new Rectangle(0, 0, 100, 20);


    public Player() {

        moduleX = new ArrayList<>();
        moduleY = new ArrayList<>();

        init();
    }


    public void init() {
        snakeLength = 6;
        angle = 0.1;

        moduleX.clear();
        moduleY.clear();

        for (int i = 0; i < snakeLength; i++) {
            moduleX.add(240f);
            moduleY.add(200f);
        }

        score = 0;
        isDead = false;

        SPEED = 4.0f;
    }


    private void checkCollision(){
        for(int i = 10; i < moduleX.size(); i++){
            if(getHeadCollisionBounds().intersects(getModuleCollisionBounds(i))){
                isDead = true;
                PlayerDeath.add(moduleX.get(0).intValue() + 5, moduleY.get(0).intValue() + 5);
                break;
            }
        }
    }

    private void checkFieldBounds() {
        if (moduleX.get(0) <= 0)
            moduleX.set(0,(float) Launcher.width);

        else if (moduleX.get(0) >= Launcher.width)
            moduleX.set(0, 0f);

        if (moduleY.get(0) <= 0)
            moduleY.set(0, (float) Launcher.height);

        else if (moduleY.get(0) >= Launcher.height)
            moduleY.set(0, 0f);
    }

    private void getInput() {
        if (KeyManager.left || KeyManager.arrLeft)
            angle -= 0.15;

        if (KeyManager.right || KeyManager.arrRight)
            angle += 0.15;

    }

    private void move() {
        if(isDead)
            return;

        float x = moduleX.get(0);
        float y = moduleY.get(0);

        x += SPEED * Math.cos(angle);
        y += SPEED * Math.sin(angle);

        moduleX.set(0, x);
        moduleY.set(0, y);

        for (int i = snakeLength - 1; i > 0; i--) {
            moduleX.set(i, moduleX.get(i - 1));
            moduleY.set(i, moduleY.get(i - 1));
        }
    }

    public void deleteModulesOnDeath(){
        if(isDead && moduleX.size() != 0){
            for(int i = 0; i < 4; i++){
                if(snakeLength <= 1)
                    break;

                moduleX.remove(snakeLength - 1);
                moduleY.remove(snakeLength - 1);
                snakeLength--;

            }
        }
    }


    public void update() {
        getInput();
        move();
        checkFieldBounds();
        checkCollision();
    }

    public void render(Graphics g) {
        g.setFont(new Font("Arial", Font.ITALIC, 14));
        g.setColor(Color.GREEN);

        if(!getHeadCollisionBounds().intersects(scoreBounds))
              g.drawString("SCORE: " + score, 10, 15);

        g.setColor(Color.GREEN);
        for (int i = 1; i < moduleX.size(); i++)
            g.fillOval(moduleX.get(i).intValue(), moduleY.get(i).intValue(), MODULE_SIZE, MODULE_SIZE);

        g.setColor(Color.RED);
        if(snakeLength > 1)
        g.fillOval(moduleX.get(0).intValue(), moduleY.get(0).intValue(), MODULE_SIZE, MODULE_SIZE);

    }

    public Rectangle getModuleCollisionBounds(int index){
        return new Rectangle(moduleX.get(index).intValue(), moduleY.get(index).intValue(), MODULE_SIZE, MODULE_SIZE);
    }

    public Rectangle getHeadCollisionBounds(){
        return new Rectangle(moduleX.get(0).intValue(), moduleY.get(0).intValue(), MODULE_SIZE, MODULE_SIZE);
    }

    public ArrayList<Float> getModuleX() {
        return moduleX;
    }

    public void setModuleX(ArrayList<Float> moduleX) {
        this.moduleX = moduleX;
    }

    public ArrayList<Float> getModuleY() {
        return moduleY;
    }

    public void setModuleY(ArrayList<Float> moduleY) {
        this.moduleY = moduleY;
    }

    public boolean isDead(){
        return isDead;
    }

    public int getScore(){
        return score;
    }

    public void addScore(int score){
        this.score += score;
    }

    public void addSnakeLength(int snakeLength){
        this.snakeLength += snakeLength;
    }

    public Rectangle getScoreBounds(){
        return scoreBounds;
    }


}
