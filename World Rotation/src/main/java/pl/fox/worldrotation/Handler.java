package pl.fox.worldrotation;

import pl.fox.worldrotation.field.Player;


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

    public int getGameWidth() {
        return game.getWidth();
    }

    public int getGameHeight() {
        return game.getHeight();
    }


}
