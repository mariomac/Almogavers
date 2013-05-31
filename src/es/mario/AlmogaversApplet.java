package es.mario;


import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.io.IOException;

/**
 *
 */
public class AlmogaversApplet extends JFrame implements Runnable {
    private AlmogaversScreen theScreen = null;
    public static boolean running = true;

    public AlmogaversApplet() {
        super();
        setSize(640,480);
        theScreen = new AlmogaversScreen();
        theScreen.setVisible(true);
        this.add(theScreen);
        addKeyListener(theScreen);
        addFocusListener(theScreen);
        setFocusable(true);
    }

    public static void main(String[] args) {
        AlmogaversApplet window = new AlmogaversApplet();
        window.setVisible(true);
        window.setTitle("Almogavers");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.run();
//        Thread th = new Thread(window);
//        th.start();
        System.exit(0);
    }

    public void run() {
        while(running) {
            long previousTime = System.currentTimeMillis();
            theScreen.tick();
            theScreen.repaint();
            long currentTime = System.currentTimeMillis();
            try {
                long toSleep = (1000 / AlmogaversScreen.FRAMES_SECOND) + previousTime - currentTime;
                if(toSleep > 0) {
                    Thread.sleep(toSleep);
                } else {
                    Thread.yield();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setVisible(false);
    }

    public static Image loadImage(String path) {
        InputStream is = AlmogaversApplet.class.getResourceAsStream(path);
        byte[] ib = null;
        try {
            ib = new byte[is.available()];
            is.read(ib);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Toolkit.getDefaultToolkit().createImage(ib);        
    }
}
