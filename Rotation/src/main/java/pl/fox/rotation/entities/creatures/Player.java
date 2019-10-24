package pl.fox.rotation.entities.creatures;


import pl.fox.rotation.Launcher;
import pl.fox.rotation.input.KeyManager;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Player{

    private double angle;
    private float xMove, yMove;
    private float x, y;
    private final float SPEED = 8.0f;

    public Player(float x, float y){
        this.x = x;
        this.y = y;
    }

    private void checkFieldBounds(){
        if(x >= Launcher.width - 10)
            x = Launcher.width - 20;
        if(x <= 0)
            x = 0;
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        if (Math.toRadians(angle) > Math.PI * 2)
            angle = 0;

        if (Math.toRadians(angle) < 0)
            angle = Math.PI * 2;

        if (KeyManager.arrl)
            angle -= 0.07;

        if (KeyManager.arrr)
            angle += 0.07;


        if (KeyManager.arrup) {
            x += SPEED * Math.cos(angle);
            y += SPEED * Math.sin(angle);
        }



        if (KeyManager.arrdown) {
            x += (-SPEED / 2) * (Math.cos(angle));
            y += (-SPEED / 2) * (Math.sin(angle));

        }
    }

    public void update() {
        getInput();
        checkFieldBounds();

    }

    public void render(Graphics g) {


        Graphics2D g2d = (Graphics2D) g;
        AffineTransform backup = g2d.getTransform();
        AffineTransform a = AffineTransform.getRotateInstance(angle, x + 10 , y + 10 );

        g2d.setTransform(a);
        g2d.setColor(Color.RED);
        g2d.fillRect((int) x, (int) y, 20, 20);
        g2d.setTransform(backup);
    }
}
