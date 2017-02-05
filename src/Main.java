import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 1/29/2017
 */
public class Main {

    public static final int WINWIDTH = 640, WINHEIGHT = 480;

    private static JFrame frame;

    private static boolean paused;

    public static void main(String[] args) {
        frame = new JFrame("SquirrelEatSquirrel");

        Game game = new Game();
        EasyKey key = new EasyKey();

        frame.add(game, BorderLayout.CENTER);
        frame.addKeyListener(key);
        frame.setFocusable(true);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e);
                paused = true;
            }

            @Override
            public void componentShown(ComponentEvent e) {
                super.componentHidden(e);
                paused = false;
            }
        });

        frame.setSize( new Dimension(WINWIDTH, WINHEIGHT) );
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // set in the middle of the screen
        frame.setVisible(true);

        frame.requestFocus();

        long lastTime = System.nanoTime(),
                timer = System.currentTimeMillis();
        int frames = 0;

        final double ns = 1e+9 / 15; // nano seconds per frame, 15 fps

        double delta = 0;

        boolean running = true;
        paused = false;

        while(running) {
            if(!paused) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;

                while (delta >= 1) {
                    game.update();
                    delta--;
                    frames++;
                }

                // its been a second!
                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    frame.setTitle(String.format("Squirrel Eat Squirrel | %1d fps", frames));
                    frames = 0;
                }
            }

            // escape key
            if(EasyKey.keyPressed(27))
                running = false;

            // r/R key
            if(EasyKey.keyPressed(82))
                game.reset();
        }

        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
