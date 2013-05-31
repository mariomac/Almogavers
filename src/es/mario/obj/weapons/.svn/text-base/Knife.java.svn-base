/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.mario.obj.weapons;

import es.mario.AlmogaversScreen;
import es.mario.Game;
import es.mario.graphics.TileMap;
import es.mario.obj.Box;
import es.mario.obj.GameObject;
import es.mario.obj.Player;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Mario
 */
public class Knife extends Weapon {
    private static int WIDTH = TileMap.TILE_WIDTH;
    private static int HEIGHT = TileMap.TILE_HEIGHT / 5;
    private static int Y_POS = (Player.HEIGHT - HEIGHT) / 2;
    private static int DURATION_FRAMES = AlmogaversScreen.FRAMES_SECOND / 4;

    private Box box = new Box();

    private int framesToHide;

    public Knife(Game game) {
        super(game);
        box.radiusX = WIDTH / 2;
        box.radiusY = HEIGHT / 2;

    }



    @Override
    public void paint(Graphics g) {
        int xo;
        if(theGame.player.facingLeft) {
            xo = -WIDTH;
        } else {
            xo = Player.WIDTH;
        }
        g.setColor(Color.white);
        g.fillRect(theGame.player.x + xo, theGame.player.y + Y_POS, WIDTH, HEIGHT);
    }

    @Override
    public int getDuration() {
        return DURATION_FRAMES;
    }

    @Override
    public boolean move() {
        super.move();
        int xo;
        if(theGame.player.facingLeft) {
            xo = -WIDTH/2;
        } else {
            xo = Player.WIDTH + WIDTH / 2;
        }

        box.centerX = theGame.player.x + xo;
        box.centerY = theGame.player.y + Y_POS + HEIGHT / 2;
        return false;
    }


    @Override
    public int getStrength() {
        return STRENGTH;
    }

    public static final int STRENGTH = 1;

    public Box getBox() {
        return box;
    }

    public void onCollision(GameObject collidingObject) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
