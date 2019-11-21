package pl.fox.drawline.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    public static boolean[] keys, justPressed, cantPress;
    public static boolean up, left, right, down, arrUp, arrLeft, arrRight, space, arrDown;

    public KeyManager(){
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public void update(){

        for(int i = 0; i < keys.length; i++) {
            if (cantPress[i] && !keys[i]) cantPress[i] = false;
            else if (justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if(!cantPress[i] && keys[i])
                justPressed[i] = true;
        }

        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        arrUp = keys[KeyEvent.VK_UP];
        arrLeft = keys[KeyEvent.VK_LEFT];
        arrRight = keys[KeyEvent.VK_RIGHT];
        arrDown = keys[KeyEvent.VK_DOWN];
        space = keys[KeyEvent.VK_SPACE];

    }

    public boolean keyJustPressed(int keyCode){
        if(keyCode < 0 || keyCode >= keys.length)
            return false;
        return justPressed[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!(e.getKeyCode() < 0 || e.getKeyCode() > keys.length))
            keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!(e.getKeyCode() < 0 || e.getKeyCode() > keys.length))
            keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}