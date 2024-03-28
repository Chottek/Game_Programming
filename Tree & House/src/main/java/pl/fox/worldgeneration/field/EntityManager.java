package pl.fox.worldgeneration.field;

import pl.fox.worldgeneration.Handler;
import pl.fox.worldgeneration.field.entity.Entity;
import pl.fox.worldgeneration.field.entity.House;
import pl.fox.worldgeneration.field.entity.Tree;
import pl.fox.worldgeneration.field.entity.Warehouse;
import pl.fox.worldgeneration.field.objects.Collectible;
import pl.fox.worldgeneration.field.objects.Rock;
import pl.fox.worldgeneration.field.objects.Wood;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EntityManager {

    private final Handler handler;
    private final CollectibleHandler collectibleHandler;

    private final Map<Collectible, Double> collectibleMap = new HashMap<>();
    private final List<Entity> entities = new ArrayList<>();

    private final Player player;

    public EntityManager(Handler handler) {
        this.handler = handler;
        this.collectibleHandler = new CollectibleHandler(handler);
        this.player = new Player(handler, (float) handler.getGameWidth() / 2, (float) handler.getGameHeight() / 4 * 3);

        for (int i = 0; i < 20; i++) {
            entities.add(new Tree(handler, 600 + 100 * i, 1000));
            entities.add(new Tree(handler, 600 + 100 * i, 1200));
            entities.add(new Tree(handler, 600 + 100 * i, 1400));
        }

        entities.add(new House(handler, 2000, 2000));
        entities.add(new House(handler, 1500, 2000));
        entities.add(new House(handler, 1500, 2500));
        entities.add(new House(handler, 2000, 2500));

        entities.add(new Warehouse(handler, 1500, 4000));

        var rand = new Random();
        for (int i = 0; i < 50; i++) {
            entities.add(new Wood(handler, rand.nextFloat(2000) + 400, rand.nextFloat(1000) + (i * 100), (rand.nextInt(4)) + 1, 100));
            entities.add(new Rock(handler, rand.nextFloat(2000) + 400, rand.nextFloat(1000) + (i * 100), (rand.nextInt(4)) + 1, 100));
        }
    }

    public void update() {
        collectibleHandler.update();
        entities.forEach(Entity::update);
        player.update();
        checkCollision();
        collectibleHandler.checkCollectiblesGather(player.getBounds());
    }

    public void render(Graphics2D g) {
        var entitiesGraphics = (Graphics2D) g.create();
        entitiesGraphics.rotate(handler.getWorldRotation(), handler.getPlayer().getX(), handler.getPlayer().getY());
        entities.stream()
                .filter(this::canPlayerSee)
                .forEach(e -> e.render(entitiesGraphics));

        entitiesGraphics.dispose();

        player.render(g);
    }

    private void checkCollision() {
        entities.stream()
                .filter(Entity::isCollidable)
                .filter(this::canPlayerSee)
                .forEach(entity -> entity.handleCollisionWithPlayer(handler.getPlayer()));
    }

    private boolean canPlayerSee(Entity entity) {
        return handler.getPlayer().getSightBounds().intersects(entity.getBounds());
    }

    public List<Collectible> getCollectibleEntities() {
        return entities.stream()
                .filter(Collectible.class::isInstance)
                .map(Collectible.class::cast)
                .toList();
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    public Player getPlayer() {
        return player;
    }
}
