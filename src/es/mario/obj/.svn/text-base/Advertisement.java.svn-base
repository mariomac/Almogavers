/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.mario.obj;

import es.mario.AlmogaversScreen;
import es.mario.AlmogaversScreen.ScreenMessage;
import es.mario.Game;
import es.mario.graphics.TileMap;
import es.mario.obj.platform.CollisionDetector;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Mario
 */
public class Advertisement implements GameObject {

    public static final int WIDTH = TileMap.TILE_WIDTH;
    public static final int HEIGHT = TileMap.TILE_HEIGHT;

    private  AlmogaversScreen screen;
    private Box box;
    private int id;
    private int px, py;
    public Advertisement(int x, int y, int id, AlmogaversScreen screen) {
        px = x; py = y;
        box = new Box();
        box.centerX = x+WIDTH/2;
        box.centerY = y+HEIGHT/2;
        box.radiusX = WIDTH/2;
        box.radiusY = HEIGHT/2;
        this.id = id;
        this.screen = screen;
    }

    public Box getBox() {
       return box;
    }

    @Override
    public void onCollision(GameObject collidingObject) {
        AlmogaversScreen.ScreenMessage msg = null;
        switch(id) {
            case TileMap.IDX_AD_1:
                msg = AD1;
                break;
            case TileMap.IDX_AD_2:
                msg = AD2;
                break;
            case TileMap.IDX_AD_3:
                msg = AD3;
                break;
            case TileMap.IDX_AD_4:
                msg = AD4;
                break;
            case TileMap.IDX_AD_5:
                msg = AD5;
                break;
            case TileMap.IDX_AD_6:
                msg = AD6;
                break;
        }
        screen.drawMessage(msg);

    }

    private static final int AD_X = 100;
    private static final int AD_Y = 100;
    private static final int AD_WIDTH = AlmogaversScreen.SCR_WIDTH - AD_X;
    private static final AlmogaversScreen.ScreenMessage AD1 = 
                    new AlmogaversScreen.ScreenMessage(("       = Bienvenido al castillo =\nPulsa las teclas < y > para moverte hacia la\n" +
                    "izquierda y la derecha, respectivamente.\nCarteles como este contienen\ninformacion util.").toUpperCase(),AD_X,AD_Y,AD_WIDTH);
    private static final AlmogaversScreen.ScreenMessage AD2 = new AlmogaversScreen.ScreenMessage(("       = Sorteando obstaculos =\nPulsa la tecla $ para saltar.\nSi pulsas $ y > o $ y < a la vez,\n" +
            "saltaras en diagonal a la derecha %\no a la izquierda ', respectivamente.").toUpperCase(),AD_X,AD_Y,AD_WIDTH);
    private static final AlmogaversScreen.ScreenMessage AD3 =
                    new AlmogaversScreen.ScreenMessage(("       = Escaleras =\nAl lado tienes una escalera.\n" +
                    "Cuando estes delante de una,\npulsa $ o abajo para subir\no bajar por ella respectivamente.").toUpperCase(),AD_X,AD_Y,AD_WIDTH);
    private static final AlmogaversScreen.ScreenMessage AD4 =
                    new AlmogaversScreen.ScreenMessage(("       = Plataformas moviles =\nCuando un abismo sea\ndemasiado grande,\n" +
                    "puedes subirte a una de estas\nplataformas moviles para\nllegar al otro lado.").toUpperCase(),AD_X,AD_Y,AD_WIDTH);

    private static final AlmogaversScreen.ScreenMessage AD5 =
                    new AlmogaversScreen.ScreenMessage(("       = Grial de la rana =\nbebe del siguiente recipiente\nhechizado por un rey\n" +
                    "batracio. obtendras sus propiedades\nsaltimbanquis").toUpperCase(),AD_X,AD_Y,AD_WIDTH);

    private static final AlmogaversScreen.ScreenMessage AD6 =
                    new AlmogaversScreen.ScreenMessage(("       = Usa tus armas! =\nEsos monstruos no te dejaran pasar bajo ningun concepto" +
                    " a menos que loso mates con tu espada. pulsa 'x' y dales una leccion").toUpperCase(),AD_X,AD_Y,AD_WIDTH);

    public boolean move() {
        if(CollisionDetector.isColliding(this, screen.theGame.player)) {
            onCollision(this);
        }
        return false;
    }

    public void paint(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(px + TileMap.TILE_WIDTH / 3, py , TileMap.TILE_WIDTH / 3,TileMap.TILE_HEIGHT);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(px, py ,TileMap.TILE_WIDTH,TileMap.TILE_HEIGHT/2);

    }

}
