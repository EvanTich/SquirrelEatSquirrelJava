import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 1/30/2017
 */
public class GivingTree {

    public static final BufferedImage SQUIRREL_IMG;
    public static final BufferedImage[] GRASS_IMGS;

    static {
        SQUIRREL_IMG = loadSprite("/sprites/squirrel.png");
        GRASS_IMGS = new BufferedImage[4];

        for(int i = 0; i < 4; i++)
            GRASS_IMGS[i] = loadSprite( String.format("/sprites/grass%1d.png", i + 1) );
    }

    public static BufferedImage loadSprite(String path) {
        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(GivingTree.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public static BufferedImage getRandomGrass() {
        return GRASS_IMGS[(int)(Math.random() * GRASS_IMGS.length)];
    }

    public static BufferedImage getSquirrel() {
        return SQUIRREL_IMG;
    }

    public static int getSquirrelWidth() {
        return SQUIRREL_IMG.getWidth();
    }

    public static int getSquirrelHeight() {
        return SQUIRREL_IMG.getHeight();
    }
}
