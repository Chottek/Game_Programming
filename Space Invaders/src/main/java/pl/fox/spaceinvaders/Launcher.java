package pl.fox.spaceinvaders;

public class Launcher {


    public static int width = 600;
    public static int height = 600;

    public static void main(String[] args) {
        Game game = new Game("Space Invaders", width, height);
        game.start();

    }
}
