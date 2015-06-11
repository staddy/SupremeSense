package ru.staddy.supremesense.screen;

import ru.staddy.supremesense.Art;
import ru.staddy.supremesense.Input;
import ru.staddy.supremesense.SupremeSense;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Screen {
    protected static Random random = new Random();
    private SupremeSense supremeSense;
    
    ArrayList<Input> inputs;
    
    boolean animationStarted = false;
    boolean animationStopped = false;
    long animationTime;
    static long letterTime = 200000000;
    
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
    
    public void drawAnimatedString(String string, Graphics g, int x, int y) {
        int len = string.length();
        if(!animationStarted && !animationStopped) {
            animationStarted = true;
            animationTime = System.nanoTime();
        }
        int pos = len;
        if(!animationStopped) {
            pos = (int)((System.nanoTime() - animationTime) / letterTime);
            if(pos > len)
                pos = len;
        }
        string = string.toUpperCase();
        for (int i=0; i<pos; i++) {
            char ch = string.charAt(i);
            for (int ys=0; ys<chars.length; ys++) {
                int xs = chars[ys].indexOf(ch);
                if (xs>=0) {
                    if(i == (pos - 1) && !animationStopped) {
                        int yp = random.nextInt(2);
                        int xp = random.nextInt(yp == 0 ? 36 : 17);
                        g.drawImage(Art.symbols[xp][yp], x+i*6, y, null);
                    }
                    g.drawImage(Art.symbols[xs][ys], x+i*6, y, null);
                }
            }
            if(i == (len - 1))
                animationStopped = true;
        }
    }
    
    public void resetAnimation() {
        animationStarted = false;
        animationStopped = false;
    }

    public void render(Graphics g) {
    }

    public void tick() {
    }
}