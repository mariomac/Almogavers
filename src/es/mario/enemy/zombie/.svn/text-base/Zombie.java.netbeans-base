package es.mario.enemy.zombie;

import es.mario.obj.Box;
import es.mario.obj.Player;
import es.mario.graphics.TileMap;
import es.mario.AlmogaversScreen;
import es.mario.AlmogaversApplet;

import java.awt.*;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.InputStream;
import java.io.IOException;

import com.sun.media.sound.*;
import es.mario.obj.GameObject;

/**
 *
 */
public class Zombie implements GameObject {
    private int x,y;
    private int status;
    private int ticks;

    private TileMap tileMap;

    private static Image image;
    private int imageWidth;
    private int imageHeight;

    public Zombie(int x, int y, TileMap tileMap) {
        this.x = x;
        this.y = y + HEIGHT;
        status = STATUS_APPEARING;
        ticks = 0;
        this.tileMap = tileMap;
        if(image == null) {
            image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
            image.getGraphics().setColor(Color.BLUE);
            image.getGraphics().fillRect(0,0,WIDTH,HEIGHT);
                    //AlmogaversApplet.loadImage(IMAGE_PATH);
        }
    }

    public void paint(Graphics g) {
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
        Graphics2D g2 = (Graphics2D) g;
        switch(status) {
            case STATUS_APPEARING:
                int off = ticks * APPEAR_SPEED;
                g2.drawImage(image, x, y, x + imageWidth, y + off, 0,0, imageWidth, off, null);
                break;
            case STATUS_LEFT:
                g.drawImage(image,x,y,null);
                break;
            case STATUS_RIGHT:
                g2.drawImage(image, x, y, x + imageWidth, y + imageHeight, imageWidth,0, 0, imageHeight, null);
                break;
        }
    }

    //returns true if the object must be removed from its containing list
    public boolean move() {
        switch(status) {
            case STATUS_APPEARING:
                if(ticks == (HEIGHT / APPEAR_SPEED)) {
                    status = STATUS_LEFT;
                } else {
                    y -= APPEAR_SPEED;
                }
                break;
            case STATUS_LEFT:
                x -= MOVE_SPEED;
                if(!tileMap.canTraverse(x / TileMap.TILE_WIDTH, (y + HEIGHT / 2) / TileMap.TILE_HEIGHT, false)) {
                    status = STATUS_RIGHT;
                }
                break;
            case STATUS_RIGHT:
                x += MOVE_SPEED;
                if(!tileMap.canTraverse((x + WIDTH) / TileMap.TILE_WIDTH, (y + HEIGHT / 2) / TileMap.TILE_HEIGHT, false)) {
                    status = STATUS_LEFT;
                }
                break;
        }

        ticks++;
        return false;
    }

    public Box getBox() {
        return null;
    }

    public static final int WIDTH   = TileMap.TILE_WIDTH;
    public static final int HEIGHT  = 3 * TileMap.TILE_HEIGHT / 2;

    public static final int STATUS_APPEARING    = 0;
    public static final int STATUS_LEFT         = 1;
    public static final int STATUS_RIGHT        = 2;

    public static final int APPEAR_SPEED = 2;
    public static final int MOVE_SPEED = Player.MOVE_SPEED / 2;

    public static final String IMAGE_PATH = "/enemies/almo_skeleton.png";

    public void onCollision(GameObject collidingObject) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
