package pl.fox.flappyrect;

import pl.fox.flappyrect.entities.Player;
import pl.fox.flappyrect.entities.Walls;
import pl.fox.flappyrect.input.KeyManager;

import java.awt.*;

public class GameState {

    private Handler handler;

    private boolean isStarted;

    private Player player;
    private Walls walls;

    public GameState(Handler handler) {
        this.handler = handler;
        player = new Player();
        walls = new Walls(handler);

        isStarted = false;
    }

    private void checkIfIsStarted() {
        if (!isStarted && KeyManager.space)
            isStarted = true;

        if (handler.getPlayer().isDead() && KeyManager.space) {
            handler.getPlayer().setDead(false);
            player.init();
            walls.init();
        }
    }

    public void update() {
        checkIfIsStarted();

        if (!isStarted)
            return;

        if (handler.getPlayer().isDead())
            return;

        player.update();
        walls.update();
    }

    public void render(Graphics g) {
        player.render(g);
        walls.render(g);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        if (!isStarted && Game.ticks / 12 % 2 == 0)
            g.drawString("Press space to begin", 100, 100);

        if (handler.getPlayer().isDead() && Game.ticks / 12 % 2 == 0)
            g.drawString("Press space to retry", 100, 100);
    }

    public Player getPlayer() {
        return player;
    }

    public Walls getWalls() {
        return walls;
    }
}
