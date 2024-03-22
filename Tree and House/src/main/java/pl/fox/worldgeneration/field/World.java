package pl.fox.worldgeneration.field;

import pl.fox.worldgeneration.Handler;
import pl.fox.worldgeneration.field.entity.House;
import pl.fox.worldgeneration.field.entity.Tree;
import pl.fox.worldgeneration.field.tiles.Tile;
import pl.fox.worldgeneration.input.KeyManager;

import java.awt.*;
import java.util.ArrayList;

public class World {

    private static final double ROTATION_SPEED = 0.07;

    private final TileMap tileMap;

    private final Player player;
    private final java.util.List<Tree> trees;
    private final java.util.List<House> houses;

    private double worldAngle = 0.0D;

    private float offsetX;
    private float offsetY;

    public World(Handler handler) {
        tileMap = new TileMap(handler);
        tileMap.init();

        player = new Player((float) handler.getGameWidth() / 2, (float) handler.getGameHeight() / 2, handler);
        setPlayerRelativePosition(1000, 2000);

        trees = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            trees.add(new Tree(handler, 100 + 100 * i, 1000));
            trees.add(new Tree(handler, 200 + 100 * i, 1200));
            trees.add(new Tree(handler, 200 + 100 * i, 1400));
        }

        houses = new ArrayList<>();
        houses.add(new House(handler, 2000, 2000));

    }

    public void update() {
        transformWorldByAngle();
        houses.forEach(House::update);
        trees.forEach(Tree::update);
        player.update();
        checkCollision();
    }

    public void render(Graphics2D g) {
        var tileMapGraphic = (Graphics2D) g.create();
        tileMapGraphic.rotate(worldAngle, player.getX(), player.getY());
        //tileMap.render(tileMapGraphic);
        tileMapGraphic.dispose();

        var housesGraphic = (Graphics2D) g.create();
        housesGraphic.rotate(worldAngle, player.getX(), player.getY());
        houses.forEach(e -> e.render(housesGraphic));
        housesGraphic.dispose();

        player.render(g);

        g.rotate(worldAngle, player.getX(), player.getY());
        trees.forEach(e -> e.render(g));
    }

    private void checkCollision() {
        tileMap.getTiles().stream()
                .filter(Tile::isCollidable)
                .forEach(tile -> {
                    if (player.getBounds().intersects(tile.getBounds())) {
                        player.handleCollision();
                    }
                });

        houses.stream()
                .filter(House::isCollidable)
                .forEach(house -> {
                    if (house.getWallBounds().stream().anyMatch(e -> e.intersects(player.getBounds()))) {
                        player.handleCollision();
                    }
                });

        trees.stream()
                .filter(Tree::isCollidable)
                .forEach(tree -> {
                    if (player.getBounds().intersects(tree.getBounds())) {
                        player.handleCollision();
                    }
                });
    }

    private void transformWorldByAngle() {
        if (worldAngle > Math.PI * 2) worldAngle = 0;
        if (worldAngle < 0) worldAngle = Math.PI * 2;
        if (KeyManager.rotate_left_q) worldAngle -= ROTATION_SPEED;
        if (KeyManager.rotate_right_e) worldAngle += ROTATION_SPEED;
    }

    private void setPlayerRelativePosition(float x, float y) {
        offsetX = (x - player.getX());
        offsetY = (y - player.getY());
    }

    public double getWorldAngle() {
        return worldAngle;
    }

    public void moveOffsetBy(float offsetX, float offsetY) {
        this.offsetX += offsetX;
        this.offsetY += offsetY;
    }

    public Player getPlayer() {
        return player;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }
}

