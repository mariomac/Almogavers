/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.mario.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author mmacias
 */
public class BitmapFont {

    private static final byte START_CHAR = ' ';
    private static final byte END_CHAR = 'Z';
    private static final int SEPARATOR_COLOR =  -65281;
    private static final int CHAR_SEPARATION = 1;
    private static final int LINE_SEPARATION = 3;

    private int charHeight;

    private BufferedImage[] chars = new BufferedImage[END_CHAR - START_CHAR + 1];
    public BitmapFont() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();

            BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/font.png"));
            byte currentChar = 0;
            int currentPixel = 0;
            while(currentPixel < img.getWidth() && currentChar < chars.length) {
                int charLength = 0;
                while(currentPixel < img.getWidth() && img.getRGB(currentPixel + charLength, 0) != SEPARATOR_COLOR) {
                    charLength++;
                }
                chars[currentChar] = gc.createCompatibleImage(charLength - 1, img.getHeight(), Transparency.TRANSLUCENT);
                Graphics ig = chars[currentChar].getGraphics();
                ig.drawImage(img, 0,0, charLength, img.getHeight() -1 ,
                        currentPixel,0, currentPixel + charLength, img.getHeight() - 1,null);

                currentPixel += charLength + 1;
                currentChar++;
                
            }
            charHeight = chars[currentChar - 1].getHeight();
            img.flush();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void drawText(Graphics g, byte[] text, int x, int y, int width) {
        int currentChar = 0;
        int cx = x, cy = y;
        while(currentChar < text.length) {
            int ci = text[currentChar] - START_CHAR;
            if(ci >= 0 && ci <= chars.length) {
                int charWidth = chars[ci].getWidth();
                if(cx + charWidth - CHAR_SEPARATION > width) {
                    cx = x;
                    cy += charHeight + LINE_SEPARATION;
                }
                g.drawImage(chars[ci], cx, cy, null);
                cx += charWidth + CHAR_SEPARATION;
            } else if(ci + START_CHAR == '\n') {
                cx = x;
                cy += charHeight + LINE_SEPARATION;
            }
            currentChar++;
        }
    }

    public void paint(Graphics g) {
        for(int x = 0 ; x < 16 ; x++) {
            for(int y = 0 ; y < 16 ; y++) {
                int idx = y * 16 + x;
                if(idx < chars.length) {
                    g.drawImage(chars[idx], x * 16 + 50, y * 16 + 50, null);
                }
            }
        }
    }



}
