/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.mario.obj.weapons;

import es.mario.Game;
import es.mario.obj.Box;
import es.mario.obj.GameObject;
import java.awt.Graphics;

/**
 *
 * @author Mario
 */
public abstract class Weapon implements GameObject {
    protected int framesToHide;
    protected Box box;
    protected Game theGame;
    protected  Weapon(Game game) {
        theGame = game;
    }
    protected static int shootNumber = 0;

    public abstract void paint(Graphics g);
    public abstract int getDuration();

    public int getShootNumber() {
        return shootNumber;
    }

    @Override
    public boolean move() {
        if(isBeingUsed()) {
            framesToHide--;
        }
        return false;
    }

    public abstract int getStrength();

    public void shoot() {
        framesToHide = getDuration();
        shootNumber++;
    }
    
    public boolean isBeingUsed() {
        return framesToHide > 0;
    }

}
