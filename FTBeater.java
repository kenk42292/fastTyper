import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Arrays;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.RescaleOp;

class FTBeater {
    public static void main(String[] args) {
        try {
            int XLC = Integer.parseInt(args[0]); //348, 144
            int YLC = Integer.parseInt(args[1]);
            int X = XLC + 81;
            int Y = YLC + 175;
            
            Robot screenReader = new Robot();
            screenReader.setAutoWaitForIdle(true);
            screenReader.delay(10);
            /**Switch to chrome Fast Typer window from command prompt. */
            screenReader.mouseMove(XLC + 288, YLC + 20);
            screenReader.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            screenReader.delay(40);
            screenReader.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            /*Calibrate vertical position on site. */
            screenReader.mouseWheel(-20);
            screenReader.delay(20);
            screenReader.mouseWheel(5);
            screenReader.delay(20);
            while (true) {
                
                //Should wait a second to allow word to switch on screen
                screenReader.delay(270);
                // Obtain buffered image of area to be captured
                BufferedImage image = getImage(screenReader, WIDTH, HEIGHT, X, Y);
                screenReader.delay(10);
                int[] word = readImage(screenReader, image);
                screenReader.delay(10);
                type(screenReader, word);
            }
        } catch (AWTException e) {
            System.out.println("Robot making failed.");
            System.exit(1);
        }
    }
    static BufferedImage getImage(Robot r, int width, int height, int x, int y) {
        Rectangle rect = new Rectangle(x, y, width, height);
        return r.createScreenCapture(rect);
    }
    static int[] readImage(Robot r, BufferedImage image) {
        BufferedImage[] arrayCharImage = new BufferedImage[10];
        int charX = 0;
        int charY = 0;
        for (int i = 0; i < 10; i++) {
            r.delay(5);
            arrayCharImage[i] = image.getSubimage(charX, charY, CHARWIDTH, CHARHEIGHT);
            r.delay(5);
            charX += CHARWIDTH;
        }
        // Now we have a BufferedImage-array of all the character images we need
        int[] result = new int[10];
        for (int i = 0; i < 10; i++) {
            r.delay(15);
            //RescaleOp filter = new RescaleOp((float) 1.03, 0, null);
            //filter.filter(arrayCharImage[i], arrayCharImage[i]);
            result[i] = readCharImage(arrayCharImage[i]);
        }
        return result;
    }
    static boolean closeColor(int actual, int ideal, int threshold) {
        //Red
        int redCom = (actual << 8) >>> 24;
        int redColor = (ideal << 8) >>> 24;
        int redDif = Math.abs(redCom - redColor);
        //Green
        int greenCom = (actual << 16) >>> 24;
        int greenColor = (ideal << 16) >>> 24;
        int greenDif = Math.abs(greenCom - greenColor);
        //Blue
        int blueCom = (actual << 24) >>> 24;
        int blueColor = (ideal << 24) >>> 24;
        int blueDif = Math.abs(blueCom - blueColor);
        return (redDif + greenDif + blueDif)/3 < threshold;
    }
    static int readCharImage(BufferedImage r) {
        int THRES = 178;//178
        //if (wordNum > 50) {
        //    THRES = 180;
        //}
        int BLACK = 0xFF000000;
         //B
        if (closeColor(r.getRGB(6, 10), BLACK, THRES) &&
                closeColor(r.getRGB(9, 20), BLACK, THRES) &&
                closeColor(r.getRGB(8, 29), BLACK, THRES) &&
                closeColor(r.getRGB(15, 17), BLACK, THRES) &&
                closeColor(r.getRGB(21, 26), BLACK, THRES) &&
                closeColor(r.getRGB(15, 34), BLACK, THRES)) {
            return KeyEvent.VK_B;
        }
         //K
        if (closeColor(r.getRGB(8, 9), BLACK, THRES) &&
                closeColor(r.getRGB(9, 20), BLACK, THRES) &&
                closeColor(r.getRGB(23, 17), BLACK, THRES) &&
                closeColor(r.getRGB(10, 27), BLACK, THRES) &&
                closeColor(r.getRGB(7, 34), BLACK, THRES) &&
                closeColor(r.getRGB(15, 21), BLACK, THRES) &&
                closeColor(r.getRGB(11, 35), BLACK, THRES) &&
                closeColor(r.getRGB(15, 26), BLACK, THRES) &&
                closeColor(r.getRGB(7, 28), BLACK, THRES) &&
                closeColor(r.getRGB(15, 24), BLACK, THRES) &&
                closeColor(r.getRGB(19, 18), BLACK, THRES) &&
                closeColor(r.getRGB(20, 34), BLACK, THRES)) {
            return KeyEvent.VK_K;
        }
         //H
        if (closeColor(r.getRGB(8, 10), BLACK, THRES) &&
                closeColor(r.getRGB(8, 9), BLACK, THRES) &&
                closeColor(r.getRGB(10, 22), BLACK, THRES) &&
                closeColor(r.getRGB(9, 34), BLACK, THRES) &&
                closeColor(r.getRGB(23, 25), BLACK, THRES) &&
                closeColor(r.getRGB(5, 35), BLACK, THRES) &&
                closeColor(r.getRGB(12, 35), BLACK, THRES) &&
                closeColor(r.getRGB(20, 17), BLACK, THRES) &&
                closeColor(r.getRGB(23, 25), BLACK, THRES) &&
                closeColor(r.getRGB(20, 35), BLACK, THRES) &&
                closeColor(r.getRGB(26, 35), BLACK, THRES)) {
            return KeyEvent.VK_H;
         }
         //F
        if (closeColor(r.getRGB(22, 11), BLACK, THRES) &&
                closeColor(r.getRGB(18, 8), BLACK, THRES) &&
                closeColor(r.getRGB(12, 11), BLACK, THRES) &&
                closeColor(r.getRGB(11, 17), BLACK, THRES) &&
                closeColor(r.getRGB(8, 16), BLACK, THRES) &&
                closeColor(r.getRGB(16, 17), BLACK, THRES) &&
                closeColor(r.getRGB(12, 25), BLACK, THRES) &&
                closeColor(r.getRGB(12, 34), BLACK, THRES) &&
                closeColor(r.getRGB(6, 34), BLACK, THRES) &&
                closeColor(r.getRGB(17, 34), BLACK, THRES)) {
            return KeyEvent.VK_F;
        }
         //T
        if (closeColor(r.getRGB(11, 9), BLACK, THRES) &&
                closeColor(r.getRGB(11, 16), BLACK, THRES) &&
                closeColor(r.getRGB(7, 16), BLACK, THRES) &&
                closeColor(r.getRGB(16, 16), BLACK, THRES) &&
                closeColor(r.getRGB(11, 22), BLACK, THRES) &&
                closeColor(r.getRGB(11, 31), BLACK, THRES) &&
                closeColor(r.getRGB(15, 34), BLACK, THRES) &&
                closeColor(r.getRGB(19, 31), BLACK, THRES) &&
                closeColor(r.getRGB(19, 28), BLACK, THRES)) {
            return KeyEvent.VK_T;
        }
         //L
        if (closeColor(r.getRGB(6, 9), BLACK, THRES) &&
                closeColor(r.getRGB(13, 9), BLACK, THRES) &&
                closeColor(r.getRGB(13, 15), BLACK, THRES) &&
                closeColor(r.getRGB(13, 25), BLACK, THRES) &&
                closeColor(r.getRGB(6, 34), BLACK, THRES) &&
                closeColor(r.getRGB(13, 34), BLACK, THRES) &&
                closeColor(r.getRGB(19, 34), BLACK, THRES)) {
            return KeyEvent.VK_L;
        }

         //D
        if (closeColor(r.getRGB(15, 8), BLACK, THRES) &&
                closeColor(r.getRGB(18, 9), BLACK, THRES) &&
                closeColor(r.getRGB(18, 18), BLACK, THRES) &&
                closeColor(r.getRGB(18, 26), BLACK, THRES) &&
                closeColor(r.getRGB(20, 34), BLACK, THRES) &&
                closeColor(r.getRGB(12, 16), BLACK, THRES) &&
                closeColor(r.getRGB(5, 25), BLACK, THRES) &&
                closeColor(r.getRGB(12, 34), BLACK, THRES)) {
            return KeyEvent.VK_D;
        }
         //G
        if (closeColor(r.getRGB(22, 19), BLACK, THRES) &&
                closeColor(r.getRGB(15, 18), BLACK, THRES) &&
                closeColor(r.getRGB(8, 24), BLACK, THRES) &&
                closeColor(r.getRGB(17, 28), BLACK, THRES) &&
                closeColor(r.getRGB(9, 29), BLACK, THRES) &&
                closeColor(r.getRGB(5, 32), BLACK, THRES) &&
                closeColor(r.getRGB(7, 35), BLACK, THRES) &&
                closeColor(r.getRGB(20, 36), BLACK, THRES) &&
                closeColor(r.getRGB(8, 44), BLACK, THRES) &&
                closeColor(r.getRGB(20, 44), BLACK, THRES)) {
            return KeyEvent.VK_G;
        }
         //Q
        if (closeColor(r.getRGB(10, 16), BLACK, THRES) &&
                closeColor(r.getRGB(5, 25), BLACK, THRES) &&
                closeColor(r.getRGB(11, 35), BLACK, THRES) &&
                closeColor(r.getRGB(17, 19), BLACK, THRES) &&
                closeColor(r.getRGB(18, 26), BLACK, THRES) &&
                closeColor(r.getRGB(18, 35), BLACK, THRES) &&
                closeColor(r.getRGB(19, 42), BLACK, THRES)) {
            return KeyEvent.VK_Q;
        }
        //P
        if (closeColor(r.getRGB(7, 17), BLACK, THRES) &&
                closeColor(r.getRGB(8, 23), BLACK, THRES) &&
                closeColor(r.getRGB(17, 17), BLACK, THRES) &&
                closeColor(r.getRGB(23, 25), BLACK, THRES) &&
                closeColor(r.getRGB(18, 34), BLACK, THRES) &&
                closeColor(r.getRGB(9, 32), BLACK, THRES) &&
                closeColor(r.getRGB(8, 37), BLACK, THRES) &&
                closeColor(r.getRGB(6, 42), BLACK, THRES) &&
                closeColor(r.getRGB(9, 42), BLACK, THRES) &&
                closeColor(r.getRGB(13, 42), BLACK, THRES)) {
            return KeyEvent.VK_P;
        }
        //Y
        if (closeColor(r.getRGB(9, 19), BLACK, THRES) &&
                closeColor(r.getRGB(12, 27), BLACK, THRES) &&
                closeColor(r.getRGB(21, 19), BLACK, THRES) &&
                closeColor(r.getRGB(19, 25), BLACK, THRES) &&
                closeColor(r.getRGB(15, 33), BLACK, THRES) &&
                closeColor(r.getRGB(6, 42), BLACK, THRES)) {
            return KeyEvent.VK_Y;
        }
        
        //A
        if (closeColor(r.getRGB(6, 19), BLACK, THRES) &&
                closeColor(r.getRGB(13, 15), BLACK, THRES) &&
                closeColor(r.getRGB(20, 19), BLACK, THRES) &&
                closeColor(r.getRGB(20, 29), BLACK, THRES) &&
                closeColor(r.getRGB(23, 34), BLACK, THRES) &&
                closeColor(r.getRGB(22, 34), BLACK, THRES) &&
                closeColor(r.getRGB(14, 24), BLACK, THRES) &&
                closeColor(r.getRGB(11, 34), BLACK, THRES) &&
                closeColor(r.getRGB(10, 25), BLACK, THRES) &&
                closeColor(r.getRGB(4, 30), BLACK, THRES)) {
            return KeyEvent.VK_A;
        }
        
        //S
        if (closeColor(r.getRGB(17, 18), BLACK, THRES) &&
                closeColor(r.getRGB(7, 17), BLACK, THRES) &&
                closeColor(r.getRGB(5, 22), BLACK, THRES) &&
                closeColor(r.getRGB(6, 23), BLACK, THRES) &&
                closeColor(r.getRGB(6, 23), BLACK, THRES) &&
                closeColor(r.getRGB(18, 24), BLACK, THRES) &&
                closeColor(r.getRGB(11, 24), BLACK, THRES) &&
                closeColor(r.getRGB(20, 27), BLACK, THRES) &&
                closeColor(r.getRGB(12, 25), BLACK, THRES) &&
                closeColor(r.getRGB(13, 24), BLACK, THRES) &&
                closeColor(r.getRGB(19, 29), BLACK, THRES) &&
                closeColor(r.getRGB(14, 34), BLACK, THRES) &&
                closeColor(r.getRGB(6, 32), BLACK, THRES)) {
            return KeyEvent.VK_S;
        }

         //J
        if (closeColor(r.getRGB(14, 11), BLACK, THRES) &&
                closeColor(r.getRGB(9, 18), BLACK, THRES) &&
                closeColor(r.getRGB(15, 18), BLACK, THRES) &&
                closeColor(r.getRGB(16, 26), BLACK, THRES) &&
                closeColor(r.getRGB(16, 36), BLACK, THRES) &&
                closeColor(r.getRGB(14, 43), BLACK, THRES) &&
                closeColor(r.getRGB(7, 38), BLACK, THRES)) {
            return KeyEvent.VK_J;
        }

        
         //W
        if (closeColor(r.getRGB(7, 17), BLACK, THRES) &&
                closeColor(r.getRGB(7, 23), BLACK, THRES) &&
                closeColor(r.getRGB(11, 32), BLACK, THRES) &&
                closeColor(r.getRGB(13, 23), BLACK, THRES) &&
                closeColor(r.getRGB(16, 19), BLACK, THRES) &&
                closeColor(r.getRGB(18, 25), BLACK, THRES) &&
                closeColor(r.getRGB(21, 32), BLACK, THRES) &&
                closeColor(r.getRGB(23, 26), BLACK, THRES) &&
                closeColor(r.getRGB(25, 18), BLACK, THRES)) {
            return KeyEvent.VK_W;
        }
        
         //M
        if (closeColor(r.getRGB(7, 34), BLACK, THRES) &&
                closeColor(r.getRGB(7, 21), BLACK, THRES) &&
                closeColor(r.getRGB(13, 17), BLACK, THRES) &&
                closeColor(r.getRGB(16, 25), BLACK, THRES) &&
                closeColor(r.getRGB(16, 33), BLACK, THRES) &&
                closeColor(r.getRGB(22, 17), BLACK, THRES) &&
                closeColor(r.getRGB(24, 27), BLACK, THRES) &&
                closeColor(r.getRGB(24, 34), BLACK, THRES)) {
            return KeyEvent.VK_M;
        }
        
         //Z
        if (closeColor(r.getRGB(6, 21), BLACK, THRES) &&
                closeColor(r.getRGB(7, 17), BLACK, THRES) &&
                closeColor(r.getRGB(10, 17), BLACK, THRES) &&
                closeColor(r.getRGB(15, 17), BLACK, THRES) &&
                closeColor(r.getRGB(18, 18), BLACK, THRES) &&
                closeColor(r.getRGB(15, 22), BLACK, THRES) &&
                closeColor(r.getRGB(11, 26), BLACK, THRES) &&
                closeColor(r.getRGB(7, 31), BLACK, THRES) &&
                closeColor(r.getRGB(6, 34), BLACK, THRES) &&
                closeColor(r.getRGB(10, 34), BLACK, THRES) &&
                closeColor(r.getRGB(14, 34), BLACK, THRES) &&
                closeColor(r.getRGB(17, 34), BLACK, THRES) &&
                closeColor(r.getRGB(19, 32), BLACK, THRES) &&
                closeColor(r.getRGB(20, 28), BLACK, THRES)) {
            return KeyEvent.VK_Z;
        }
         //X
        if (closeColor(r.getRGB(10, 17), BLACK, THRES) &&
                closeColor(r.getRGB(20, 17), BLACK, THRES) &&
                closeColor(r.getRGB(15, 23), BLACK, THRES) &&
                closeColor(r.getRGB(15, 27), BLACK, THRES) &&
                closeColor(r.getRGB(10, 34), BLACK, THRES) &&
                closeColor(r.getRGB(21, 34), BLACK, THRES) &&
                closeColor(r.getRGB(12, 21), BLACK, THRES) &&
                closeColor(r.getRGB(18, 21), BLACK, THRES) &&
                closeColor(r.getRGB(12, 30), BLACK, THRES) &&
                closeColor(r.getRGB(19, 31), BLACK, THRES)) {
            return KeyEvent.VK_X;
        }
        
         //I
        if (closeColor(r.getRGB(13, 19), BLACK, THRES) &&
                closeColor(r.getRGB(8, 16), BLACK, THRES) &&
                closeColor(r.getRGB(12, 16), BLACK, THRES) &&
                closeColor(r.getRGB(13, 21), BLACK, THRES) &&
                closeColor(r.getRGB(13, 29), BLACK, THRES) &&
                closeColor(r.getRGB(7, 34), BLACK, THRES) &&
                closeColor(r.getRGB(13, 32), BLACK, THRES) &&
                closeColor(r.getRGB(19, 33), BLACK, THRES)) {
            return KeyEvent.VK_I;
        }
        

         //N
        if (closeColor(r.getRGB(7, 16), BLACK, THRES) &&
                closeColor(r.getRGB(10, 21), BLACK, THRES) &&
                closeColor(r.getRGB(9, 27), BLACK, THRES) &&
                closeColor(r.getRGB(9, 34), BLACK, THRES) &&
                closeColor(r.getRGB(14, 18), BLACK, THRES) &&
                closeColor(r.getRGB(23, 34), BLACK, THRES) &&//
                closeColor(r.getRGB(20, 16), BLACK, THRES) &&
                closeColor(r.getRGB(22, 21), BLACK, THRES) &&
                closeColor(r.getRGB(22, 33), BLACK, THRES)) {
            return KeyEvent.VK_N;
        }
        //O
        if (closeColor(r.getRGB(13, 15), BLACK, THRES) &&
                closeColor(r.getRGB(17, 17), BLACK, THRES) &&
                closeColor(r.getRGB(20, 22), BLACK, THRES) &&
                closeColor(r.getRGB(19, 29), BLACK, THRES) &&
                closeColor(r.getRGB(19, 26), BLACK, THRES) &&
                closeColor(r.getRGB(20, 25), BLACK, THRES) &&
                closeColor(r.getRGB(16, 33), BLACK, THRES) &&
                closeColor(r.getRGB(11, 33), BLACK, THRES) &&
                closeColor(r.getRGB(7, 31), BLACK, THRES) &&
                closeColor(r.getRGB(5, 26), BLACK, THRES) &&
                closeColor(r.getRGB(5, 21), BLACK, THRES) &&
                closeColor(r.getRGB(6, 18), BLACK, THRES) &&
                closeColor(r.getRGB(9, 16), BLACK, THRES)) {
            return KeyEvent.VK_O;
        }
        
        //U
        if (closeColor(r.getRGB(6, 18), BLACK, THRES) &&
                closeColor(r.getRGB(6, 25), BLACK, THRES) &&
                closeColor(r.getRGB(11, 34), BLACK, THRES) &&
                closeColor(r.getRGB(18, 29), BLACK, THRES) &&
                closeColor(r.getRGB(19, 17), BLACK, THRES) &&
                closeColor(r.getRGB(22, 34), BLACK, THRES)) {
            return KeyEvent.VK_U;
        }
        

        
         //R
        if (closeColor(r.getRGB(7, 16), BLACK, THRES) &&
                closeColor(r.getRGB(12, 19), BLACK, THRES) &&
                closeColor(r.getRGB(11, 27), BLACK, THRES) &&
                closeColor(r.getRGB(16, 18), BLACK, THRES) &&
                closeColor(r.getRGB(9, 34), BLACK, THRES) &&
                closeColor(r.getRGB(5, 35), BLACK, THRES) &&
                closeColor(r.getRGB(16, 34), BLACK, THRES) &&
                closeColor(r.getRGB(18, 36), BLACK, THRES) &&
                closeColor(r.getRGB(19, 16), BLACK, THRES) &&
                closeColor(r.getRGB(13, 20), BLACK, THRES) &&
                closeColor(r.getRGB(24, 19), BLACK, THRES)) {
            return KeyEvent.VK_R;
        }

         //W
        if (closeColor(r.getRGB(7, 17), BLACK, THRES) &&
                closeColor(r.getRGB(7, 23), BLACK, THRES) &&
                closeColor(r.getRGB(11, 32), BLACK, THRES) &&
                closeColor(r.getRGB(13, 23), BLACK, THRES) &&
                closeColor(r.getRGB(16, 19), BLACK, THRES) &&
                closeColor(r.getRGB(18, 25), BLACK, THRES) &&
                closeColor(r.getRGB(21, 32), BLACK, THRES) &&
                closeColor(r.getRGB(23, 26), BLACK, THRES) &&
                closeColor(r.getRGB(25, 18), BLACK, THRES)) {
            return KeyEvent.VK_W;
        }
        
         //E
        if (closeColor(r.getRGB(19, 31), BLACK, THRES) &&
                closeColor(r.getRGB(14, 34), BLACK, THRES) &&
                closeColor(r.getRGB(14, 24), BLACK, THRES) &&
                closeColor(r.getRGB(6, 23), BLACK, THRES) &&
                closeColor(r.getRGB(6, 29), BLACK, THRES) &&
                closeColor(r.getRGB(5, 24), BLACK, THRES) &&
                closeColor(r.getRGB(6, 20), BLACK, THRES) &&
                closeColor(r.getRGB(13, 16), BLACK, THRES) &&
                closeColor(r.getRGB(20, 20), BLACK, THRES) &&
                closeColor(r.getRGB(18, 24), BLACK, THRES) &&
                closeColor(r.getRGB(13, 24), BLACK, THRES)) {
            return KeyEvent.VK_E;
        }
        
         //V
        if (closeColor(r.getRGB(7, 17), BLACK, THRES) &&
                closeColor(r.getRGB(10, 24), BLACK, THRES) &&
                closeColor(r.getRGB(14, 31), BLACK, THRES) &&
                closeColor(r.getRGB(18, 25), BLACK, THRES) &&
                closeColor(r.getRGB(20, 17), BLACK, THRES)) {
            return KeyEvent.VK_V;
        }
        
        //C
        if (closeColor(r.getRGB(19, 21), BLACK, THRES) &&
                closeColor(r.getRGB(14, 17), BLACK, THRES) &&
                closeColor(r.getRGB(5, 22), BLACK, THRES) &&
                closeColor(r.getRGB(13, 16), BLACK, THRES) &&
                closeColor(r.getRGB(6, 31), BLACK, THRES) &&
                closeColor(r.getRGB(13, 34), BLACK, THRES) &&
                closeColor(r.getRGB(19, 32), BLACK, THRES)) {
            return KeyEvent.VK_C;
        }
        return KeyEvent.VK_SPACE;
    }
    static void type(Robot r, int[] w) {
        //r.keyPress(KeyEvent.VK_C); //DEBUGGING
        for (int keycode : w) {
            //System.out.print((char) keycode);
            r.delay(20);
            if (keycode != 32) {
                r.delay(18);
                r.waitForIdle();
                r.keyPress(keycode);
                r.delay(18);
                r.keyRelease(keycode);
            }
        }
        System.out.println();
    }
    /* Dimension and coordinates of 10 character box to be imaged. */
    static final int HEIGHT = 58;
    static final int WIDTH = 300;
    static int X = 533;
    static int Y = 400;//400
    /* Dimension and coordinates of 10 character box to be imaged. */
    static final int CHARHEIGHT = 58;
    static final int CHARWIDTH = 30;
}