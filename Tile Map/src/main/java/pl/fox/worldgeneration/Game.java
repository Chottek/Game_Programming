package pl.fox.worldgeneration;

import pl.fox.worldgeneration.display.Display;
import pl.fox.worldgeneration.field.World;
import pl.fox.worldgeneration.input.KeyManager;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{

    private Display display;
    private final KeyManager keyManager;
    public static Thread thread;
    private BufferStrategy bs;
    private Graphics2D g;

    private final Handler handler;
    private final World world;

    private final String title;
    private final int width;
    private final int height;
    private boolean isRunning = false;
    public static int ticks;

    public Game(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
        handler = new Handler(this);
        world = new World(handler);
        keyManager = new KeyManager();
    }

    private void init(){
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getCanvas().addKeyListener(keyManager);
    }

    private void update(){
        keyManager.update();
        world.update();
    }

    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = (Graphics2D) bs.getDrawGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g.clearRect(0,0, width, height);

        world.render(g);

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

    public World getWorld() {
        return world;
    }
}
