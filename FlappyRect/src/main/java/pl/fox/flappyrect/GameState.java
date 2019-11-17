package pl.fox.flappyrect;

import pl.fox.flappyrect.entities.Player;
import pl.fox.flappyrect.entities.Walls;
import pl.fox.flappyrect.input.KeyManager;

import java.awt.*;

public class GameState {

    private boolean isStarted;

    private Player player;
    private Walls walls;

    public GameState(){
        player = new Player();
        walls = new Walls();

        isStarted = false;
    }

    private void checkIfIsStarted(){
        if(!isStarted && KeyManager.space)
            isStarted = true;

        if(Player.isDead && KeyManager.space){
            Player.isDead = false;
            player.init();
            walls.init();
        }
    }

    public void update(){
        checkIfIsStarted();

        if(!isStarted)
            return;

        if(Player.isDead)
            return;

        player.update();
        walls.update();
    }

    public void render(Graphics g){
        player.render(g);
        walls.render(g);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        if(!isStarted && Game.ticks / 12 % 2 == 0)
            g.drawString("Press space to begin", 100, 100);

        if(Player.isDead && Game.ticks / 12 % 2 == 0)
            g.drawString("Press space to retry", 100, 100);
    }
}
