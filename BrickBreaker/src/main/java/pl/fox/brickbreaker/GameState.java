package pl.fox.brickbreaker;

import pl.fox.brickbreaker.field.Ball;
import pl.fox.brickbreaker.field.Bricks;
import pl.fox.brickbreaker.field.Player;

import java.awt.*;

public class GameState {

    private Player player;
    private Ball ball;
    private Bricks bricks;

    public GameState(){
        player = new Player();
        ball = new Ball();
        bricks = new Bricks();
    }

    public void update(){
        player.update();
        ball.update();
        bricks.update();
    }

    public void render(Graphics g){
        player.render(g);
        ball.render(g);
        bricks.render(g);
    }

}
