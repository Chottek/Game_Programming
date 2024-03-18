package pl.fox.worldrotation.field;

import pl.fox.worldrotation.Handler;
import pl.fox.worldrotation.input.KeyManager;

import java.awt.*;

public class Player {
    private final Handler handler;

    private float x, y;
    private float speed = 8.0f;
    private final float width = 20;
    private final float height = 20;

    public Player(float x, float y, Handler handler) {
        this.x = x;
        this.y = y;
        this.handler = handler;
    }

    public void update() {
        movePlayer();
    }

    public void render(Graphics2D g) {
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
    }

    private void movePlayer() {
        var worldAngle = handler.getWorldRotation();
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

    public void pushPlayerBack() {
        var worldAngle = handler.getWorldRotation();
        if (KeyManager.arrup) {
            handler.moveOffsetBy(
                    (float) ((speed + 0.5) * Math.sin(worldAngle)),
                    (float) ((speed + 0.5) * Math.cos(worldAngle))
            );
        }

        if (KeyManager.arrdown) {
            handler.moveOffsetBy(
                    (float) -(speed * Math.sin(worldAngle)),
                    (float) -(speed * Math.cos(worldAngle))
            );
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (x - width / 2), (int) (y - height / 2), (int) width, (int) height);
    }

    public float getSpeed() {
        return speed;
    }

    public float getX() {
        return x;
    }

    public float getY() { return y; }

}
