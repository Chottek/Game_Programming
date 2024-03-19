package pl.fox.worldrotation.field;

import pl.fox.worldrotation.Handler;
import pl.fox.worldrotation.input.KeyManager;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class World {

    private final Player player;
    private final List<CubeObject> cubeObjects = new ArrayList<>();
    private final List<RectObject> rectObjects = new ArrayList<>();

    private double worldAngle = 0.0D;

    private float offsetX;
    private float offsetY;

    public World(Handler handler) {
        player = new Player((float) handler.getGameWidth() / 2, (float) handler.getGameHeight() / 2, handler);
        for (int i = 0; i < 10; i++) {
            cubeObjects.add(new CubeObject(handler, 10, 10 * 3 * i, new Random().nextDouble()));
            cubeObjects.add(new CubeObject(handler, 10 * i * 3, 10, new Random().nextDouble()));

            cubeObjects.add(new CubeObject(handler, 40 + 20 * i, 400, 0));
            cubeObjects.add(new CubeObject(handler, 40 + 20 * i, 50 + 20 * i, 0));
        }

        for (int j = 0; j < 10; j++) {
            rectObjects.add(new RectObject(handler, 600 + 10 * j, 100 + 42 * j, 0));
            rectObjects.add(new RectObject(handler, 500 - 10 * j, 100 + 80 * j, new Random().nextDouble()));
        }
    }

    public void update() {
        transformWorldByAngle();
        player.update();
        cubeObjects.forEach(CubeObject::update);
        rectObjects.forEach(RectObject::update);
        checkCollision();
    }

    public void render(Graphics2D g) {
        player.render(g);
        g.rotate(worldAngle, player.getX(), player.getY()); //Rotate the world in general, do not rotate player
        cubeObjects.forEach(e -> e.render(g)); //Each object can have its own rotation now
        rectObjects.forEach(e -> e.render(g));
    }

    private void checkCollision() {
        for(var cubeObject : cubeObjects) {
            if (cubeObject.getBounds().intersects(player.getBounds())) {
                player.handleCollision();
                break;
            }
        }

        for(var rectObject : rectObjects) {
            if (rectObject.getBounds().intersects(player.getBounds())) {
                player.handleCollision();
                break;
            }
        }
    }

    private void transformWorldByAngle() {
        if (worldAngle > Math.PI * 2) worldAngle = 0;
        if (worldAngle < 0) worldAngle = Math.PI * 2;
        if (KeyManager.rotate_left_q) worldAngle -= 0.07;
        if (KeyManager.rotate_right_e) worldAngle  += 0.07;
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

