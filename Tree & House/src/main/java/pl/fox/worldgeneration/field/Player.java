package pl.fox.worldgeneration.field;

import pl.fox.worldgeneration.Handler;
import pl.fox.worldgeneration.field.entity.MovingEntity;
import pl.fox.worldgeneration.field.objects.Collectible;
import pl.fox.worldgeneration.input.KeyManager;

import java.awt.*;
import java.util.ArrayList;

public class Player extends MovingEntity {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private float tempOffsetX;
    private float tempOffsetY;
    private float slowDownValue;

    private final java.util.List<Collectible> inventory;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, WIDTH, HEIGHT);
        this.x = x;
        this.y = y;
        this.handler = handler;
        this.inventory = new ArrayList<>();
    }

    @Override
    public void update() {
        movePlayer();
    }

    @Override
    public void render(Graphics2D g) {
        drawInfo(g);

        g.setColor(Color.GREEN);
        g.fillRect((int) (x - WIDTH / 2), (int) (y - HEIGHT / 2), WIDTH, HEIGHT);

        g.setColor(Color.BLUE);
        g.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
    }

    private void movePlayer() {
        var worldAngle = handler.getWorldRotation();
        tempOffsetX = handler.getOffsetX();
        tempOffsetY = handler.getOffsetY();
        var speed = this.speed;
        if (KeyManager.ctrl_l) {
            speed = speed / 2;
        }

        if (KeyManager.shift) {
            speed = speed * 2;
        }

        if (KeyManager.arrup) {
            handler.moveOffsetBy(
                    (float) -(speed * Math.sin(worldAngle)),
                    (float) -(speed * Math.cos(worldAngle))
            );

            slowDownValue = speed;
        }

        if (KeyManager.arrdown) {
            handler.moveOffsetBy(
                    (float) (speed / 2 * Math.sin(worldAngle)),
                    (float) (speed / 2 * Math.cos(worldAngle))
            );
            slowDownValue = 0;
        }

        if (!KeyManager.arrup && slowDownValue > 0) {
            handler.moveOffsetBy(
                    (float) -(slowDownValue * Math.sin(worldAngle)),
                    (float) -(slowDownValue * Math.cos(worldAngle))
            );
            slowDownValue -= 0.5F;
        }
    }

    private void drawInfo(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 20));
        g.drawString("X: " + (x + handler.getOffsetX()), 20, 20);
        g.drawString("Y: " + (y + handler.getOffsetY()), 160, 20);
        g.drawString("Angle: " + handler.getWorldRotation(), 20, 40);
    }

    public void handleCollision() {
        slowDownValue = 0;
        handler.setOffset(tempOffsetX, tempOffsetY);
    }

    public void handleCollectibleGather(Collectible collectible) {
        collectible.setLife(-1); //Just for clearance purposes
        inventory.stream()
                .filter(item -> collectible.getClass().equals(item.getClass())).findFirst()
                .ifPresentOrElse(
                        item -> item.addQuantity(collectible.getQuantity()), //If the object of type is already in inventory, add quantity
                        () -> inventory.add(collectible) //Otherwise add new collectible type to inventory
                );

        System.out.println("Current state of inventory: " + inventory + " -> + " + collectible.getQuantity() + " x "+ collectible.getClass().getSimpleName());
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (x - WIDTH / 2), (int) (y - HEIGHT / 2), WIDTH, HEIGHT);
    }

    public Rectangle getSightBounds() {
        //TODO: Adjust it to the rotation if the performance will drop in case of bigger levels
        double distance = java.awt.geom.Point2D.distance(0, 0, x, y);
        return new Rectangle((int) (x - distance), (int) (y - distance), (int) (x + 2 * distance), (int) (y + 2 * distance));

       // return new Rectangle(-handler.getGameWidth() / 2, -handler.getGameHeight() / 2, 3 * handler.getGameWidth(), 3 * handler.getGameHeight());
//        return new Rectangle((int) (x - handler.getGameWidth()), (int) (y - handler.getGameHeight()), 2 * handler.getGameWidth(), 2 * handler.getGameHeight());
    }

}
