package pl.fox.platformarsenal.field;

import pl.fox.platformarsenal.Launcher;
import pl.fox.platformarsenal.field.creatures.Enemy;
import pl.fox.platformarsenal.field.creatures.Player;
import pl.fox.platformarsenal.field.dynamics.Box;
import pl.fox.platformarsenal.field.dynamics.Wheel;
import pl.fox.platformarsenal.field.statics.Platform;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class EManager {

    private static ArrayList<Entity> entities;

    private Player player;

    public EManager() {
        entities = new ArrayList<>();
        player = new Player(10, 10);

        addEntity(player);


        addEntity(new Platform(Launcher.width / 2, 450, 200, 7, new Color(255, 162, 0)));
        addEntity(new Platform(200, 450, 100, 10, new Color(0, 83, 255)));
        addEntity(new Platform(600, 200, 50, 10, new Color(202, 63, 255)));
        addEntity(new Platform(100, 300, 70, 10, new Color(177, 255, 172)));
        addEntity(new Platform(400, 500, 150, 10, new Color(193, 255, 0)));
        addEntity(new Platform(100, 550, 400, 5, new Color(90, 0, 255)));
        addEntity(new Platform(700, 400, 285, 7, new Color(255, 143, 0)));
        addEntity(new Platform(400, 120, 170, 7, new Color(0, 255, 175)));
        addEntity(new Platform(Launcher.width / 2, Launcher.height / 2, 266, 7, new Color(0, 83, 255)));
        addEntity(new Platform(Launcher.width / 3, Launcher.height / 3, 29, 7, new Color(0, 83, 255)));

        addEntity(new Box(600, 100, 30, 30, new Color(102, 59, 0)));
        addEntity(new Box(270, 100, 25, 25, new Color(102, 95, 75)));
        addEntity(new Box(300, 0, 50, 25, new Color(102, 95, 75)));
        addEntity(new Box(400, 0, 50, 25, new Color(74, 102, 76)));
        addEntity(new Box(500, 0, 50, 25, new Color(74, 102, 76)));
        addEntity(new Box(600, 0, 15, 50, new Color(102, 97, 95)));

        addEntity(new Wheel(200, 100, 25, 25, new Color(102, 95, 75)));
        addEntity(new Wheel(100, 10, 25, 25, new Color(24, 20, 0)));

        addEntity(new Enemy(250, 100, 20, 20));
    }

    public void update() {
        for (Entity e : entities)
            e.update();
    }

    public void render(Graphics g) {
        for (Entity e : entities)
            e.render(g);
    }

    public static void addEntity(Entity s) {
        entities.add(s);
    }

    public static ArrayList<Entity> getEntities() {
        return entities;
    }


}
