/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.mario.enemy.zombie;

import es.mario.AlmogaversApplet;
import es.mario.Game;
import es.mario.graphics.TileMap;
import es.mario.obj.Box;
import es.mario.obj.GameObject;
import es.mario.obj.platform.CollisionDetector;
import es.mario.obj.weapons.Knife;
import es.mario.obj.weapons.Weapon;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author Mario
 */
public class BarrierZombie implements GameObject  {
    private int tileX, tileY;
    private int imgXpos, imgYpos;
    private Box box;
    private static Image image = null;


    private static int imageWidth, imageHeight;
    private Game theGame;
    private int lastShoot = -1;
    private int energy = MAX_ENERGY;
    private boolean dead = false;

    public BarrierZombie(TileMap tileMap, Game theGame, int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
        if(image == null) {
            image = AlmogaversApplet.loadImage(IMAGE_PATH);
            do {
                imageWidth = image.getWidth(null);
            } while(imageWidth < 0);
            imageHeight = image.getHeight(null);
        }

        imgXpos = tileX * TileMap.TILE_WIDTH - Math.abs(imageWidth-TileMap.TILE_WIDTH) / 2;
        imgYpos = (tileY - 1) * TileMap.TILE_HEIGHT;// + HEIGHT / 2 - imageHeight / 2;
        box = new Box(
                tileX * TileMap.TILE_WIDTH + TileMap.TILE_WIDTH/2,
                tileY * TileMap.TILE_WIDTH,
                WIDTH / 2, HEIGHT / 2);

        tileMap.tiles[tileX][tileY] = TileMap.IDX_INVISIBLE_WALL;
        tileMap.tiles[tileX][tileY-1] = TileMap.IDX_INVISIBLE_WALL;

        this.theGame = theGame;
    }

    public boolean move() {
        if(CollisionDetector.isColliding(this, theGame.player.weapon)) {
            onCollision(theGame.player.weapon);
        }
        return dead;
    }

    public Box getBox() {
        return box;
    }

    public void onCollision(GameObject collidingObject) {
        if(collidingObject instanceof Weapon) {
            Weapon w = (Weapon) collidingObject;
            if(w.getShootNumber() != lastShoot) {
                lastShoot = w.getShootNumber();
                energy -= w.getStrength();
                if(energy <= 0) {
                    theGame.tileMap.tiles[tileX][tileY] = TileMap.IDX_VOID;
                    theGame.tileMap.tiles[tileX][tileY - 1] = TileMap.IDX_VOID;
                    dead = true;
                }
            }
        }
    }

    public void paint(Graphics g) {
        if(theGame.player.x > imgXpos + WIDTH / 2) {
            g.drawImage(image, imgXpos,
                    imgYpos, imgXpos + imageWidth-1, imgYpos + imageHeight-1,
                    imageWidth-1,0,0,imageHeight-1,null);
        } else {
            g.drawImage(image, imgXpos, imgYpos, null);
        }
    }

    public static final String IMAGE_PATH = "/enemies/barrierZombie.png";
    public static final int WIDTH = TileMap.TILE_WIDTH;
    public static final int HEIGHT = TileMap.TILE_WIDTH * 2;

    public static final int MAX_ENERGY = Knife.STRENGTH * 3;

}
