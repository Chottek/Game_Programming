package pl.fox.worldgeneration.field;

import pl.fox.worldgeneration.Handler;
import pl.fox.worldgeneration.field.tiles.Tile;
import pl.fox.worldgeneration.input.KeyManager;

import java.awt.*;

public class World {

    private static final double ROTATION_SPEED = 0.07;

    private final TileMap tileMap;

    private final Player player;

    private double worldAngle = 0.0D;

    private float offsetX;
    private float offsetY;

    public World(Handler handler) {
        tileMap = new TileMap(handler);
        tileMap.init();

        player = new Player((float) handler.getGameWidth() / 2, (float) handler.getGameHeight() / 2, handler);

        //Set player position
        offsetX = 400;
        offsetY = 400;
    }

    public void update() {
        transformWorldByAngle();
        player.update();
        checkCollision();
    }

    public void render(Graphics2D g) {
        var tileMapGraphic = (Graphics2D) g.create();
        tileMapGraphic.rotate(worldAngle, player.getX(), player.getY());
        tileMap.render(tileMapGraphic);
        tileMapGraphic.dispose();

        player.render(g);
    }

    private void checkCollision() {
        tileMap.getTiles().stream()
                .filter(Tile::isCollidable)
                .forEach(tile -> {
                    if (player.getBounds().intersects(tile.getBounds())) {
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

