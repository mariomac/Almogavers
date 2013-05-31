package es.mario.graphics;

import es.mario.AlmogaversScreen;
import es.mario.AlmogaversApplet;
import es.mario.Game;
import es.mario.enemy.zombie.BarrierZombie;
import es.mario.obj.Advertisement;
import es.mario.obj.HighJump;
import es.mario.obj.Player;
import es.mario.util.Base64;
import es.mario.obj.platform.FreePlatform;
import es.mario.enemy.zombie.ZombieTomb;

import java.awt.*;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 *
 */
public class TileMap {
    public int mapWidth,mapHeight;
    public short tiles[][];
    private int maxCamRight;
    private int maxCamDown;

    private Image imgWalls;
    private Image imgStairs;

    private Game theGame = null;
private AlmogaversScreen theScreen ;
    public TileMap(Game theGAme, AlmogaversScreen theScreen) {
        theGame = theGAme;
        this.theScreen = theScreen;
        createMap("walls.tmx");
    }

    private void createMap(String mapFile) {
        imgStairs = AlmogaversApplet.loadImage("/scenario/stairs.png");
        imgWalls = AlmogaversApplet.loadImage("/scenario/walls.jpg");
        Document doc = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(AlmogaversApplet.class.getResourceAsStream("/walls.tmx"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Node map = doc.getElementsByTagName("map").item(0);
        mapWidth = Integer.decode(map.getAttributes().getNamedItem("width").getNodeValue());
        mapHeight = Integer.decode(map.getAttributes().getNamedItem("height").getNodeValue());
//        TILE_WIDTH = Integer.decode(map.getAttributes().getNamedItem("tilewidth").getNodeValue());
//        TILE_HEIGHT = Integer.decode(map.getAttributes().getNamedItem("tileheight").getNodeValue());

        tiles = new short[mapWidth][mapHeight];

        String mapContent = doc.getElementsByTagName("data").item(0).getChildNodes().item(0).getNodeValue();

        try {
            byte[] base64data = Base64.decode(mapContent.trim());
//            base64.decodeBuffer(mapContent.trim());
//            GZIPInputStream zip = new GZIPInputStream(new ByteArrayInputStream(base64data));
            InputStream zip = new ByteArrayInputStream(base64data);

            byte[] integer = new byte[4];
            for(int y = 0 ; y < mapHeight; y++) {
                for(int x = 0 ; x < mapWidth; x++) {
                    zip.read(integer);
                    short tile = (short) ((integer[0] & 0xFF) - 1);

                    switch(tile) {
                        case IDX_ZOMBIE_TOMB:
                            theGame.addImmediately(new ZombieTomb(x,y - 1,theGame));
                            tiles[x][y] = IDX_ZOMBIE_TOMB;
                            break;
                        case IDX_PLAYER:
                            tiles[x][y] = IDX_VOID;
                            theGame.player.x = x * TILE_WIDTH;
                            theGame.player.y = y * TILE_HEIGHT;
                            break;
                        case IDX_FREE_PLATFORM:
                            //By default, free platforms go to left, except if the left tile is a RIGHT direction modifier
                            int direction = tiles[x-1][y] == IDX_FREE_PLATFORM_RIGHT ? FreePlatform.RIGHT : FreePlatform.LEFT;
                            theGame.addImmediately(new FreePlatform(x * TILE_WIDTH,y * TILE_HEIGHT,direction,this));
                            tiles[x][y] = IDX_VOID;
                            break;
                        case IDX_AD_1:
                        case IDX_AD_2:
                        case IDX_AD_3:
                        case IDX_AD_4:
                        case IDX_AD_5:
                        case IDX_AD_6:
                            theGame.addImmediately(new Advertisement(x * TILE_WIDTH, y * TILE_HEIGHT, tile,theScreen));
                            tiles[x][y] = tile;
                            break;
                        case IDX_ITEM_DOUBLE_JUMP:
                            if(theGame.player.getJumpAccel() == Player.JUMP_ACCEL_1) {
                                tiles[x][y] = tile;
                                theGame.addImmediately(new HighJump(theGame, x,y));
                            }
                            break;
                        case IDX_BARRIER_ZOMBIE:
                            theGame.addImmediately(new BarrierZombie(this, theGame, x, y));
                            break;
                        default:
                            tiles[x][y] = tile;
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        maxCamRight = (mapWidth * TILE_WIDTH) - AlmogaversScreen.SCR_WIDTH;
        maxCamDown = (mapHeight * TILE_HEIGHT) - AlmogaversScreen.SCR_HEIGHT;
//        if(windowWidth <= (mapWidth * TILE_WIDTH)) {
//        } else {
//            maxCamRight = (mapWidth * tileSize);
//        }
//        if(windowHeight <= (mapHeight * tileSize)) {
//        } else {
//            maxCamDown = (mapHeight * tileSize);
//        }
        theGame.camX = theGame.camY = 0;
    }

    private Color eraseme = new Color(0x440b00);
    public void paint(Graphics g) {
        final int camX = theGame.camX;
        final int camY = theGame.camY;

        g.setColor(eraseme);
        g.fillRect(camX,camY,AlmogaversScreen.SCR_WIDTH,AlmogaversScreen.SCR_HEIGHT);

        int initTileX,endTileX;
        if(camX > maxCamRight) {
            initTileX = maxCamRight / TILE_WIDTH;
            endTileX = mapWidth - 1;
        } else {
            if (camX < 0) {
                initTileX = 0;
            } else {
                initTileX = camX / TILE_WIDTH;
            }
            endTileX = 1 + initTileX + (AlmogaversScreen.SCR_WIDTH / TILE_WIDTH);
            if(endTileX >= mapWidth) {
                endTileX = mapWidth - 1;
            }
        }
        int initTileY, endTileY;
        if(camY > maxCamDown) {
            initTileY = maxCamDown / TILE_HEIGHT;
            endTileY = mapHeight - 1;
        } else {
            if(camY < 0) {
                initTileY = 0;
            } else {
                initTileY = camY / TILE_HEIGHT;
            }
            endTileY = 1 + initTileY + (AlmogaversScreen.SCR_HEIGHT / TILE_HEIGHT);
            if(endTileY >= mapHeight) {
                endTileY = mapHeight - 1;
            }
        }
        for(int px = initTileX ; px <= endTileX ; px++ ) {
            for(int py = initTileY ; py <= endTileY ; py++) {

                int tile = tiles[px][py];
                switch(tile) {
                    case IDX_VOID:
                        break;
                    case IDX_STAIRS:
                    case IDX_STAIR_NEXUS:
                        g.drawImage(imgStairs,px * TILE_WIDTH, py * TILE_HEIGHT, null);
                        break;
                    case IDX_ZOMBIE_TOMB:
                        g.drawImage(ZombieTomb.image,px * TILE_WIDTH, py * TILE_HEIGHT,null);
                        break;
                    default:
                        if(tile >= IDX_WALLS) {
                            int wt = tile - IDX_WALLS;
                            int wtx = (wt & 15) * TILE_WIDTH;
                            int wty = (wt >> 4) * TILE_HEIGHT;
                            g.drawImage(imgWalls,px * TILE_WIDTH, py * TILE_HEIGHT,
                                    px * TILE_WIDTH + TILE_WIDTH, py * TILE_HEIGHT + TILE_HEIGHT,
                                    wtx, wty, wtx + TILE_WIDTH, wty + TILE_HEIGHT
                                    , null);
                        }
                        break;
                }


                //g.setColor(Color.white);
                //g.drawString(""+tile,px * TILE_WIDTH, py * TILE_HEIGHT);
            }
        }
    }

    public boolean canTraverse(int tileX,int tileY,boolean ignoreStairs) {
        int tile = tiles[tileX][tileY];
        switch(tile) {
            case IDX_STAIR_NEXUS:
                return ignoreStairs;
            default:
                return tile < IDX_INVISIBLE_WALL;
        }
    }

    public static final int TILE_WIDTH = 32, TILE_HEIGHT = 32;

    public static final int IDX_VOID = -1;
    public static final int IDX_STAIRS = 0;
    public static final int IDX_STAIR_NEXUS = 1;
    public static final int IDX_FREE_PLATFORM = 2;
    public static final int IDX_FREE_PLATFORM_UP = 3;
    public static final int IDX_FREE_PLATFORM_RIGHT = 4;
    public static final int IDX_FREE_PLATFORM_DOWN = 5;
    public static final int IDX_FREE_PLATFORM_LEFT = 6;
    public static final int IDX_ZOMBIE_TOMB = 7;
    public static final int IDX_PLAYER = 8;
    public static final int IDX_BARRIER_ZOMBIE = 9;

    public static final int IDX_AD_1 = 16;
    public static final int IDX_AD_2 = 17;
    public static final int IDX_AD_3 = 18;
    public static final int IDX_AD_4 = 19;
    public static final int IDX_AD_5 = 20;
    public static final int IDX_AD_6 = 21;

    public static final int IDX_ITEM_DOUBLE_JUMP = 0x20;

    public static final int IDX_INVISIBLE_WALL = 127;
    public static final int IDX_WALLS = 128;
}
