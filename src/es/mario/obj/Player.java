package es.mario.obj;
import es.mario.AlmogaversApplet;
import es.mario.graphics.TileMap;
import es.mario.AlmogaversScreen;
import es.mario.Game;
import es.mario.obj.platform.FreePlatform;
import es.mario.obj.weapons.Knife;
import es.mario.obj.weapons.Weapon;

import java.awt.*;

/**
 *
 */
public class Player implements GameObject {
    public static final int WIDTH = 30;
    public static final int HEIGHT = 60;
    
    public static final int TOUCH_MARGIN = 4;

    public static final int WEAPON_NONE = 0;
    public static final int WEAPON_KNIFE = 1;

    public static final int GRAVITY = 1;
    public static final int MAX_VERT_SPEED = 6;

    public static final int JUMP_ACCEL_1 = -8;
    public static final int JUMP_ACCEL_2 = -12;
    public static final int MOVE_SPEED = 4;

    private int jumpAccel = JUMP_ACCEL_1;
    private int currentAction;
    private int yspeed = 0;
    private boolean jumping = false;
    private boolean goingLeft = false;
    private boolean goingRight = false;
     private boolean goingUp = false;
    private boolean goingDown = false;

    public boolean facingLeft;

    private Box box = new Box();

    public int x = 40;
    public int y = 75 * 32;

    public Weapon weapon;

    private static Image playerImg = null;
    private int piw, pih;

    //pixels to push from mobile platforms
//    private int pushedX,pushedY;

    private boolean canJump = false;
    public boolean canShoot = true;
//    private boolean hasFreeMove = true;
//    private boolean overPlatform = false;
    private Game theGame;

    //linkedPlatform[FreePlatform.DOWN] means the free platform under the player
    // the same for the other platforms in array;
    public FreePlatform[] linkedPlatform = new FreePlatform[4];

    public static Player instance = null;

    public Player(Game theGame) {
        this.theGame = theGame;

        currentAction = ACTION_NORMAL;
        canJump = true;
        box.radiusX = (WIDTH / 2) - TOUCH_MARGIN;
        box.radiusY = HEIGHT / 2;
        instance = this;

        playerImg = AlmogaversApplet.loadImage("/prota.png");
        do {
           piw = playerImg.getWidth(null);
        }while(piw < 0);
        pih = playerImg.getHeight(null);
    }

    public boolean move() {
        switch(currentAction) {
            case ACTION_NORMAL:
                moveNormal();
                break;
            case ACTION_CLIMBING:
                moveClimbing();
                break;
            case ACTION_SHOOTING:
                moveShooting();
                break;
        }

        box.centerX = x + WIDTH / 2;
        box.centerY = y + HEIGHT /2;

//        for(int i = 0 ; i < linkedPlatform.length ; i++) {
//            linkedPlatform[i] = null;
//        }
//        hasFreeMove = true;
//        overPlatform = false;
        return false;
    }

    public void moveClimbing() {
        int rest = x % TileMap.TILE_WIDTH;
        if(rest != 0) {
            if(rest < (TileMap.TILE_WIDTH / 2)) {
                x -= rest;
            } else {
                x += TileMap.TILE_WIDTH - rest;
            }
        }
        if(goingUp) {
            if(canClimb(true)) {
                y -= MOVE_SPEED;
                if(!canClimb(true)) {
                    canJump = false;
                    jumping = false;
                    yspeed = 0;
                    currentAction = ACTION_NORMAL;
                    y += MOVE_SPEED;
                }
            }
        } else if(goingDown) {
            if(canClimb(false)) {
                y += MOVE_SPEED;
                if(!canClimb(false)) {
                    currentAction = ACTION_NORMAL;
                    //y -= MOVE_SPEED;
                }
            }
        }
    }

    public void moveShooting() {
        boolean bjumping = this.jumping;
        boolean bgoingLeft = this.goingLeft;
        boolean bgoingRight = this.goingRight;
        boolean bgoingUp = this.goingUp;
        boolean bgoingDown = this.goingDown;
        goingLeft = goingRight = goingDown = goingUp = jumping = false;
        moveNormal();
//        moveShooting();
        weapon.move();
        if(!weapon.isBeingUsed()) {
            currentAction = ACTION_NORMAL;
        }
        this. jumping = bjumping;
        this. goingLeft = bgoingLeft;
        this. goingRight = bgoingRight;
        this. goingUp = bgoingUp;
        this. goingDown = bgoingDown;
    }
    
