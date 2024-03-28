package pl.fox.worldgeneration;

public class Launcher {

    public static int width = 1200;
    public static int height = 700;

    public static void main(String[] args) {
        Game game = new Game("TileMap", width, height);
        game.start();
    }
}
