package pl.fox.drawline.graphics;

import java.awt.*;
import java.util.ArrayList;

public class DeathCircle {

    private ArrayList<Float> x;
    private ArrayList<Float> y;
    private ArrayList<Double> radius;
    private ArrayList<Color> color;

    public DeathCircle(){
        x = new ArrayList<>();
        y = new ArrayList<>();
        radius = new ArrayList<>();
        color = new ArrayList<>();
    }

    public void addCircle(float x1, float y1, Color color1){
        x.add(x1);
        y.add(y1);
        color.add(color1);
        radius.add(0.0);
    }

    private void resize(){
        for(int i = 0; i < radius.size(); i++){
            if(radius.get(i) < 20.0)
                radius.set(i, radius.get(i) + 2);
            else{
                x.remove(i);
                y.remove(i);
                color.remove(i);
                radius.remove(i);
            }
        }
    }

    public void update(){
        resize();
    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        g2d.setStroke(new BasicStroke(4));
        for(int i = 0; i < x.size(); i++){
            g2d.setColor(color.get(i));
            g2d.drawOval( x.get(i).intValue() - radius.get(i).intValue(), y.get(i).intValue() - radius.get(i).intValue(),2 * radius.get(i).intValue(), 2 * radius.get(i).intValue());
        }
    }

    public void clear(){
        x.clear();
        y.clear();
        color.clear();
        radius.clear();
    }
}
