package pl.fox.platformarsenal.graphics;

import java.awt.*;
import java.util.ArrayList;

public class JumpIndicator {

    private static ArrayList<Integer> x = new ArrayList<>();
    private static  ArrayList<Integer> y = new ArrayList<>();
    private static ArrayList<Integer> r = new ArrayList<>();
    private static ArrayList<Color> colors = new ArrayList<>();


    public static void add(int iks, int ygr, Color color){
        x.add(iks);
        y.add(ygr);
        r.add(0);
        colors.add(color);
    }

    private void resize(){
        for(int i = 0; i < x.size(); i++){
            if(r.get(i) < 10)
                r.set(i, r.get(i) + 1);
            else{
                x.remove(i);
                y.remove(i);
                r.remove(i);
                colors.remove(i);
            }
        }
    }

    public void update(){
        resize();
    }

    public void render(Graphics g){
        for(int i = 0; i < x.size(); i++){
            g.setColor(colors.get(i));
            g.drawLine(x.get(i), y.get(i), x.get(i) + r.get(i), y.get(i));
            g.drawLine(x.get(i), y.get(i), x.get(i) - r.get(i), y.get(i));
          //  drawCircle(g, x.get(i), y.get(i), r.get(i));
        }

    }

    public void drawCircle(Graphics g, int xCenter, int yCenter, int r) {
        g.drawOval(xCenter - r, yCenter - r, 2 * r, r);
    }

}
