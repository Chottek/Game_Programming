package pl.fox.worldgeneration.field;

import pl.fox.worldgeneration.Handler;
import pl.fox.worldgeneration.field.entity.Entity;
import pl.fox.worldgeneration.field.entity.House;
import pl.fox.worldgeneration.field.entity.Building;
import pl.fox.worldgeneration.field.entity.Tree;
import pl.fox.worldgeneration.field.entity.Warehouse;
import pl.fox.worldgeneration.field.objects.Collectible;
import pl.fox.worldgeneration.field.objects.Rock;
import pl.fox.worldgeneration.field.objects.Wood;
import pl.fox.worldgeneration.field.tiles.Tile;
import pl.fox.worldgeneration.input.KeyManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    private static final double ROTATION_SPEED = 0.07;
    private final EntityManager entityManager;
    private final TileMap tileMap;

    private double worldAngle = 0.0D;
    private float offsetX;
    private float offsetY;

    public World(Handler handler) {
        tileMap = new TileMap(handler);
        tileMap.init();

        entityManager = new EntityManager(handler);
        setOffsetRelativePosition(2600, 1000);
    }

    public void update() {
        tileMap.update();
        transformWorldByAngle();
        entityManager.update();
    }

    public void render(Graphics2D g) {
        var tileMapGraphic = (Graphics2D) g.create();
        tileMapGraphic.rotate(worldAngle, entityManager.getPlayer().getX(), entityManager.getPlayer().getY());
        tileMap.render(tileMapGraphic);
        tileMapGraphic.dispose();

        entityManager.render(g);
    }

    private void transformWorldByAngle() {
        if (worldAngle > Math.PI * 2) worldAngle = 0;
        if (worldAngle < 0) worldAngle = Math.PI * 2;
        if (KeyManager.rotate_left_q) worldAngle -= ROTATION_SPEED;
        if (KeyManager.rotate_right_e) worldAngle += ROTATION_SPEED;
    }

    public double getWorldAngle() {
        return worldAngle;
    }

    public void moveOffsetBy(float offsetX, float offsetY) {
        this.offsetX += offsetX;
        this.offsetY += offsetY;
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

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setOffsetRelativePosition(float x, float y) {
        this.offsetX = (x - entityManager.getPlayer().getX());
        this.offsetY = (y - entityManager.getPlayer().getY());
    }
}

