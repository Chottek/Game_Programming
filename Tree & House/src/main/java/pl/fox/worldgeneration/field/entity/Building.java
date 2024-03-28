package pl.fox.worldgeneration.field.entity;

import pl.fox.worldgeneration.Handler;
import pl.fox.worldgeneration.field.Player;

import java.awt.*;

public abstract class Building extends Entity {

    private int roofAlpha = 255;
    protected int roofLeapOver = 30;
    protected int wallWidth = 10;

    protected Color roofColor;
    protected Color floorColor;
    protected Color wallColor;

    public Building(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        this.usableBoundsOffset = 80;
        this.roofColor = new Color(100, 10, 10);
        this.floorColor = new Color(164, 160, 119);
        this.wallColor = Color.DARK_GRAY;
    }

    @Override
    public void update() {
        if (handler.getPlayer().getBounds().intersects(getUsableBounds())) {
            if (roofAlpha > 0) roofAlpha -= 10;
        } else {
            if (roofAlpha < 255) roofAlpha += 6;
        }

        if (roofAlpha < 0) roofAlpha = 0;
        if (roofAlpha > 255) roofAlpha = 255;
    }

    @Override
    public void render(Graphics2D g) {
        var offsetX = (int) (x - handler.getOffsetX() - width / 2);
        var offsetY = (int) (y - handler.getOffsetY() - height / 2);

        drawFloorAndWalls(g, offsetX, offsetY);
        drawRoof(g, offsetX, offsetY);
        drawUsableBounds(g);
    }

    protected void drawFloorAndWalls(Graphics2D g, int offsetX, int offsetY) {
        if (!handler.getPlayer().getBounds().intersects(getUsableBounds()) && roofAlpha == 255) {
            return; //Do not draw it if it's under the roof -> Tryin' to optimize stuff here
        }

        g.setColor(floorColor);
        g.fillRect(offsetX, offsetY, width, height);

        g.setColor(wallColor);
        g.fillRect(offsetX, offsetY, wallWidth, height);
        g.fillRect(offsetX + (width - wallWidth), offsetY, wallWidth, height);

        g.fillRect(offsetX, offsetY, width / 2 - 5 * wallWidth, wallWidth);
        g.fillRect(offsetX + (width / 2 + 5 * wallWidth), offsetY, width / 2 - 5 * wallWidth, wallWidth);

        g.fillRect(offsetX, offsetY + (height - wallWidth), width / 2 - 5 * wallWidth, wallWidth);
        g.fillRect(offsetX + (width / 2 + 5 * wallWidth), offsetY + (height - wallWidth), width / 2 - 5 * wallWidth, wallWidth);
    }

    protected void drawRoof(Graphics2D g, int offsetX, int offsetY) {
        g.setColor(new Color(roofColor.getRed(), roofColor.getGreen(), roofColor.getBlue(), roofAlpha));
        g.fillRect(offsetX - roofLeapOver, offsetY - roofLeapOver, width + roofLeapOver * 2, height + roofLeapOver * 2);
    }

    public java.util.List<Rectangle> getWallBounds() {
        var offsetX = (int) (x - handler.getOffsetX() - width / 2);
        var offsetY = (int) (y - handler.getOffsetY() - height / 2);

        return java.util.List.of(
                new Rectangle(offsetX, offsetY, wallWidth, height),
                new Rectangle(offsetX + (width - wallWidth), offsetY, wallWidth, height),
                new Rectangle(offsetX, offsetY, width / 2 - 5 * wallWidth, wallWidth),
                new Rectangle(offsetX + (width / 2 + 5 * wallWidth), offsetY, width / 2 - 5 * wallWidth, wallWidth),
                new Rectangle(offsetX, offsetY + (height - wallWidth), width / 2 - 5 * wallWidth, wallWidth),
                new Rectangle(offsetX + (width / 2 + 5 * wallWidth), offsetY + (height - wallWidth), width / 2 - 5 * wallWidth, wallWidth)
        );
    }

    @Override
    public boolean isCollidable() {
        return true;
    }


    @Override
    public void handleCollisionWithPlayer(Player p) {
        if (this.getWallBounds().stream().anyMatch(wallBound -> wallBound.intersects(p.getBounds()))) {
            p.handleCollision();
        }
    }
}
