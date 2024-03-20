package pl.fox.worldgeneration.field;

import pl.fox.worldgeneration.Handler;
import pl.fox.worldgeneration.input.KeyManager;

import java.awt.*;

public class Player {

    private final Handler handler;

    private float x, y;
    private float speed = 8.0f;
    private final float width = 20;
    private final float height = 20;

    private float tempOffsetX;
    private float tempOffsetY;

    public Player(float x, float y, Handler handler) {
        this.x = x;
        this.y = y;
        this.handler = handler;
    }

    public void update() {
        movePlayer();
    }

    public void render(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 20));
        g.drawString("X: " + (x - handler.getOffsetX()), 20, 20);
        g.drawString("Y: " + (y - handler.getOffsetY()), 150, 20);
        g.drawString("Angle: " + handler.getWorldRotation(), 20, 40);

        g.setColor(Color.GREEN);
        g.fillRect(
                (int) (x - width / 2),
                (int) (y - height / 2),
                (int) width,
                (int) height
        );

        g.setColor(Color.BLUE);
        g.drawRect(
                getBounds().x,
                getBounds().y,
                getBounds().width,
                getBounds().height
        );

        g.setColor(Color.RED);
        g.drawRect(
                getSightBounds().x,
                getSightBounds().y,
                getSightBounds().width,
                getSightBounds().height
        );
    }

    private void movePlayer() {
        var worldAngle = handler.getWorldRotation();
        tempOffsetX = handler.getOffsetX();
        tempOffsetY = handler.getOffsetY();
        if (KeyManager.arrup) {
            handler.moveOffsetBy(
                    (float) -(speed * Math.sin(worldAngle)),
                    (float) -(speed * Math.cos(worldAngle))
            );
        }

        if (KeyManager.arrdown) {
            handler.moveOffsetBy(
                    (float) (speed / 2 * Math.sin(worldAngle)),
                    (float) (speed / 2 * Math.cos(worldAngle))
            );
        }
    }

    public void handleCollision() {
        handler.setOffset(tempOffsetX, tempOffsetY);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (x - width / 2), (int) (y - height / 2), (int) width, (int) height);
    }

    public Rectangle getSightBounds() {
        //TODO: Adjust it to the rotation if the performance will drop in case of bigger levels
        return new Rectangle((int) (x - handler.getGameWidth()), (int) (y - handler.getGameHeight()), 2 * handler.getGameWidth(), 2 * handler.getGameHeight());
    }

    public float getSpeed() {
        return speed;
    }

    public float getX() {
        return x;
    }

    public float getY() { return y; }

}
