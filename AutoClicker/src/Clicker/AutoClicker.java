package Clicker;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class AutoClicker implements Runnable {
    private Robot robot;
    private Color targetColor;
    private int delay;
    private int tolerance;

    public AutoClicker(Color color, int delay, int tolerance) throws AWTException {
        this.robot = new Robot();
        this.targetColor = color;
        this.delay = delay;
        this.tolerance = tolerance;
    }

    public void run() {
        try {
            while (true) {
                Point mousePos = MouseInfo.getPointerInfo().getLocation();
                Color color = robot.getPixelColor(mousePos.x, mousePos.y);
                if (colorDistance(color, targetColor) <= tolerance) {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.delay(10);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.delay(delay);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws AWTException {
        AutoClicker clicker = new AutoClicker(new Color(255, 87, 34), 0, 10);
        Thread t = new Thread(clicker);
        t.start();
    }
    
    private double colorDistance(Color c1, Color c2) {
        double rmean = (c1.getRed() + c2.getRed()) / 2.0;
        double r = c1.getRed() - c2.getRed();
        double g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();
        double weightR = 2.0 + rmean / 256.0;
        double weightG = 4.0;
        double weightB = 2.0 + (255.0 - rmean) / 256.0;
        return Math.sqrt(weightR * r * r + weightG * g * g + weightB * b * b);
    }
}