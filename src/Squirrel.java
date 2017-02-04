/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 1/29/2017
 */
public class Squirrel {

    public static final int
            BOUNCERATE = 5, // lower = faster
            BOUNCEHEIGHT = 20; // max height in pixels

    protected Point pos;

    protected int size;
    private int bounce;

    private boolean movingDown;

    protected boolean facingRight;

    // TODO: make collision box

    public Squirrel() {
        pos = new Point(0, 0);
        facingRight = true;

        size = 1;
        bounce = 0;
        movingDown = false;
    }

    public void bounce(boolean moving) {
        if(!moving) {
            if(bounce > 0)
                bounce -= 3;
            else bounce = 0;
            return;
        }

        bounce += movingDown ? -3 : 1;

        if(bounce >= BOUNCERATE)
            movingDown = true;
        else if(bounce <= 0)
            movingDown = false;
    }

    public final Point getPos() {
        return pos;
    }

    public final int getSize() {
        return size;
    }

    public final int getImgWidth() {
        return (int)(GivingTree.getSquirrelWidth() * size / 16.0);
    }

    public final int getImgHeight() {
        return (int)(GivingTree.getSquirrelHeight() * size / 16.0);
    }

    public final int getBounceHeight() {
        return (int) (bounce * 1f / BOUNCERATE * BOUNCEHEIGHT);
    }

    public final boolean isFacingRight() {
        return facingRight;
    }

    public final void eat(Squirrel other) {
        size += other.size;
    }

    public final boolean equals(Squirrel other) {
        return pos.equals(other.pos) &&
                size == other.size &&
                facingRight == other.facingRight;
    }
}
