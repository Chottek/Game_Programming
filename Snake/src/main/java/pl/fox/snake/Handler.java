package pl.fox.snake;

import pl.fox.snake.field.Player;

public class Handler {

    private Game game;

    public Handler(Game game){
        this.game = game;
    }

    public Game getGame(){
        return game;
    }

    public Player getPlayer(){
        return getGame().getPlayer();
    }

}
