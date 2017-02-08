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
            NUMGRASS = 50,
            NUMSQUIRRELS = 20,
            WINSIZE = 300,
            CAMERASLACK = 45; // from the middle of the screen, pixels

    private Player player;
    private List<Enemy> enemies;
    private List<Grass> grass;

    private int invincibilityFrames;
    private int livesLeft;

    private Font font;

    public Game() {
        reset();
        font = new Font("default", Font.BOLD, 28);
    }

    public void update() {
        if(!gameOver()) {
            keys();

            if(invincibilityFrames > 0)
                invincibilityFrames--;

            player.update();
            enemies.forEach(Enemy::update);

            Enemy colliding = player.intersects(enemies);
            if (colliding != null && Math.abs(colliding.getSize() - player.getSize()) >= 5) {
                if (colliding.getSize() > player.getSize()) {
                    if(invincibilityFrames <= 0) {
                        livesLeft--;
                        invincibilityFrames = 23;
                    }
                } else {
                    player.eat(colliding);
                    enemies.remove(colliding);
                }
            }
        }

        transformSprites();
        spawnThings();

        repaint();
    }

    private void keys() {
        // up, w
        if(EasyKey.keyPressed(87) || EasyKey.keyPressed(38))
            player.setMoveUp(true);
        else player.setMoveUp(false);

        // left, a
        if(EasyKey.keyPressed(65) || EasyKey.keyPressed((37))) {
            player.setMoveLeft(true);
            player.setFacingRight(true);
        } else player.setMoveLeft(false);

        // down, s
        if(EasyKey.keyPressed(83) || EasyKey.keyPressed(40))
            player.setMoveDown(true);
        else player.setMoveDown(false);

        // right, d
        if(EasyKey.keyPressed(68) || EasyKey.keyPressed(39)) {
            player.setMoveRight(true);
            player.setFacingRight(false);
        } else player.setMoveRight(false);

        if(EasyKey.noPress()) {
            player.setMoveUp(false);
            player.setMoveDown(false);
            player.setMoveLeft(false);
            player.setMoveRight(false);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setOpaque(true);
        setBackground(Color.GREEN);

        // draw grass
        for(int i = 0; i < grass.size(); i++) {
            Grass grass = this.grass.get(i);
            if(outsideArea(grass.getPos())) {
                this.grass.remove(i--);
                continue;
            }

            g.drawImage(grass.getSprite(), grass.getPos().getX(), grass.getPos().getY(), null);
        }

        // draw enemies
        for(int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if(outsideArea(e.getPos())) {
                enemies.remove(i--);
                continue;
            }

            drawSquirrel(g, e);
        }

        // draw player
        drawSquirrel(g, player);

        // draw lives
        g.setColor(Color.RED);
        for(int i = 0; i < livesLeft; i++)
            g.fillRect(20, 20 + i * 11, 20, 10);

        g.setColor(Color.BLACK);

        if(!g.getFont().equals(font))
            g.setFont(font);

        if(player.getSize() > WINSIZE)
            g.drawString("YOU HAVE ACHIEVED OMEGA SQUIRREL!", 10, Main.WINHEIGHT / 2);
        else if(gameOver())
            g.drawString("Game over. R to restart.", Main.WINWIDTH / 4, Main.WINHEIGHT / 2);
    }

    private void drawSquirrel(Graphics g, Squirrel squirrel) {
        g.drawImage(
                GivingTree.getSquirrel(),
                squirrel.getPos().getX(),
                squirrel.getPos().getY() - squirrel.getBounceHeight(),
                squirrel.getImgWidth() * (squirrel.isFacingRight() ? 1 : -1),
                squirrel.getImgHeight(),
                null
        );
    }

    private boolean outsideArea(Point p) {
        return p.getX() < -Main.WINWIDTH * .5 || p.getX() > Main.WINWIDTH * 1.5 ||
                p.getY() < -Main.WINHEIGHT * .5 || p.getY() > Main.WINHEIGHT * 1.5;
    }

    /**
     * Works now.
     * Transforms all sprites on screen so they will move with the player.
     *
     * Only moves the sprites when the player is outside the slack box.
     */
    private void transformSprites() {
        Point middle = new Point(Main.WINWIDTH / 2, Main.WINHEIGHT / 2);
        Point player = this.player.getPos();

        int dx, dy;
        if(player.getX() < middle.getX() - CAMERASLACK)
            dx = player.getX() - middle.getX() + CAMERASLACK;
        else if(player.getX() > middle.getX() + CAMERASLACK)
            dx = player.getX() - middle.getX() - CAMERASLACK;
        else dx = 0;

        if(player.getY() < middle.getY() - CAMERASLACK)
            dy = player.getY() - middle.getY() + CAMERASLACK;
        else if(player.getY() > middle.getY() + CAMERASLACK)
            dy = player.getY() - middle.getY() - CAMERASLACK;
        else dy = 0;

        if(Math.abs(dx) > 0 || Math.abs(dy) > 0) {
            enemies.forEach(e -> e.getPos().add(-dx, -dy));
            grass.forEach(g -> g.getPos().add(-dx, -dy));

            player.add(-dx, -dy);
        }

    }

    /**
     * This function/method spawns new squirrels and grass objects.
     */
    private void spawnThings() {

        for(int i = (int)(Math.random() * (NUMSQUIRRELS - enemies.size()) / 2); i >= 0; i--) {
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

        if(!player.isMoving())
            return;

        for(int i = (int)(Math.random() * (NUMGRASS - grass.size()) / 2); i >= 0; i--) {
            // was grass.size() >= NUMSQUIRRELS before, lol
            if(grass.size() >= NUMGRASS)
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

    public void reset() {
        player = new Player();

        enemies = new ArrayList<>();
        grass = new ArrayList<>();

        livesLeft = 3;

        for(int i = 0; i < NUMGRASS / 2; i++)
            grass.add( new Grass((int)(Math.random() * Main.WINWIDTH), (int)(Math.random() * Main.WINHEIGHT)) );
    }

    public boolean gameOver() {
        return livesLeft <= 0;
    }
}
