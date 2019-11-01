package pl.fox.spaceinvaders;

import pl.fox.spaceinvaders.display.Display;
import pl.fox.spaceinvaders.field.AlienShip;
import pl.fox.spaceinvaders.field.Enemy;
import pl.fox.spaceinvaders.field.Player;
import pl.fox.spaceinvaders.graphics.*;
import pl.fox.spaceinvaders.input.KeyManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.security.Key;

public class Game implements Runnable {

    private Display display;
    private KeyManager keyManager;
    public static Thread thread;
    private BufferStrategy bs;
    private Graphics g;

    private AlienShip alienShip;
    private Player player;
    private Enemy enemy;
    private Points points;
    private EnemyDeath enemyDeath;
    private PlayerDeath playerDeath;
    private MissileImpact missileImpact;
    private NextLevel nextLevel;

    private String title;
    private int width, height;
    private boolean isRunning = false;
    public static int ticks;
    public static boolean isStarted = false;
    private boolean isPaused;


    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        alienShip = new AlienShip();
        player = new Player();
        enemy = new Enemy();
        keyManager = new KeyManager();
        points = new Points();
        enemyDeath = new EnemyDeath();
        playerDeath = new PlayerDeath();
        missileImpact = new MissileImpact();
        nextLevel = new NextLevel();

        player.init();

        isPaused = false;
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
                isStarted = false;
                player.init();
                enemy.init();
                alienShip.clearStartTimer();
                alienShip.uncheckDrawn();
            } else if (KeyManager.keyJustPressed(KeyEvent.VK_ESCAPE))
                System.exit(0);
        }
    }

    private void checkLevelAnnoucement() {
        if (NextLevel.isDoneAnnouncing) {
            EnemyDeath.removeAll();
            enemy.initNextLevel();
        }
    }

    private void checkPause() {
        if (KeyManager.keyJustPressed(KeyEvent.VK_ESCAPE))
            isPaused = !isPaused;
    }

    private void update() {
        keyManager.update();
        checkPause();

        if (!isStarted)
            checkIfStarted();

        else if (Player.hasWon) {
            nextLevel.update();
            checkLevelAnnoucement();
            alienShip.clearStartTimer();
            alienShip.uncheckDrawn();
        } else if (Player.isDead)
            checkDeathChoice();

        else {
            if (!isPaused) {
                alienShip.update();
                enemy.update();
                player.update();
                points.update();
                enemyDeath.update();
                missileImpact.update();
                playerDeath.update();
            }
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
            g.setColor(Color.GREEN);
            if ((Game.ticks / 12) % 4 == 0)
                g.drawString("Press SPACE to start", 150, Launcher.height / 2);
        }

        if (Player.hasWon)
            nextLevel.render(g);

        if (isPaused) {
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.GREEN);
            g.drawString("PAUSED", Launcher.width / 2 - 100, Launcher.height / 2);
        }


        if (!Player.isDead) {
            alienShip.render(g);
            player.render(g);
            enemy.render(g);
            points.render(g);
            enemyDeath.render(g);
            missileImpact.render(g);
            playerDeath.render(g);
        } else {
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", (Launcher.width - 200) / 2, Launcher.height / 2 - 50);
            g.drawString("Press SPACE to retry or ESC to exit", 45, Launcher.height / 2);
            g.setColor(Color.YELLOW);
            g.drawString("Score: "+ Player.score, (Launcher.width - 200) / 2, Launcher.height / 2 + 50);
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
