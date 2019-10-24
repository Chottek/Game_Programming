package pl.fox.rotation;


import pl.fox.rotation.display.Display;
import pl.fox.rotation.input.KeyManager;

import javax.swing.*;

public class Handler {

    private Display display;
    private Game game;

    public Handler(Game game){
        this.game = game;
    }

    public KeyManager getKeyManager(){ return game.getKeyManager(); }

    public int getWidth(){
        return game.getWidth();
    }

    public int getHeight(){
        return game.getHeight();
    }

    public Game getGame(){
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Display getDisplay(){ return display;}

    public JFrame getFrame(){
        return display.getFrame();
    }


}
