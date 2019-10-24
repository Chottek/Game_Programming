package pl.fox.jumpphysics;

public class Launcher {


    public static int width = 600;
    public static int height = 400;

    public static void main(String[] args) {
        Game game = new Game("Jumping Physics Test", width, height);
        game.start();

    }
}
