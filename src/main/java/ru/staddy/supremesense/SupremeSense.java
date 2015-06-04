package ru.staddy.supremesense;

import ru.staddy.supremesense.screen.Screen;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by stad on 17.05.15.
 */
public class SupremeSense extends Applet implements Runnable, KeyListener {
    public static final int GAME_WIDTH = 320;
    public static final int GAME_HEIGHT = 240;
    public static final int SCREEN_SCALE = 2;

    private boolean running = false;
    private Screen screen;
    private Input input = new Input();
    private boolean started = false;

    public SupremeSense() {
        setPreferredSize(new Dimension(GAME_WIDTH * SCREEN_SCALE, GAME_HEIGHT * SCREEN_SCALE));
        this.addKeyListener(this);
        this.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent arg0) {
            }
            public void focusLost(FocusEvent arg0) {
                input.releaseAllKeys();
            }
        });
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    public void run() {

    }

    public void setScreen(Screen screen) {
        this.screen = screen;
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
}
