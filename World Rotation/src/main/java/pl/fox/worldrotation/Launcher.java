package pl.fox.worldrotation;

public class Launcher {

    public static int width = 600;
    public static int height = 400;

    public static void main(String[] args) {
        Game game = new Game("World Rotation", width, height);
        game.start();
    }
}
