/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 1/29/2017
 */
public class Player extends Squirrel {

    public static final int
            MOVERATE = 9,
            STARTSIZE = 25;

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
        // TODO: fix holding two opposite directions at once, use XOR (x ^ y) probably
        bounce(moveUp || moveDown || moveLeft || moveRight);

        if(moveLeft)
            pos.add(MOVERATE, 0);
        if(moveRight)
            pos.add(-MOVERATE, 0);
        if(moveUp)
            pos.add(0, MOVERATE);
        if(moveDown)
            pos.add(0, -MOVERATE);
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
}
