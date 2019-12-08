package pl.fox.flappyrect;

import pl.fox.flappyrect.entities.Player;

public class Handler {

    private Game game;

    public Handler(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return this.getGame().getGameState().getPlayer();
    }
}
