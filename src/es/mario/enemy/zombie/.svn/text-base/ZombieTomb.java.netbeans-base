package es.mario.enemy.zombie;

import es.mario.graphics.TileMap;
import es.mario.obj.Box;
import es.mario.obj.GameObject;
import es.mario.AlmogaversScreen;
import es.mario.AlmogaversApplet;
import es.mario.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;

/**
 *
 */
public class ZombieTomb implements GameObject {

    public static Image image = null;
    //returns true if the object must be removed from its containing list
    private int ticks = 0;
    private int mapX,mapY;
    private Game theGame;

    public ZombieTomb(int mapX, int mapY,Game theGame) {
        this.mapX = mapX;
        this.mapY = mapY;
        this.theGame = theGame;
        if(image == null) {
            image = AlmogaversApplet.loadImage("/enemies/zombie_tomb.png");
        }
    }

    public boolean move() {
        if( (ticks % ZOMBIE_INTERVAL_AND) == 0 ) {
            theGame.requestForAddition(new Zombie(mapX * TileMap.TILE_WIDTH, mapY * TileMap.TILE_HEIGHT,theGame.tileMap));
        }
        ticks++;
        return false;
    }

    public static final int ZOMBIE_INTERVAL_AND = AlmogaversScreen.FRAMES_SECOND * 2;

    public static final int WIDTH = TileMap.TILE_WIDTH;
    public static final int HEIGHT = TileMap.TILE_HEIGHT;

    public Box getBox() {
        return null;
    }

    public void onCollision(GameObject collidingObject) {
        
    }

    public void paint(Graphics g) {
        
    }

}
