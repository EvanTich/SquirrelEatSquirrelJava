import java.awt.image.BufferedImage;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 1/31/2017
 */
public class Grass {

    private BufferedImage img;
    private Point pos;

    public Grass(int x, int y) {
        this.img = GivingTree.getRandomGrass();
        this.pos = new Point(x, y);
    }

    public BufferedImage getSprite() {
        return img;
    }

    public Point getPos() {
        return pos;
    }
}
