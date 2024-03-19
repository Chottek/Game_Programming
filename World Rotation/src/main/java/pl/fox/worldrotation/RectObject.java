package pl.fox.worldrotation.field;

import pl.fox.worldrotation.Handler;

import java.awt.*;

public class RectObject {

    private final Handler handler;
    private final float x;
    private final float y;
    private final int width = 80;
    private final int height = 20;

    private final double angle;

    public RectObject(Handler handler, float x, float y, double angle) {
        this.handler = handler;
        this.x = x;
        this.y = y;

        this.angle = angle;
    }

    public void update() { }

    public void render(Graphics2D g) {
        int objX = (int) (x - width / 2 - handler.getOffsetX());
        int objY = (int) (y - height / 2 - handler.getOffsetY());

        int centerX = objX + width / 2;
        int centerY = objY + height / 2;

        var g2dObject = (Graphics2D) g.create();

        g2dObject.rotate(angle, centerX, centerY);
        g2dObject.setColor(Color.ORANGE);
        g2dObject.fillRect(objX, objY, width, height);

        //Drawing collision bounds
        g2dObject.setColor(Color.BLUE);
        g2dObject.drawRect(
                getBounds().x,
                getBounds().y,
                getBounds().width,
                getBounds().height
        );
        g2dObject.dispose();
    }

    public Rectangle getBounds() {
        return new Rectangle((int)(x - handler.getOffsetX() - width / 2), (int) (y - handler.getOffsetY() - height / 2), width, height);
    }

}
