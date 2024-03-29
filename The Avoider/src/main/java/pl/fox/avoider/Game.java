package pl.fox.avoider;

import pl.fox.avoider.display.Display;
import pl.fox.avoider.field.Ball;
import pl.fox.avoider.field.Player;
import pl.fox.avoider.input.KeyManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    private Display display;
    private KeyManager keyManager;
    public static Thread thread;
    private BufferStrategy bs;
    private Graphics g;

    private Player player;
    private Ball ball;

    private String title;
    private int width, height;
    private boolean isRunning = false;
    public static int ticks;
    public static boolean isStarted = false;


    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        player = new Player(Launcher.width / 2, Launcher.height / 2);
        ball = new Ball();
        keyManager = new KeyManager();


    }

    private void init() {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getCanvas().addKeyListener(keyManager);
    }

    private void checkIfStarted() {
        if (KeyManager.keyJustPressed(KeyEvent.VK_SPACE))
            isStarted = true;
    }

    private void checkDeathChoice() {
        if (Player.isDead) {
            if (KeyManager.keyJustPressed(KeyEvent.VK_SPACE)) {
                player.init();
                ball.init();
            } else if (KeyManager.keyJustPressed(KeyEvent.VK_ESCAPE))
                System.exit(0);
        }
    }


    private void update() {
        keyManager.update();

        if (!isStarted)
            checkIfStarted();

        else {
            if (!Player.isDead) {
                player.update();
                ball.update();
            } else
                checkDeathChoice();

        }
    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();

        g.clearRect(0, 0, width, height);

        if (!isStarted) {
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.YELLOW);
            g.drawString("Press SPACE to start!", Launcher.width / 4, 25);
        }

        if (!Player.isDead) {
            player.render(g);
            ball.render(g);
        } else {
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.GREEN);
            g.drawString("Score: "+ Player.score, 40, 40);
            g.setColor(Color.RED);
            g.drawString("Press SPACE to retry", 100, Launcher.height / 2);
            g.drawString("or ESC to exit", 130, Launcher.height / 2 + 40);
        }


        bs.show();
        g.dispose();
    }

    public void run() {
        init();
        int FPS = 60;
        double timePerTick = 1000000000 / FPS;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        ticks = 0;

        while (isRunning) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                update();
                render();
                ticks++;
                delta--;
            }
            if (timer >= 1000000000) {
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    public synchronized void start() {
        if (!isRunning) {
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    public synchronized void stop() {
        if (isRunning)
            try {
                thread.join();
            } catch (Exception fox) {
                fox.printStackTrace();
            }
    }


}
