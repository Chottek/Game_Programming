package pl.fox.vectorsnake;

import pl.fox.vectorsnake.field.Field;
import pl.fox.vectorsnake.field.Player;
import pl.fox.vectorsnake.graphics.FoodLocation;
import pl.fox.vectorsnake.graphics.PlayerDeath;
import pl.fox.vectorsnake.graphics.Text;
import pl.fox.vectorsnake.input.KeyManager;

import java.awt.*;
import java.awt.event.KeyEvent;


public class GameState {

    private Player player;
    private Field field;
    private FoodLocation foodLocation;
    private PlayerDeath playerDeath;
    private Handler handler;

    private boolean isStarted, isPaused;

    public GameState(Handler handler) {
        this.handler = handler;
        player = new Player();
        field = new Field(handler);
        foodLocation = new FoodLocation();
        playerDeath = new PlayerDeath();

        isStarted = false;
        isPaused = false;
    }

    private void checkStart() {
        if (KeyManager.keyJustPressed(KeyEvent.VK_SPACE)){
            isStarted = !isStarted;
            field.locateFood();
        }
    }

    private void checkPause(){
        if(player.isDead() || !isStarted)
            return;

        if(KeyManager.keyJustPressed(KeyEvent.VK_ESCAPE))
            isPaused = !isPaused;
    }

    private void checkDeathChoice() {
        if (player.isDead()) {
            if (KeyManager.keyJustPressed(KeyEvent.VK_SPACE)) {
                player.init();
                field.locateFood();
            } else if (KeyManager.keyJustPressed(KeyEvent.VK_ESCAPE))
                System.exit(0);
        }
    }

    public void update() {
        if (!isStarted) {
            checkStart();
            return;
        }

        checkPause();

        if(isPaused)
            return;

        player.update();
        field.update();
        foodLocation.update();

        if(player.isDead()){
            checkDeathChoice();
            player.deleteModulesOnDeath();
            playerDeath.update();
        }
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        player.render(g);
        field.render(g);
        foodLocation.render(g);

        if(player.isDead()){
            playerDeath.render(g);
            Text.drawString(g, "Score: "+ player.getScore(), Launcher.width / 2, 100,
                    true, Color.YELLOW, new Font("Arial", Font.ITALIC, 30));

            if(handler.getGame().getTicks() / 24 % 2 == 0)
            Text.drawString(g, "Press SPACE to retry or ESC to exit", Launcher.width / 2, Launcher.height / 2,
                    true, Color.RED, new Font("Arial", Font.PLAIN, 30));
        }

        g.setColor(Color.GRAY);
        if(isPaused){
            g.fillRoundRect(Launcher.width / 2 - 30, 100, 30, 100, 10, 10);
            g.fillRoundRect(Launcher.width / 2 + 20, 100, 30, 100, 10, 10);
        }

    }

    public Player getPlayer() {
        return player;
    }

    public boolean isStarted() {
        return isStarted;
    }

}
