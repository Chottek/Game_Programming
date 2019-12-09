package pl.fox.spaceinvaders.graphics;

import java.awt.*;
import java.util.ArrayList;

public class MissileImpact {

    private static ArrayList<Integer> x = new ArrayList<>();
    private static ArrayList<Integer> y = new ArrayList<>();
    private static ArrayList<Integer> radius = new ArrayList<>();

    public static void add(int impactX, int impactY) {
        x.add(impactX);
        y.add(impactY);
        radius.add(1);
    }

    private void resize() {
        if (!x.isEmpty()) {
            for (int i = 0; i < x.size(); i++) {
                if (radius.get(i) < 1000) {
                    radius.set(i, radius.get(i) + 20);

                } else {
                    x.remove(i);
                    y.remove(i);
                    radius.remove(i);
                    return;
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
        g.setColor(Color.WHITE);
        for (int i = 0; i < x.size(); i++)
            drawCircle(g, x.get(i), y.get(i), radius.get(i));

        g2d.setStroke(new BasicStroke(1));
    }

    public void drawCircle(Graphics g, int xCenter, int yCenter, int r) {
        g.drawOval(xCenter - r, yCenter - r, 2 * r, 2 * r);
    }

}
