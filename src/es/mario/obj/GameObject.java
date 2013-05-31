package es.mario.obj;

import java.awt.*;

/**
 *
 */
public interface GameObject {

    /**
     * @return true if the object must be removed from its containing list 
     */
    public boolean move();

    public Box getBox();
    public void onCollision(GameObject collidingObject);

    public void paint(Graphics g);
}
