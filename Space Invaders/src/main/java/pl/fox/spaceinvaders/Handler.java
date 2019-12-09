package pl.fox.spaceinvaders;

public class Handler {

    private Game game;

    public Handler(Game game){
        this.game = game;
    }

    public Game getGame(){
        return game;
    }

}
