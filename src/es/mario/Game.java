/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.mario;

import es.mario.graphics.TileMap;
import es.mario.obj.GameObject;
import es.mario.obj.Player;
import es.mario.obj.platform.CollisionDetector;
import es.mario.obj.weapons.Knife;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Mario
 */
public class Game {
    public static final int CAMERA_MARGIN_H = 6*TileMap.TILE_WIDTH;
    public static final int CAMERA_MARGIN_V = 3*TileMap.TILE_HEIGHT;

    private static final int BTN_SHOOT = KeyEvent.VK_X;

    private List<GameObject> gameObjects = new LinkedList<GameObject>();

    public Player player;

    public TileMap tileMap;
    public int camX,camY;

    public Game(AlmogaversScreen screen) {
        player = new Player(this);

        // TODO: borrame
        player.weapon = new Knife(this);

        tileMap = new TileMap(this,screen);
    }

    private List<GameObject> objectsToRemove = new LinkedList<GameObject>();

    private void requestForObjectRemoval(GameObject object){
        objectsToRemove.add(object);
    }

    private List<GameObject> objectsToAdd = new LinkedList<GameObject>();
    public void requestForAddition(GameObject object) {
        objectsToAdd.add(object);
    }
    public void addImmediately(GameObject object) {
        gameObjects.add(object);
    }

    public void paint(Graphics g) {
        g.translate(-camX, -camY);
        tileMap.paint(g);
        try {
            for(GameObject object : gameObjects) {
                object.paint(g);
            }
        } catch (ConcurrentModificationException e) {
            System.err.println("Concurrent Modification on AlmogaversScreen.paint()");
        }
        player.paint(g);
        g.translate(camX,camY);
    }

    public void tick() {
        centerCamera();
        for(GameObject object : gameObjects) {
            if(object.move()) {
                requestForObjectRemoval(object);
            }
        }

        player.move();

        if(objectsToRemove.size() > 0) {
            gameObjects.removeAll(objectsToRemove);
            objectsToRemove.clear();
        }
        if(objectsToAdd.size() > 0) {
            gameObjects.addAll(objectsToAdd);
            objectsToAdd.clear();
        }

    }

    public void centerCamera() {
        if(player.x < (camX + CAMERA_MARGIN_H)) {
            camX = (camX + (player.x - CAMERA_MARGIN_H)) / 2;
            if(camX < 0) {
                camX = 0;
            }
        } else if( (player.x + Player.WIDTH) > (camX + AlmogaversScreen.SCR_WIDTH - CAMERA_MARGIN_H)) {
            camX = (camX + (player.x + Player.WIDTH + CAMERA_MARGIN_H - AlmogaversScreen.SCR_WIDTH)) / 2;
            if(camX > ((tileMap.mapWidth * TileMap.TILE_WIDTH) - AlmogaversScreen.SCR_WIDTH)) {
                camX = (tileMap.mapWidth * TileMap.TILE_WIDTH) -AlmogaversScreen. SCR_WIDTH;
            }
        }
        if(player.y < (camY + CAMERA_MARGIN_V)) {
            camY = (camY + (player.y - CAMERA_MARGIN_V)) / 2;
            if(camY < 0) {
                camY = 0;
            }
        } else if( (player.y + Player.HEIGHT) > (camY + AlmogaversScreen.SCR_HEIGHT - CAMERA_MARGIN_V)) {
            camY = (camY + (player.y + Player.HEIGHT + CAMERA_MARGIN_V - AlmogaversScreen.SCR_HEIGHT)) / 2;
            if(camY > ((tileMap.mapHeight * TileMap.TILE_HEIGHT) - AlmogaversScreen.SCR_HEIGHT)) {
                camY = (tileMap.mapHeight * TileMap.TILE_HEIGHT) - AlmogaversScreen.SCR_HEIGHT;
            }
        }
    }

    public void keyPressed(KeyEvent event) {
        switch(event.getKeyCode()) {
            case KeyEvent.VK_UP:
                player.setGoingUp(true);
                break;
            case KeyEvent.VK_RIGHT:
                player.setGoingRight(true);
                break;
            case KeyEvent.VK_LEFT:
                player.setGoingLeft(true);
                break;
            case KeyEvent.VK_DOWN:
                player.setGoingDown(true);
                break;
            case KeyEvent.VK_ESCAPE:
                AlmogaversApplet.running = false;
                break;
            case BTN_SHOOT:
                player.shoot();
                break;
        }
    }
    public void keyReleased(KeyEvent event) {
        switch(event.getKeyCode()) {
            case KeyEvent.VK_UP:
                player.setGoingUp(false);
                break;
            case KeyEvent.VK_RIGHT:
                player.setGoingRight(false);
                break;
            case KeyEvent.VK_LEFT:
                player.setGoingLeft(false);
                break;
            case KeyEvent.VK_DOWN:
                player.setGoingDown(false);
                break;
            case BTN_SHOOT:
                player.canShoot = true;
                break;
        }
    }

    public void stopActions() {
        player.setGoingUp(false);
        player.setGoingLeft(false);
        player.setGoingRight(false);
        player.setGoingDown(false);
    }
}
