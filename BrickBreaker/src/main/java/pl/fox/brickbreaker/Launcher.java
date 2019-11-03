package pl.fox.brickbreaker;

public class Launcher {


    public static int width = 600;
    public static int height = 700;

    public static void main(String[] args) {
        Game game = new Game("Brick Breaker", width, height);
        game.start();

    }
}