    public void moveNormal() {
//        x += pushedX;
//        y += pushedY;

        int dleft = (x + TOUCH_MARGIN + 1)/TileMap.TILE_WIDTH;
        int dright = (x - TOUCH_MARGIN - 1+ WIDTH)/TileMap.TILE_WIDTH;
        int ddown = (y + HEIGHT + yspeed)/TileMap.TILE_HEIGHT;

        int down = (y + HEIGHT + yspeed - TOUCH_MARGIN)/TileMap.TILE_HEIGHT;
        int up = (y + yspeed + TOUCH_MARGIN)/TileMap.TILE_HEIGHT;
        int left = (x + TOUCH_MARGIN - MOVE_SPEED)/TileMap.TILE_WIDTH;
        int right = (x - TOUCH_MARGIN + MOVE_SPEED + WIDTH)/TileMap.TILE_WIDTH;
        int middle = (y + (HEIGHT / 2) + yspeed) / TileMap.TILE_HEIGHT;

        boolean headKnok = false;
        boolean landing = false;
        if(yspeed != 0 && linkedPlatform[FreePlatform.UP] != null) {
            headKnok = true;
            if(linkedPlatform[FreePlatform.UP].getDirection() == FreePlatform.DOWN) {
                yspeed = FreePlatform.MOVE_SPEED + 1;
            } else {
                yspeed = 1;                
            }
            y += yspeed;
        } else if(theGame.tileMap.canTraverse(dleft,ddown,false)
                && theGame.tileMap.canTraverse(dright,ddown,false)
                && (linkedPlatform[FreePlatform.DOWN] == null) ) {
            y += yspeed;
            yspeed += GRAVITY;
            if(yspeed < 0) {
                if(!theGame.tileMap.canTraverse(dleft,up,false) || !theGame.tileMap.canTraverse(dright,up,false)) {
                    headKnok = true;
                    y -= yspeed;
                    yspeed = 0;
                }
            } else {
                if(yspeed > MAX_VERT_SPEED) {
                    yspeed = MAX_VERT_SPEED;
                }
            }
        } else {
            y += yspeed;
            if(yspeed > 0) {
                y -= (y + HEIGHT) % TileMap.TILE_HEIGHT;
                yspeed = 0;
                landing = true;
                jumping = false;                
            }
            if(jumping && currentAction == ACTION_NORMAL) {
                yspeed = jumpAccel;
                linkedPlatform[FreePlatform.DOWN] = null;
            } else {
                jumping = false;
            }
        }
        if(!headKnok && !landing) {
            if(goingLeft && linkedPlatform[FreePlatform.LEFT] == null) {
                x -= MOVE_SPEED;
                if(theGame.tileMap.canTraverse(left,up,false)
                        && theGame.tileMap.canTraverse(left,down,false)
                        && theGame.tileMap.canTraverse(left,middle,false)) {
                } else {
                    x = x + TileMap.TILE_WIDTH;
                    x -= (x % TileMap.TILE_WIDTH) + TOUCH_MARGIN;
                }
            } else if(goingRight && linkedPlatform[FreePlatform.RIGHT] == null) {
                x += MOVE_SPEED;
                if(theGame.tileMap.canTraverse(right,up,false)
                        && theGame.tileMap.canTraverse(right,down,false)
                        && theGame.tileMap.canTraverse(right,middle,false)) {
                } else {
                    x = x - (x % TileMap.TILE_WIDTH) + TOUCH_MARGIN;
                }
            }
        }

        if(linkedPlatform[FreePlatform.DOWN] != null) {
            Box pbox = linkedPlatform[FreePlatform.DOWN].getBox();
            y = pbox.centerY - pbox.radiusY - HEIGHT;
            int direction = linkedPlatform[FreePlatform.DOWN].getDirection();
            if(direction == FreePlatform.RIGHT) {
                x += FreePlatform.MOVE_SPEED;
            } else if(direction == FreePlatform.LEFT) {
                x -= FreePlatform.MOVE_SPEED;
            }
            yspeed = 0;
        }
        if((linkedPlatform[FreePlatform.RIGHT] != null) && (linkedPlatform[FreePlatform.RIGHT].getDirection() == FreePlatform.LEFT)) {
            x -= FreePlatform.MOVE_SPEED;
        }
        if((linkedPlatform[FreePlatform.LEFT] != null) && (linkedPlatform[FreePlatform.LEFT].getDirection() == FreePlatform.RIGHT)) {
            x += FreePlatform.MOVE_SPEED; 
        }
        

//        pushedX = pushedY = 0;
    }
    public void paint(Graphics g) {
        if(facingLeft) {
            g.drawImage(playerImg, x, y, null);
        } else {
            g.drawImage(playerImg, x, y, x + piw, y + pih,piw,0,0,pih,null);

        }
        if (currentAction == ACTION_SHOOTING) {
            weapon.paint(g);
        }
    }



