import java.awt.*;
import java.util.List;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 1/29/2017
 */
public class Player extends Squirrel {

    public static final int
            MOVERATE = 9,
            STARTSIZE = 25,
            MAXSIZE = 500;

    public static Player player;

    private boolean moveUp, moveDown, moveLeft, moveRight;

    public Player() {
        super();

        moveUp = false;
        moveDown = false;
        moveLeft = false;
        moveRight = false;

        size = STARTSIZE;

        player = this;
    }

    public void update() {
        bounce(isMoving());

        // ok then, thanks java graphics
        if(moveLeft)
            pos.add(-MOVERATE, 0);
        if(moveRight)
            pos.add(MOVERATE, 0);
        if(moveUp)
            pos.add(0, -MOVERATE);
        if(moveDown)
            pos.add(0, MOVERATE);

        if(size > MAXSIZE)
            size = MAXSIZE;
    }

    public Enemy intersects(List<Enemy> enemies) {
        Rectangle collider = getCollisionBox();

        for(Enemy e : enemies)
            if(collider.intersects(e.getCollisionBox()))
                return e;

        // return null if not colliding with any enemy
        return null;
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public boolean isMoving() {
        return moveUp ^ moveDown || moveLeft ^ moveRight;
    }
}
