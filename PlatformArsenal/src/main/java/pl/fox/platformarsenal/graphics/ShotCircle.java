package pl.fox.platformarsenal.graphics;


import java.awt.*;
import java.util.ArrayList;

public class ShotCircle {

    private static ArrayList<Integer> x = new ArrayList<>();
    private static  ArrayList<Integer> y = new ArrayList<>();
    private static ArrayList<Integer> radius = new ArrayList<>();


    public static void add(int shotX, int shotY) {
        x.add(shotX);
        y.add(shotY);
        radius.add(0);
    }

    private void resize() {
        if (!x.isEmpty()) {
            for (int i = 0; i < x.size(); i++) {

                if (radius.get(i) < 20)
                    radius.set(i, radius.get(i) + 2);

                 else {
                    x.remove(i);
                    y.remove(i);
                    radius.remove(i);
                }
            }
        }
    }

    public void update() {
        resize();
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        for (int i = 0; i < x.size(); i++)
            drawCircle(g, x.get(i) + 10 , y.get(i) + 10, radius.get(i));
    }

    public void drawCircle(Graphics g, int xCenter, int yCenter, int r) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(6));
        g.drawOval(xCenter - r, yCenter - r, 2 * r, 2 * r);
        g2d.setStroke(new BasicStroke(1));
    }

}
