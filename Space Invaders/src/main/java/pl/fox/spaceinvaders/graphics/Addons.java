package pl.fox.spaceinvaders.graphics;

import pl.fox.spaceinvaders.Handler;
import pl.fox.spaceinvaders.Launcher;
import pl.fox.spaceinvaders.field.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Addons {

    private Handler handler;

    public Addons(Handler handler){
        this.handler = handler;
    }

    private int addOn;
    public ArrayList<Integer> x = new ArrayList<>();
    public ArrayList<Integer> y = new ArrayList<>();
    private ArrayList<Integer> addOns = new ArrayList<>();

    public void draw(){
        Random rand = new Random();
        if(handler.getGame().getPlayer().getLife() == 4)
            addOn = rand.nextInt(3)+1;
        else
            addOn = rand.nextInt(3);

        addOns.add(addOn);
    }

    private void move(){
        for(int i = 0; i < y.size(); i++)
            y.set(i, y.get(i) + 1);
    }

    private void checkFall(){
        for(int i = 0; i < y.size(); i++){
            if(y.get(i) > Launcher.height)
                y.remove(i);
        }
    }

    private void checkPlayerShot(){
        for(int i = 0; i < handler.getGame().getPlayer().getShotX().size(); i++){
            for (int j = 0; j < y.size() ; j++) {
                if(handler.getGame().getPlayer().getShotCollisionBounds(i).intersects(getCollisionBounds(j))){
                    handler.getGame().getPlayer().setAddOn(addOns.get(j));
                    x.remove(j);
                    y.remove(j);
                    addOns.remove(j);
                }
            }
        }
    }

    private void checkPlayerTouch(){
        for(int i = 0; i < y.size(); i++){
            if(getCollisionBounds(i).intersects(handler.getGame().getPlayer().getCollisionBounds())){
                handler.getGame().getPlayer().setAddOn(addOns.get(i));
                x.remove(i);
                y.remove(i);
                addOns.remove(i);
            }
        }
    }

    private Rectangle getCollisionBounds(int index){
        return new Rectangle(x.get(index), y.get(index), 20, 20);
    }

    public void update(){
        if(y.size() > 0){
            move();
            checkFall();
            checkPlayerShot();
            checkPlayerTouch();
        }
    }

    public void render(Graphics g){
        for(int i = 0; i < y.size(); i++){
            switch(addOns.get(i)){
                case 0: g.setColor(Color.GREEN); break;
                case 1: g.setColor(Color.YELLOW); break;
                case 2: g.setColor(Color.MAGENTA); break;
            }
            g.drawRoundRect(x.get(i), y.get(i), 20, 20, 3,3);
        }
    }

}
