package pl.fox.worldgeneration.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    public static boolean[] keys, justPressed, cantPress;
    public static boolean left, right, up, arrl, arrr, arrup, arrdown;
    public static boolean rotate_left_q, rotate_right_e;

    public static boolean shift, ctrl_l;

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

        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        up = keys[KeyEvent.VK_W];
        arrup = keys[KeyEvent.VK_UP];
        arrdown = keys[KeyEvent.VK_DOWN];

        arrl = keys[KeyEvent.VK_LEFT];
        arrr = keys[KeyEvent.VK_RIGHT];

        rotate_left_q = keys[KeyEvent.VK_Q];
        rotate_right_e = keys[KeyEvent.VK_E];

        shift = keys[KeyEvent.VK_SHIFT];
        ctrl_l = keys[KeyEvent.VK_CONTROL];
    }

    public static boolean keyJustPressed(int keyCode){
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
