package pl.fox.rotation;

import pl.fox.rotation.display.Display;
import pl.fox.rotation.entities.creatures.Player;
import pl.fox.rotation.input.KeyManager;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{

    private Display display;
    private KeyManager keyManager;
    public static Thread thread;
    private BufferStrategy bs;
    private Graphics g;
    private Player player;

    private String title;
    private int width, height;
    private boolean isRunning = false;
    public static int ticks;


    public Game(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
        player = new Player(200, 100);
        keyManager = new KeyManager();
    }

    private void init(){
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getCanvas().addKeyListener(keyManager);
    }

    private void update(){
        keyManager.update();
        player.update();
    }

    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();

        g.clearRect(0,0, width, height);

        player.render(g);

        bs.show();
        g.dispose();
    }

    public void run(){
        init();
        int FPS = 60;
        double timePerTick = 1000000000 / FPS;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        ticks = 0;

        while(isRunning){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1){
                update();
                render();
                ticks++;
                delta--;
            }
            if(timer >= 1000000000){
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    public synchronized void start(){
        if(!isRunning){
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    public synchronized void stop(){
        if(isRunning)
            try{
                thread.join();
            }catch(Exception fox){fox.printStackTrace();}
    }

    public KeyManager getKeyManager() { return keyManager; }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }


}
