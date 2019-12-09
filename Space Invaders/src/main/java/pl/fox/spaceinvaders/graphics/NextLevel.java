package pl.fox.spaceinvaders.graphics;

import pl.fox.spaceinvaders.Handler;
import pl.fox.spaceinvaders.Launcher;
import pl.fox.spaceinvaders.field.Player;

import java.awt.*;

public class NextLevel {

    private Handler handler;


    private static int fontSize = 1;
    private static int counter;
    public static boolean isDoneAnnouncing;

    public NextLevel(Handler handler){
        this.handler = handler;
    }

    public static void init() {
        isDoneAnnouncing = false;
        fontSize = 1;
        counter = 0;
    }

    public void update() {
        if (!isDoneAnnouncing && fontSize < 150)
            fontSize += 2;
        if (fontSize >= 150) {
            counter++;
        }
        if(counter >= 100){
            isDoneAnnouncing = true;
            handler.getGame().getPlayer().setHasWon(false);
        }
    }

    public void render(Graphics g) {
        if (handler.getGame().getPlayer().isHasWon() && !isDoneAnnouncing) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, fontSize));
            g.drawString("Level " + handler.getGame().getPlayer().getLevel(), 15, Launcher.height / 2);
        }
    }

}
