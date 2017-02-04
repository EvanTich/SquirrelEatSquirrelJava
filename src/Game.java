import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 1/29/2017
 */
public class Game extends JPanel {

    public static final int
            NUMGRASS = 80,
            NUMSQUIRRELS = 30,
            WINSIZE = 300,
            CAMERASLACK = 90;

    private Player player;
    private List<Enemy> enemies;
    private List<Grass> grass;

    private boolean gameOver;

    public Game() {
        player = new Player();

        enemies = new ArrayList<>();
        grass = new ArrayList<>();

        for(int i = 0; i < 10; i++)
            grass.add( new Grass((int)(Math.random() * Main.WINWIDTH), (int)(Math.random() * Main.WINHEIGHT)) );
    }

    public void update() {
        keys();

        player.update();
        enemies.forEach(Enemy::update);

        transformSprites();
        spawnThings();

        repaint();
    }

    public void keys() {
        if(EasyKey.keyPressed('w') || EasyKey.keyPressed((char)38)) {
            player.setMoveUp(true);
            player.setMoveDown(false);
        } else if(EasyKey.keyPressed('a') || EasyKey.keyPressed((char)37)) {
            player.setMoveLeft(true);
            player.setMoveRight(false);
            player.setFacingRight(false);
        } else if(EasyKey.keyPressed('s') || EasyKey.keyPressed((char)40)) {
            player.setMoveDown(true);
            player.setMoveUp(false);
        } else if(EasyKey.keyPressed('d') || EasyKey.keyPressed((char)39)) {
            player.setMoveRight(true);
            player.setMoveLeft(false);
            player.setFacingRight(true);
        }

        if(EasyKey.noPress()) {
            player.setMoveUp(false);
            player.setMoveDown(false);
            player.setMoveLeft(false);
            player.setMoveRight(false);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        setOpaque(true);
        setBackground(Color.GREEN);

        // draw grass
        for(int i = 0; i < grass.size(); i++) {
            Grass p = grass.get(i);
            if(outsideArea(p.getPos())) {
                grass.remove(i--);
                continue;
            }

            g2d.drawImage(p.getSprite(), p.getPos().getX(), p.getPos().getY(), null);
        }

        // draw enemies
        for(int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if(outsideArea(e.getPos())) {
                enemies.remove(i--);
                continue;
            }

            if(e.getPos().getX() < 0 || e.getPos().getY() < 0)
                continue;

            BufferedImage sqrl = GivingTree.getSquirrel();
            int w = sqrl.getWidth() * e.getSize() / 24,
                h = sqrl.getHeight() * e.getSize() / 24;

            g2d.drawImage(
                    sqrl,
                    e.getPos().getX() + w,
                    e.getPos().getY() - e.getBounceHeight(),
                    w * (e.isFacingRight() ? 1 : -1),
                    h,
                    null
            );
        }

        // draw player
        BufferedImage sqrl = GivingTree.getSquirrel();
        int w = sqrl.getWidth() * player.getSize() / 24,
            h = sqrl.getHeight() * player.getSize() / 24;

        g2d.drawImage(
                sqrl,
                player.getPos().getX() + w,
                player.getPos().getY() - player.getBounceHeight(),
                w * (player.isFacingRight() ? 1 : -1),
                h,
                null
        );

    }

    private boolean outsideArea(Point p) {
        return p.getX() < -Main.WINWIDTH * .5 || p.getX() > Main.WINWIDTH * 1.5 ||
                p.getY() < -Main.WINHEIGHT * .5 || p.getY() > Main.WINHEIGHT * 1.5;
    }

    private void transformSprites() {
        Point middle = new Point(Main.WINWIDTH / 2, Main.WINHEIGHT / 2);
        Point player = this.player.getPos();

        int dx = player.getX() - middle.getX() - CAMERASLACK,
            dy = player.getY() - middle.getY() - CAMERASLACK;

        if(Math.abs(dx) > 0 || Math.abs(dy) > 0) {
            enemies.forEach(e -> e.getPos().add(dx, dy));
            grass.forEach(g -> g.getPos().add(dx, dy));

            player.add(-dx, -dy);
        }

    }

    private void spawnThings() {
        double rand1 = Math.random() * (NUMSQUIRRELS - enemies.size());
        for(int i = 0; i < rand1; i++) {
            if(enemies.size() >= NUMSQUIRRELS)
                break;
            double rand = Math.random();
            if(rand < .25) // SPAWN AT TOP
                enemies.add( new Enemy((int) (Math.random() * Main.WINWIDTH), 0) );
            else if(rand < .5) // SPAWN AT BOTTOM
                enemies.add( new Enemy((int) (Math.random() * Main.WINWIDTH), Main.WINHEIGHT) );
            else if(rand < .75) // SPAWN AT LEFT
                enemies.add( new Enemy(0, (int) (Math.random() * Main.WINHEIGHT)) );
            else // SPAWN AT RIGHT
                enemies.add( new Enemy(Main.WINWIDTH, (int) (Math.random() * Main.WINHEIGHT)) );
        }

        double rand2 = Math.random() * (NUMGRASS - enemies.size());
        for(int i = 0; i < rand2; i++) {
            if(grass.size() >= NUMSQUIRRELS)
                break;

            double rand = Math.random();
            if(rand < .25) // SPAWN AT TOP
                grass.add( new Grass((int) (Math.random() * Main.WINWIDTH), 0) );
            else if(rand < .5) // SPAWN AT BOTTOM
                grass.add( new Grass((int) (Math.random() * Main.WINWIDTH), Main.WINHEIGHT) );
            else if(rand < .75) // SPAWN AT LEFT
                grass.add( new Grass(0, (int) (Math.random() * Main.WINHEIGHT)) );
            else // SPAWN AT RIGHT
                grass.add( new Grass(Main.WINWIDTH, (int) (Math.random() * Main.WINHEIGHT)) );
        }
    }
}
