package pl.fox.worldrotation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class RotatingObject extends JPanel {
    private double objectRotation = 0; // Object's initial rotation
    private double worldRotation = 0;  // World's rotation

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Save the original transform
        AffineTransform originalTransform = g2d.getTransform();

        // Rotate the object around its own axis
        AffineTransform objectTransform = new AffineTransform();
        objectTransform.rotate(objectRotation, centerX, centerY);
        g2d.setTransform(objectTransform);

        // Draw the object
        g2d.setColor(Color.RED);
        g2d.fillRect(centerX - 25, centerY - 25, 50, 50);

        // Restore original transform
        g2d.setTransform(originalTransform);

        // Apply transformation relative to the world
        g2d.rotate(worldRotation, centerX, centerY);

        // Draw the world
        g2d.setColor(Color.BLUE);
        g2d.drawLine(centerX - 100, centerY, centerX + 100, centerY);
        g2d.drawLine(centerX, centerY - 100, centerX, centerY + 100);

        g2d.dispose();
    }

    public void rotateObject(double angle) {
        objectRotation += angle;
        repaint();
    }

    public void rotateWorld(double angle) {
        worldRotation += angle;
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rotating Object");
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            RotatingObject rotatingObject = new RotatingObject();
            frame.add(rotatingObject);
            frame.setVisible(true);

            // Rotate the object and the world for demonstration
            rotatingObject.rotateObject(Math.toRadians(45)); // Rotate object by 45 degrees
            rotatingObject.rotateWorld(Math.toRadians(30)); // Rotate world by 30 degrees
        });
    }
}

