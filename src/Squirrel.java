/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 1/29/2017
 */
public class Squirrel {

    public static final int
            MINSPEED = 3,
            MAXSPEED = 7,
            BOUNCERATE = 6,
            BOUNCEHEIGHT = 30;

    protected Point pos;

    protected int size;
    private int bounce;

    protected boolean facingRight;

    public Squirrel() {
        pos = new Point(0, 0);
        facingRight = true;

        size = 1;
        bounce = 0;
    }

    public void bounce() {
        bounce++;

        if(bounce > BOUNCERATE)
            bounce = 0;
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
