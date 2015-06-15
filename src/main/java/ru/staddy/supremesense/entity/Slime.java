package ru.staddy.supremesense.entity;

import java.awt.Color;
import java.awt.Graphics;
import ru.staddy.supremesense.level.Camera;
import ru.staddy.supremesense.level.Level;

public class Slime extends Entity {
    static double X_FRICTION = 0.9;
    int time = 0;
    static int LIFETIME = 120;
    
    public Slime(double x, double y, double xa, double ya) {
        this.x = x;
        this.y = y;
        this.xa = xa;
        this.ya = ya;
        w = 1;
        h = 1;
    }
    
    @Override
    public void tick() {
        tryMove(xa, ya);
        if(onGround)
            xa *= X_FRICTION;
        ya *= Level.FRICTION;
        ya += Level.GRAVITY;
        if(++time >= LIFETIME)
            remove();
    }
    
    @Override
    public void render(Graphics g, Camera camera) {
        if(time < LIFETIME / 3)
            g.setColor(Color.decode("0x326400"));
        else if(time < 2 * LIFETIME / 3)
            g.setColor(Color.decode("0x324F00"));
        else
            g.setColor(Color.decode("0x1A2900"));
        g.fillRect((int)x, (int)y, w, h);
    }
}
