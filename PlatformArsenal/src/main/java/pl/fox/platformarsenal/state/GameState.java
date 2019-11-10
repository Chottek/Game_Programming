package pl.fox.platformarsenal.state;

import pl.fox.platformarsenal.field.EManager;
import pl.fox.platformarsenal.graphics.JumpIndicator;
import pl.fox.platformarsenal.graphics.ShotCircle;

import java.awt.*;

public class GameState {

    private ShotCircle shotCircle;
    private JumpIndicator jumpIndicator;
    private EManager eManager;

    public GameState(){
        shotCircle = new ShotCircle();
        jumpIndicator = new JumpIndicator();
        eManager = new EManager();
    }

    public void update(){
        eManager.update();
        shotCircle.update();
        jumpIndicator.update();
    }

    public void render(Graphics g){
        shotCircle.render(g);
        eManager.render(g);
        jumpIndicator.render(g);
    }

}
