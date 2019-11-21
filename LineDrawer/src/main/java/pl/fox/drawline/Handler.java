package pl.fox.drawline;

import pl.fox.drawline.field.PlayerOne;
import pl.fox.drawline.field.PlayerTwo;
import pl.fox.drawline.input.KeyManager;

public class Handler {

    private Game game;

    public Handler(Game game){
        this.game = game;
    }

    public Game getGame(){
        return game;
    }

    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }

    public PlayerOne getPlayerOne(){
        return this.getGame().getGameState().getPlayerOne();
    }

    public PlayerTwo getPlayerTwo(){
        return this.getGame().getGameState().getPlayerTwo();
    }

}
