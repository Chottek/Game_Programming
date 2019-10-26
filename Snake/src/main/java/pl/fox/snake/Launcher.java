package pl.fox.snake;

public class Launcher {


    public static int width = 700;
    public static int height = 400;

    public static void main(String[] args) {
        Game game = new Game("Snake", width, height);
        game.start();

    }
}
