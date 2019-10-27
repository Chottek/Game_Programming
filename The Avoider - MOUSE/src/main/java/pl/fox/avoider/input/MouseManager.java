package pl.fox.avoider.input;


import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseMotionListener {

    public static int mouseX;
    public static int mouseY;


    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }




}