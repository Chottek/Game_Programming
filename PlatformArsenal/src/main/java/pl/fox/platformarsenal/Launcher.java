package pl.fox.platformarsenal;

public class Launcher {


    public static int width = 1000;
    public static int height = 600;

    public static void main(String[] args) {
        Game game = new Game("Platform Arsenal", width, height);
        game.start();

    }
}
