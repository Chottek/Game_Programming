package pl.fox.vectorsnake.graphics;

import java.awt.*;
import java.util.ArrayList;

public class PlayerDeath {

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
                if (radius.get(i) < 100) {
                    radius.set(i, radius.get(i) + 5);

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
        g2d.setStroke(new BasicStroke(5));
        g.setColor(Color.RED);

        for (int i = 0; i < x.size(); i++)
            g.drawOval(x.get(i) - radius.get(i), y.get(i) - radius.get(i), 2 * radius.get(i), 2 * radius.get(i));

        g2d.setStroke(new BasicStroke(1));
    }
}