    public void setGoingUp(boolean goingUp) {
        if(goingUp) {
            if(canClimb(true)) {
                if(!jumping && yspeed == 0) {
                    currentAction = ACTION_CLIMBING;
                    this.goingUp = true;
                }
            } else {
                if(canJump || (linkedPlatform[FreePlatform.DOWN] != null)) {
                    jumping = true;
                    canJump = false;
                }
            }
        } else {
            canJump = true;
            this.goingUp = false;
            jumping = false;
        }
    }

    public int getJumpAccel() {
        return jumpAccel;
    }

    public void setJumpAccel(int jumpAccel) {
        this.jumpAccel = jumpAccel;
    }



    // position: if the player is over, under, at left, or at right of the platform
    public void linkPlatform(FreePlatform platform,int position) {
        linkedPlatform[position] = platform;
    }

    public void unlinkPlatform(int position) {
        if(position != -1) {
            linkedPlatform[position] = null;
        }
    }

//    public void push(int x, int y, boolean hasFreeMove) {
//        pushedX += x;
//        pushedY += y;
//        this.hasFreeMove = hasFreeMove;
//    }
//
//    public void isSteppingMe(Box box) {
//        y = box.centerY - box.radiusY - HEIGHT;
//        yspeed = 0;
//        pushedY = 0;
//        overPlatform = true;
//    }

    public void setGoingDown(boolean goingDown) {
        if(goingDown) {
            if(canClimb(false)) {
                currentAction = ACTION_CLIMBING;
                this.goingDown = true;
            } else {
                currentAction = ACTION_NORMAL;
            }
        } else {
            this.goingDown = false;
        }
    }

    private boolean canClimb(boolean goUpper) {
        int up = y / TileMap.TILE_HEIGHT;
        int down = (y + HEIGHT) / TileMap.TILE_HEIGHT ;
        int left = (x + CLIMBING_MARGIN) / TileMap.TILE_WIDTH;
        int right = (x + WIDTH - CLIMBING_MARGIN) / TileMap.TILE_WIDTH;
        boolean climbUp = (theGame.tileMap.tiles[left][up] == TileMap.IDX_STAIRS || theGame.tileMap.tiles[left][up] == TileMap.IDX_STAIR_NEXUS)
             && (theGame.tileMap.tiles[right][up] == TileMap.IDX_STAIRS || theGame.tileMap.tiles[right][up] == TileMap.IDX_STAIR_NEXUS);
        boolean climbDown = (theGame.tileMap.tiles[left][down] == TileMap.IDX_STAIRS ||theGame. tileMap.tiles[left][down] == TileMap.IDX_STAIR_NEXUS)
             && (theGame.tileMap.tiles[right][down] == TileMap.IDX_STAIRS ||theGame. tileMap.tiles[right][down] == TileMap.IDX_STAIR_NEXUS);

        if(goUpper) {
            if(currentAction != ACTION_CLIMBING) {
                return climbUp;
            } else {
                return climbDown || climbUp;
            }
        } else {
            return climbDown && (theGame.tileMap.canTraverse(left,down,true) && theGame.tileMap.canTraverse(right,down,true));
        }

    }

    public void setGoingLeft(boolean goingLeft) {
        this.goingLeft = goingLeft;
        if(goingLeft == true && currentAction == ACTION_NORMAL) {
            facingLeft = true;
        }
    }

    public void setGoingRight(boolean goingRight) {
        this.goingRight = goingRight;
        if(goingRight == true && currentAction == ACTION_NORMAL) {
            facingLeft = false;
        }
    }

    public void shoot() {
        if(canShoot && currentAction == ACTION_NORMAL && !weapon.isBeingUsed() ) {
            weapon.shoot();
            currentAction = ACTION_SHOOTING;
            canShoot = false;
        }
    }

    public Box getBox() {
        return box;
    }

    public static final int ACTION_NORMAL = 0;
    public static final int ACTION_CLIMBING = 1;
    public static final int ACTION_SHOOTING = 2;
    public static final int ACTION_SIT = 3;
    
    private static int CLIMBING_MARGIN = 10;

    public void onCollision(GameObject collidingObject) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}


