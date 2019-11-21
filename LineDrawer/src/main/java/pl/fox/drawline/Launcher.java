package pl.fox.drawline;

public class Launcher {


    public static int width = 1000;
    public static int height = 700;

    public static void main(String[] args) {
        Game game = new Game("DrawLine", width, height);
        game.start();
    }
}
