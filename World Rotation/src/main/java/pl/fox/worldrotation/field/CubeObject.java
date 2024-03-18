package pl.fox.worldrotation.field;

import pl.fox.worldrotation.Handler;

import java.awt.*;

public class CubeObject {

    private final Handler handler;
    private final float x;
    private final float y;
    private final int width = 20;
    private final int height = 20;

    public CubeObject(Handler handler, float x, float y) {
        this.handler = handler;
        this.x = x;
        this.y = y;
    }

    public void update() { }

    public void render(Graphics2D g) {
        var playerPosX = handler.getPlayer().getX();
        var playerPosY = handler.getPlayer().getY();

        int objX = (int) (x - width / 2 - handler.getOffsetX());
        int objY = (int) (y - height / 2 - handler.getOffsetY());

        //Draw object
        var g2dObject = (Graphics2D) g.create();
        g2dObject.rotate(handler.getWorldRotation(), playerPosX, playerPosY);
        g2dObject.setColor(Color.RED);
        g2dObject.fillRect(objX, objY, width, height);
        g2dObject.dispose();


        //Draw collision bounds
        g2dObject =(Graphics2D) g.create();
        g2dObject.setColor(Color.BLUE);
        g2dObject.rotate(handler.getWorldRotation(), playerPosX, playerPosY);
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
