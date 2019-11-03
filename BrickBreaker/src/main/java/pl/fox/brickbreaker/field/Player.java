package pl.fox.brickbreaker.field;

import pl.fox.brickbreaker.Launcher;
import pl.fox.brickbreaker.input.KeyManager;

import java.awt.*;

public class Player {

    static float x = (Launcher.width - 10) / 2;
    static final float y = Launcher.height - 25;

    private float speed = 8.0f;

    public Player() {

    }

    private void getInput(){
        if(KeyManager.left || KeyManager.arrLeft)
            x -= speed;
        if(KeyManager.right || KeyManager.arrRight)
            x += speed;
    }

    private void checkFieldBounds(){
        if(x >= Launcher.width - 60)
            x = Launcher.width - 60;
        else if(x <= 0)
            x = 0;
    }

    public void update(){
        getInput();
        checkFieldBounds();
    }

    public void render(Graphics g){
        g.setColor(Color.RED);
        g.fillRoundRect((int) x, (int) y, 60, 20, 10, 10);
    }


}
