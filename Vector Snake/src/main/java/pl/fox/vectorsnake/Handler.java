package pl.fox.vectorsnake;


import pl.fox.vectorsnake.input.KeyManager;

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


}
