package pl.fox.spaceinvaders.graphics;

import java.awt.*;
import java.util.ArrayList;

public class PlayerMissileFuel {

    private static ArrayList<Integer> x = new ArrayList<>();
    private static  ArrayList<Integer> y = new ArrayList<>();
    private static ArrayList<Integer> width = new ArrayList<>();


    public static void add(int iks, int ygr, int wdth){
        x.add(iks);
        y.add(ygr);
        width.add(wdth);
    }

    private void resize(){
        for(int i = 0; i < x.size(); i++){
            if(width.get(i) > 0)
                width.set(i, width.get(i) - 1);
            else{
                x.remove(i);
                y.remove(i);
                width.remove(i);
            }
        }
    }


    public void update(){
        resize();
    }

    public void render(Graphics g){
        g.setColor(Color.gray);
        for(int i = 0; i < x.size(); i++){
            g.fillRect(x.get(i), y.get(i), width.get(i), 10);
            g.fillRect(x.get(i) - width.get(i), y.get(i), width.get(i), 10);
        }
    }

    public void clear(){
        x.clear();
        y.clear();
        width.clear();
    }
}
