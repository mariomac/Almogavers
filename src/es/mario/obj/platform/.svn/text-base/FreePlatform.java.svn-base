package es.mario.obj.platform;

import es.mario.obj.Box;
import es.mario.obj.Player;
import es.mario.graphics.TileMap;
import es.mario.obj.GameObject;

import java.awt.*;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Comparator;

/**
 *
 */
public class FreePlatform implements GameObject {
    private int x,y;
    private int direction;
    private TileMap tileMap = null;
    private Box box = new Box();

    int linkPosition = -1;

    public FreePlatform(int x, int y, int direction, TileMap tileMap) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.tileMap = tileMap;
        box.radiusX = TileMap.TILE_WIDTH / 2;
        box.radiusY = TileMap.TILE_HEIGHT / 2;
        box.centerX = x + TileMap.TILE_WIDTH / 2;
        box.centerY = y + TileMap.TILE_HEIGHT / 2;
    }

    public void paint(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRoundRect(x,y, TileMap.TILE_WIDTH, TileMap.TILE_HEIGHT,5,5);
    }

    public boolean move() {
        boolean collides = CollisionDetector.isColliding(Player.instance,this);

        if(collides) {
            //calculates where is the player (up, down, left, right) to link here the platform
            Box pb = Player.instance.getBox();
            TreeMap<Integer,Integer> dist = new TreeMap<Integer,Integer>();
            dist.put(Math.abs((box.centerX + box.radiusX) - (pb.centerX - pb.radiusX)),LEFT);
            dist.put(Math.abs((box.centerY + box.radiusY) - (pb.centerY - pb.radiusY)),UP);
            dist.put(Math.abs((box.centerX - box.radiusX) - (pb.centerX + pb.radiusX)),RIGHT);
            dist.put(Math.abs((box.centerY - box.radiusY) - (pb.centerY + pb.radiusY)),DOWN);

            int linkedSide = dist.firstEntry().getValue();
            Player.instance.linkPlatform(this,linkedSide);
            if(linkPosition != linkedSide) {
                Player.instance.unlinkPlatform(linkPosition);
            }
            linkPosition = linkedSide;
        } else {
            if(linkPosition != -1) {
                Player.instance.unlinkPlatform(linkPosition);
                linkPosition = -1;                
            }
        }

        //if collides with player, moves it at its same speed
        int incX = 0, incY = 0;
        switch(direction) {
            case UP:
                incY -= MOVE_SPEED;
                break;
            case RIGHT:
                incX += MOVE_SPEED;
                break;
            case DOWN:
                incY += MOVE_SPEED;
                break;
            case LEFT:
                incX -= MOVE_SPEED;
                break;
        }
        x += incX;
        y += incY;
        box.centerX += incX;
        box.centerY += incY;
        
        if( ((x % TileMap.TILE_WIDTH) < MOVE_SPEED) && (y % TileMap.TILE_HEIGHT) < MOVE_SPEED) {
            switch(tileMap.tiles[x / TileMap.TILE_WIDTH][y / TileMap.TILE_HEIGHT]) {
                case TileMap.IDX_FREE_PLATFORM_UP:
                    direction = UP;
                    break;
                case TileMap.IDX_FREE_PLATFORM_DOWN:
                    direction = DOWN;
                    break;
                case TileMap.IDX_FREE_PLATFORM_LEFT:
                    direction = LEFT;
                    break;
                case TileMap.IDX_FREE_PLATFORM_RIGHT:
                    direction = RIGHT;
                    break;
            }
        }

        return false;
    }


    public int getDirection() {
        return direction;
    }

    public Box getBox() {
        return box;
    }

    public static final int MOVE_SPEED = 2;
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;

    public void onCollision(GameObject collidingObject) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
