package pl.fox.snake.field;

import pl.fox.snake.Game;
import pl.fox.snake.Handler;
import pl.fox.snake.Launcher;

import java.awt.*;


public class Field {

    private Handler handler;

    private final int RAND_POS_X = 70;
    private final int RAND_POS_Y = 40;
    private final int MODULE_SIZE = 10;

    private int food_x;
    private int food_y;
    private int bon;

    public Field(Handler handler){
        this.handler = handler;
    }


    public void update(){
        checkFood();
    }

    public void render(Graphics g){
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.YELLOW);
        if(!Game.isStarted)
                g.drawString("Press space to start!", Launcher.width / 4, Launcher.height / 2);


        g.setColor(Color.RED);
        g.drawRect(0, 0, Launcher.width - 1, Launcher.height - 1);

        if(bon == 1){
            g.setColor(Color.RED);
            g.fillRect(food_x, food_y, MODULE_SIZE, MODULE_SIZE);
        }
        else{
            g.setColor(Color.YELLOW);
            g.fillRect(food_x, food_y, MODULE_SIZE, MODULE_SIZE);
        }
    }

    public void locateFood() {
        bon = (int)(Math.random() * 15);

        int rand = (int) (Math.random() * RAND_POS_X);
        food_x = ((rand * MODULE_SIZE));

        rand = (int) (Math.random() * RAND_POS_Y);
        food_y = ((rand * MODULE_SIZE));
    }

    private void checkFood() {
        if((handler.getPlayer().getSnakex()[0] == food_x) && (handler.getPlayer().getSnakey()[0] == food_y)) {
            if(bon == 1)
                handler.getPlayer().setScore(handler.getPlayer().getScore() + 100);
            else
                handler.getPlayer().setScore(handler.getPlayer().getScore() + 50);

            handler.getPlayer().setSnakeLenght(handler.getPlayer().getSnakeLenght() + 1);
            locateFood();
        }
    }

}
