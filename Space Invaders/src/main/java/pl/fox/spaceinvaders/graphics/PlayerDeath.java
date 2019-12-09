package pl.fox.spaceinvaders.graphics;


import java.awt.*;
import java.util.ArrayList;

public class PlayerDeath {

    private static ArrayList<Integer> x = new ArrayList<>();
    private static ArrayList<Integer> y = new ArrayList<>();
    private static ArrayList<Integer> radius = new ArrayList<>();

    public static void removeAll(){
        x.clear();
        y.clear();
        radius.clear();
    }

    public static void add(int deathX, int deathY) {
        x.add(deathX);
        y.add(deathY);
        radius.add(1);
    }

    private void resize() {
        if (!x.isEmpty()) {
            for (int i = 0; i < x.size(); i++) {
                if ( radius.get(i) < 30) {
                    radius.set(i, radius.get(i) + 5);
                } else {
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(6));
        g.setColor(Color.RED);
        for (int i = 0; i < x.size(); i++)
            drawCircle(g, x.get(i) , y.get(i), radius.get(i));

        g2d.setStroke(new BasicStroke(1));
    }

    public void drawCircle(Graphics g, int xCenter, int yCenter, int r) {
        g.setColor(Color.YELLOW);
        g.drawOval(xCenter - r, yCenter - r, 2 * r, 2 * r);
    }

}
