package es.mario.obj.platform;

import es.mario.obj.Box;
import es.mario.obj.GameObject;

/**
 *
 */
public class CollisionDetector {
    public static boolean isColliding(GameObject obj1, GameObject obj2) {
        Box box1 = obj1.getBox();
        Box box2 = obj2.getBox();

        if(box1 == null || box2 == null) {
            return false;
        } else {
            return (Math.abs(box1.centerX - box2.centerX) <= (box1.radiusX + box2.radiusX))
                && (Math.abs(box1.centerY - box2.centerY) <= (box1.radiusY + box2.radiusY));
        }
    }
}
