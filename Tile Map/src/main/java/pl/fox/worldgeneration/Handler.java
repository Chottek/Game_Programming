package pl.fox.worldgeneration;

import pl.fox.worldgeneration.field.Player;


public class Handler {

    private final Game game;

    public Handler(Game game) {
        this.game = game;
    }

    public double getWorldRotation() {
        return game.getWorld().getWorldAngle();
    }

    public Player getPlayer() {
        return game.getWorld().getPlayer();
    }

    public void moveOffsetBy(float xMove, float yMove) {
        game.getWorld().moveOffsetBy(xMove, yMove);
    }

    public float getOffsetX() {
        return game.getWorld().getOffsetX();
    }

    public float getOffsetY() {
        return game.getWorld().getOffsetY();
    }

    public void setOffset(float offsetX, float offsetY) {
        game.getWorld().setOffsetX(offsetX);
        game.getWorld().setOffsetY(offsetY);
    }

    public int getGameWidth() {
        return game.getWidth();
    }

    public int getGameHeight() {
        return game.getHeight();
    }


}
