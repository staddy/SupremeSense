package ru.staddy.supremesense;

import ru.staddy.supremesense.screen.*;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SupremeSense extends Applet implements Runnable, KeyListener, MouseListener {
    public static final int GAME_WIDTH = 320;
    public static final int GAME_HEIGHT = 240;
    public static final int SCREEN_SCALE = 3;

    private boolean running = false;
    private Screen screen;
    private ArrayList<Input> inputs = new ArrayList<>();
    private boolean started = false;

    public SupremeSense() {
        setPreferredSize(new Dimension(GAME_WIDTH * SCREEN_SCALE, GAME_HEIGHT * SCREEN_SCALE));
        this.addKeyListener(this);
        this.addMouseListener(this);
        inputs.add(new Input());
        this.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent arg0) {
            }
            public void focusLost(FocusEvent arg0) {
                inputs.get(0).releaseAllKeys();
            }
        });
    }
    
    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void stop() {
        running = false;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        inputs.get(0).set(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
        inputs.get(0).set(e.getKeyCode(), false);
    }

    public void run() {
        requestFocus();
        Image image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_RGB);
        setScreen(new TitleScreen(inputs));
        
        long lastTime = System.nanoTime();
        long unprocessedTime = 0;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        while (running) {
            Graphics g = image.getGraphics();

            long now = System.nanoTime();
            unprocessedTime += now - lastTime;
            lastTime = now;

            int max = 10;
            while (unprocessedTime > 0) {
                unprocessedTime -= 1000000000 / 60;
                screen.tick();
                for(Input i : inputs)
                    i.tick();
                if (max-- == 0) {
                    unprocessedTime = 0;
                    break;
                }
            }

            screen.render(g);
            if (!hasFocus()) {
                String msg = "CLICK TO FOCUS!";
                int w = msg.length();
                int xp = 160 - w * 3;
                int yp = 120 - 3;
                g.setColor(Color.BLACK);
                g.fillRect(xp - 6, yp - 6, 6 * (w + 2), 6 * 3);
                if (System.currentTimeMillis() / 500 % 2 == 0) screen.drawString(msg, g, xp, yp);
            }

            g.dispose();
            try {
                started = true;
                g = getGraphics();
                g.drawImage(image, 0, 0, GAME_WIDTH * SCREEN_SCALE, GAME_HEIGHT * SCREEN_SCALE, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
                g.dispose();
            } catch (Throwable e) {
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setScreen(Screen screen) {
        if(this.screen != null)
            this.screen.removed();
        this.screen = screen;
        if(screen != null)
            this.screen.init(this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Supreme Sense");
        SupremeSense supremeSense = new SupremeSense();
        frame.setLayout(new BorderLayout());
        frame.add(supremeSense, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        supremeSense.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        inputs.get(0).x = e.getX() / SCREEN_SCALE;
        inputs.get(0).y = e.getY() / SCREEN_SCALE;
        inputs.get(0).set(KeyEvent.VK_X, true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        inputs.get(0).set(KeyEvent.VK_X, false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
