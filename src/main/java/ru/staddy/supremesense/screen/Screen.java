package ru.staddy.supremesense.screen;

import ru.staddy.supremesense.Art;
import ru.staddy.supremesense.Input;
import ru.staddy.supremesense.SupremeSense;

import java.awt.*;
import java.util.Random;

public class Screen {
    protected static Random random = new Random();
    private SupremeSense supremeSense;
    
    public void removed() {
    }

    public final void init(SupremeSense supremeSense) {
        this.supremeSense = supremeSense;
    }
    
    protected void setScreen(Screen screen) {
        supremeSense.setScreen(screen);
    }

    String[] chars = {
                      "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",
                      ".,!?:;\"'+-=/\\< "
    };
            
    public void drawString(String string, Graphics g, int x, int y) {
        string = string.toUpperCase();
        for (int i=0; i<string.length(); i++) {
            char ch = string.charAt(i);
            for (int ys=0; ys<chars.length; ys++) {
                int xs = chars[ys].indexOf(ch);
                if (xs>=0) {
                    g.drawImage(Art.symbols[xs][ys], x+i*6, y, null);
                }
            }
        }
    }

    public void render(Graphics g) {
    }

    public void tick(Input input) {
    }
}