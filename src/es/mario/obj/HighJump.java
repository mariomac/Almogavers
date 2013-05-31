/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.mario.obj;

import es.mario.Game;
import es.mario.graphics.TileMap;
import es.mario.obj.platform.CollisionDetector;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Mario
 */
public class HighJump implements GameObject {

    private Box box;
    private Game theGame;
    private int tileX, tileY;

    private boolean finished = false;

    public HighJump(Game game, int mapTileX, int mapTileY) {
        box = new Box();
        tileX = mapTileX;
        tileY = mapTileY;
        this.theGame = game;
        box.centerX = mapTileX * TileMap.TILE_WIDTH + TileMap.TILE_WIDTH / 2;
        box.centerY = mapTileY * TileMap.TILE_HEIGHT + TileMap.TILE_HEIGHT / 2;
        box.radiusX = TileMap.TILE_WIDTH / 2;
        box.radiusY = TileMap.TILE_HEIGHT / 2;
    }

    public Box getBox() {
        return box;
    }

    public void onCollision(GameObject collidingObject) {
        theGame.player.setJumpAccel(Player.JUMP_ACCEL_2);
        finished = true;
        theGame.tileMap.tiles[tileX][tileY] = TileMap.IDX_VOID;
    }

    public boolean move() {
        if(CollisionDetector.isColliding(this, theGame.player)) {
            onCollision(theGame.player);
        }
        return finished;
    }

    public void paint(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(tileX*TileMap.TILE_WIDTH,tileY*TileMap.TILE_HEIGHT,TileMap.TILE_WIDTH,TileMap.TILE_HEIGHT);

 
    }

}
