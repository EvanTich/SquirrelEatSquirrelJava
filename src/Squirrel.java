/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 1/29/2017
 */
public class Squirrel {

    public static final int
            BOUNCERATE = 5, // lower = faster
            BOUNCEHEIGHT = 20; // max height in pixels

    protected Point pos;
    protected Point lastPos;

    protected int size;
    private int bounce;

    private boolean movingDown;

    protected boolean facingRight;

    // TODO: make collision box

    public Squirrel() {
        pos = new Point(0, 0);
        lastPos = pos.clone();
        facingRight = true;

        size = 1;
        bounce = 0;
        movingDown = false;
    }

    public void bounce(boolean moving) {
        if(!moving)
            return;

        lastPos = pos.clone();

        bounce += movingDown ? -2 : 1;

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

    public final int getBounceHeight() {
        return (int) (bounce * 1f / BOUNCERATE * BOUNCEHEIGHT);
    }

    public final boolean isFacingRight() {
        return facingRight;
    }
}
