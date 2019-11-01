package pl.fox.spaceinvaders.graphics;

import pl.fox.spaceinvaders.Launcher;
import pl.fox.spaceinvaders.field.Player;

import java.awt.*;

public class NextLevel {

    private static int fontSize = 1;
    private static int counter;
    public static boolean isDoneAnnouncing;


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
            Player.hasWon = false;
        }
    }

    public void render(Graphics g) {
        if (Player.hasWon && !isDoneAnnouncing) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, fontSize));
            g.drawString("Level " + Player.level, 15, Launcher.height / 2);
        }
    }

}
