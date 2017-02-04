/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 1/29/2017
 */
public class Enemy extends Squirrel {

    public static final int TURNCHANCE = 2;

    private Point move;

    public Enemy(int x, int y) {
        this(x, y, random(5, 3), random(5, 3));
    }

    public Enemy(int x, int y, int dx, int dy) {
        super();
        pos = new Point(x, y);
        move = new Point(dx, dy);

        size = random(160, 0) + Player.player.getSize();

        facingRight = move.getX() < 0;
    }

    public void update() {
        bounce(true);

        pos.add(move);

        if(Math.random() * 100 < TURNCHANCE) {
            move = new Point(random(5, 3), random(5, 3));

            facingRight = move.getX() < 0;
        }
    }

    private static int random(int factor, int insideNumber) {
        return ((int)(Math.random() * factor) + insideNumber) * Math.random() < .5 ? 1 : -1;
    }
}
