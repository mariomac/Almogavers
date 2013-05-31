package es.mario;

import es.mario.graphics.BitmapFont;
import es.mario.graphics.TileMap;
import es.mario.obj.Player;
import es.mario.obj.GameObject;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 *
 */
public class AlmogaversScreen extends JPanel implements KeyListener, FocusListener {
    public static final int SCR_WIDTH = 640;
    public static final int SCR_HEIGHT = 480;



    private BitmapFont font;
    private BufferedImage buffer;
    public Game theGame = null;
    public int linkPosition = -1;
    private ScreenMessage[] messageArray = new ScreenMessage[32];
    private int messages;

    public AlmogaversScreen() {
        theGame = new Game(this);
        buffer = new BufferedImage(SCR_WIDTH, SCR_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        font = new BitmapFont();
    }

    @Override
    public void paint(Graphics screenGraphics) {
        Graphics g = buffer.getGraphics();
        g.setClip(0,0,SCR_WIDTH,SCR_HEIGHT);
        theGame.paint(g);
        for(int i = 0 ; i < messages ; i++) {
            font.drawText(g, messageArray[i].text, messageArray[i].x,messageArray[i].y,messageArray[i].width);
        }
        g.translate(0,0);

        //screenGraphics.drawImage(buffer, 0, 0, getWidth(), getHeight(), 0,0, SCR_WIDTH,SCR_HEIGHT,null);
        screenGraphics.drawImage(buffer, 0, 0, null);
    }

    public void tick() {
        messages = 0;
        theGame.tick();

    }


    public void keyTyped(KeyEvent event) {
    }

    public void keyPressed(KeyEvent event) {
        theGame.keyPressed(event);
    }

    

    public void keyReleased(KeyEvent event) {
        theGame.keyReleased(event);
    }

    public void focusGained(FocusEvent event) {
    }

    public void focusLost(FocusEvent event) {
        theGame.stopActions();
    }

    public void drawMessage(ScreenMessage message) {
        messageArray[messages++] = message;
    }

    public static class ScreenMessage {
        int x,y,width;
        byte[] text;

        public ScreenMessage(String text, int x, int y, int width) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.text = text.getBytes();
        }


    }

    public static final int FRAMES_SECOND = 30;
}
