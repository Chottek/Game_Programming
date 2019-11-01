package pl.fox.spaceinvaders.graphics;

import java.awt.*;
import java.util.ArrayList;

public class Points {

    private static ArrayList<Integer> x = new ArrayList<>();
    private static ArrayList<Integer> y = new ArrayList<>();
    private static ArrayList<Integer> firstY = new ArrayList<>();
    private static ArrayList<Integer> value = new ArrayList<>();


    public static void removeAll() {
        x.clear();
        y.clear();
        firstY.clear();
        value.clear();
    }

    public static void add(int pointsX, int pointsY, int val) {
        x.add(pointsX);
        y.add(pointsY);
        firstY.add(pointsY);
        value.add(val);
    }

    private void move() {
        if (!x.isEmpty()) {
            for (int i = 0; i < x.size(); i++) {
                if (y.get(i) >= firstY.get(i) - 20)
                    y.set(i, y.get(i) - 1);
                else {
                    x.remove(i);
                    y.remove(i);
                    firstY.remove(i);
                    value.remove(i);
                }
            }
        }
    }

    public void update() {
        move();
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        for (int i = 0; i < x.size(); i++) {
            g.drawString("+" + value.get(i), x.get(i), y.get(i));
        }
    }
}
