import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 1/30/2017
 */
public class GivingTree {

    public static final BufferedImage SQUIRRELIMG;

    public static final BufferedImage[] GRASSIMGS;

    static {
        SQUIRRELIMG = loadSprite("sprites/squirrel.png");
        GRASSIMGS = new BufferedImage[4];

        for(int i = 0; i < 4; i++)
            GRASSIMGS[i] = loadSprite( String.format("sprites/grass%1d.png", i + 1) );
    }

    public static BufferedImage loadSprite(String path) {
        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public static BufferedImage getRandomGrass() {
        return GRASSIMGS[(int)(Math.random() * GRASSIMGS.length)];
    }

    public static BufferedImage getSquirrel() {
        return SQUIRRELIMG;
    }

    public static int getSquirrelWidth() {
        return SQUIRRELIMG.getWidth();
    }

    public static int getSquirrelHeight() {
        return SQUIRRELIMG.getHeight();
    }
}
