package pl.fox.spaceinvaders.graphics;


import java.awt.*;
import java.util.ArrayList;

public class EnemyDeath {

    private static ArrayList<Integer> x = new ArrayList<>();
    private static ArrayList<Integer> y = new ArrayList<>();
    private static ArrayList<Integer> width = new ArrayList<>();
    private static ArrayList<Integer> height = new ArrayList<>();
    private static ArrayList<Integer> radius = new ArrayList<>();

    public static void removeAll(){
        x.clear();
        y.clear();
        width.clear();
        height.clear();
        radius.clear();
    }

    public static void add(int deathX, int deathY, int shipwidth, int shipheight) {
        x.add(deathX);
        y.add(deathY);
        width.add(shipwidth);
        height.add(shipheight);
        radius.add(1);
    }

    private void resize() {
        if (!x.isEmpty()) {
            for (int i = 0; i < x.size(); i++) {
                if (width.get(i) > 0 || height.get(i) > 0 || radius.get(i) < 30) {
                    radius.set(i, radius.get(i) + 5);
                    width.set(i, width.get(i) - 3);
                    height.set(i, height.get(i) - 1);
                } else {
                    x.remove(i);
                    y.remove(i);
                    width.remove(i);
                    height.remove(i);
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
        for (int i = 0; i < x.size(); i++) {
            g.fillRect(x.get(i), y.get(i), width.get(i), height.get(i));
            drawCircle(g, x.get(i) + 15 , y.get(i) + 5, radius.get(i));
        }
        g2d.setStroke(new BasicStroke(1));
    }

    public void drawCircle(Graphics g, int xCenter, int yCenter, int r) {
        g.setColor(Color.YELLOW);
        g.drawOval(xCenter - r, yCenter - r, 2 * r, 2 * r);
    }

}
