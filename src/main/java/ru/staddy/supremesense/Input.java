package ru.staddy.supremesense;

import java.awt.event.KeyEvent;

public class Input {
    public static final int MAX_KEYS = 16;

    public enum Key {  UP, DOWN, LEFT, RIGHT,
                JUMP, SHOOT, WAVE,
                ESCAPE
    }

    public boolean[] buttons = new boolean[MAX_KEYS];
    public boolean[] oldButtons = new boolean[MAX_KEYS];

    public int x = 0, y = 0;
    
    public void set(int key, boolean down) {
        int button = -1;

        switch(key) {
            case KeyEvent.VK_UP:
                button = Key.UP.ordinal();
                break;
            case KeyEvent.VK_DOWN:
                button = Key.DOWN.ordinal();
                break;
            case KeyEvent.VK_LEFT:
                button = Key.LEFT.ordinal();
                break;
            case KeyEvent.VK_RIGHT:
                button = Key.RIGHT.ordinal();
                break;
            case KeyEvent.VK_Z:
                button = Key.JUMP.ordinal();
                break;
            case KeyEvent.VK_X:
                button = Key.SHOOT.ordinal();
                break;
            case KeyEvent.VK_A:
                button = Key.WAVE.ordinal();
                break;
            case KeyEvent.VK_ESCAPE:
                button = Key.ESCAPE.ordinal();
                break;
        }

        if (button >= 0) {
            buttons[button] = down;
        }
    }
    
    public void set(Key k, boolean down) {
        buttons[k.ordinal()] = down;
    }
    
    public boolean getButton(Key key) {
        return buttons[key.ordinal()];
    }
    
    public boolean getOldButton(Key key) {
        return oldButtons[key.ordinal()];
    }

    public void tick() {
        for (int i = 0; i < buttons.length; ++i) {
            oldButtons[i] = buttons[i];
        }
    }

    public void releaseAllKeys() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = false;
        }
    }
}
