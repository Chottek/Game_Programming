package pl.fox.rotation;

public class Launcher {


    public static int width = 600;
    public static int height = 400;

    public static void main(String[] args) {
        Game game = new Game("Rotation test", width, height);
        game.start();

    }
}
