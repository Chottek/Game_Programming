package pl.fox.avoider;

public class Launcher {


    public static int width = 500;
    public static int height = 500;

    public static void main(String[] args) {
        Game game = new Game("The Avoider", width, height);
        game.start();

    }
}
