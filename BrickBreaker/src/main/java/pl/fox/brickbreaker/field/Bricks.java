package pl.fox.brickbreaker.field;

import pl.fox.brickbreaker.utils.Convert;

import java.awt.*;
import java.util.ArrayList;

public class Bricks {

    private ArrayList<Float> brickX;
    private ArrayList<Float> brickY;

    public Bricks(){
        brickX = new ArrayList<>();
        brickY = new ArrayList<>();

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 10; j++){
                brickX.add(j * 50f + 50);
                brickY.add(i * 30f + 60);
            }
        }
    }

    private void checkBallTouch(){
        for(int i = 0; i < brickX.size(); i++){
            if(Ball.x >= brickX.get(i) - (2 * Ball.SIZE) && Ball.x <= brickX.get(i) + 40
                && Ball.y >= brickY.get(i) - (2 * Ball.SIZE) && Ball.y <= brickY.get(i) + 20){
                Ball.angle = Convert.angleHor(Ball.angle);
                brickX.remove(i);
                brickY.remove(i);
                return;
            }
        }
    }

    public void update(){
        checkBallTouch();
    }

    public void render(Graphics g){
        g.setColor(Color.GREEN);
        for(int i = 0; i < brickX.size(); i++){
            g.fillRect(brickX.get(i).intValue(), brickY.get(i).intValue(), 40, 20);
        }
    }

}
