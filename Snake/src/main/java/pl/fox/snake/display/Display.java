package pl.fox.snake.display;

import javax.swing.*;
import java.awt.*;

public class Display {

    private JFrame frame;
    private Canvas canvas;
    private String title;
    private int width, height;

    public Display(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;

        createDisplay();
    }

    private void createDisplay(){
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setBackground(Color.BLACK);
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();

        frame.setLocationRelativeTo(null);
    }

    public Canvas getCanvas(){
        return canvas;
    }
    public JFrame getFrame(){ return frame; }

}
