package pl.fox.vectorsnake.field;

import pl.fox.vectorsnake.Handler;
import pl.fox.vectorsnake.Launcher;
import pl.fox.vectorsnake.graphics.FoodLocation;
import pl.fox.vectorsnake.graphics.Text;

import java.awt.*;


public class Field {

    private Handler handler;

    public Field(Handler handler){
        this.handler = handler;
    }

    private final int RAND_POS_X = 70;
    private final int RAND_POS_Y = 40;
    private final int MODULE_SIZE = 10;

    private int food_x = -10;
    private int food_y = -10;
    private int bon;


    public void update() {
        checkFood();
    }

    public void render(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.YELLOW);
        if (!handler.getGame().getGameState().isStarted() &&  handler.getGame().getTicks() / 24 % 2 == 0)
            Text.drawString(g, "Press SPACE to start!", Launcher.width / 2, Launcher.height / 4,
                true, Color.RED, new Font("Arial", Font.PLAIN, 30));


            g.setColor(Color.GREEN);
             g.drawRect(0, 0, Launcher.width - 1, Launcher.height - 1);

        if (bon == 1)
            g.setColor(Color.RED);
         else
            g.setColor(Color.YELLOW);

         g.fillOval(food_x, food_y, MODULE_SIZE, MODULE_SIZE);

    }

    public void locateFood() {
        bon = (int) (Math.random() * 15);

        int rand = (int) (Math.random() * RAND_POS_X);
        food_x = ((rand * MODULE_SIZE));

        rand = (int) (Math.random() * RAND_POS_Y);
        food_y = ((rand * MODULE_SIZE));

        for(int i = 0; i < handler.getGame().getGameState().getPlayer().getModuleX().size(); i++)
            if(getFoodBounds().intersects(handler.getGame().getGameState().getPlayer().getModuleCollisionBounds(i)) ||
                    getFoodBounds().intersects(handler.getGame().getGameState().getPlayer().getScoreBounds())){
                locateFood();
                break;
            }

        FoodLocation.add(food_x + MODULE_SIZE / 2, food_y + MODULE_SIZE / 2);
    }

    private void checkFood() {
            if(handler.getGame().getGameState().getPlayer().getHeadCollisionBounds().intersects(getFoodBounds())){

                if (bon == 1)
                    handler.getGame().getGameState().getPlayer().addScore(100);
                else
                    handler.getGame().getGameState().getPlayer().addScore(50);

            locateFood();

            for (int i = 0; i < 3; i++) {
                handler.getGame().getGameState().getPlayer().getModuleX().add(-20f);
                handler.getGame().getGameState().getPlayer().getModuleY().add(-20f);
                handler.getGame().getGameState().getPlayer().addSnakeLength(1);
            }
        }
    }

    private Rectangle getFoodBounds(){
        return new Rectangle(food_x, food_y, MODULE_SIZE, MODULE_SIZE);
    }

}
