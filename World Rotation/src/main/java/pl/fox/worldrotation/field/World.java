package pl.fox.worldrotation.field;

import pl.fox.worldrotation.Handler;
import pl.fox.worldrotation.input.KeyManager;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class World {

    private final Player player;
    private final List<CubeObject> objects = new ArrayList<>();

    private double worldAngle = 0.0D;

    private float offsetX;
    private float offsetY;

    public World(Handler handler) {
        player = new Player((float) handler.getGameWidth() / 2, (float) handler.getGameHeight() / 2, handler);
        for (int i = 0; i < 10; i++) {
            objects.add(new CubeObject(handler, 10, 10 * 3 * i));
            objects.add(new CubeObject(handler, 10 * i * 3, 10));

            objects.add(new CubeObject(handler, 40 + 20 * i, 400));
            objects.add(new CubeObject(handler, 40 + 20 * i, 50 + 20 * i));
        }
    }

    public void update() {
        transformWorldByAngle();
        player.update();
        objects.forEach(CubeObject::update);
        checkCollision();
    }

    public void render(Graphics2D g) {
        player.render(g);
        objects.forEach(e -> e.render(g));
    }

    private void checkCollision() {
        //TODO: Find better way to detect and handle collision instead of just pushing the player back
        objects.forEach(object -> {
            if (object.getBounds().intersects(player.getBounds())) {
                player.pushPlayerBack();
            }
        });
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

