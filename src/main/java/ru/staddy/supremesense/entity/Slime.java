package ru.staddy.supremesense.entity;

import java.awt.Color;
import java.awt.Graphics;
import ru.staddy.supremesense.level.Camera;
import ru.staddy.supremesense.level.Level;

public class Slime extends Entity {
    static double X_FRICTION = 0.7;
    int time = 0;
    
    public Slime(double x, double y, double xa, double ya) {
        this.x = x;
        this.y = y;
        this.xa = xa;
        this.ya = ya;
        w = 1;
        h = 1;
        bounce = 0.9;
    }
    
    @Override
    public void tick() {
        tryMove(xa, ya);
        if(onGround)
            xa *= X_FRICTION;
        ya *= Level.FRICTION;
        ya += Level.GRAVITY;
        if(time++ >= 120)
            remove();
    }
    
    @Override
    public void render(Graphics g, Camera camera) {
        g.setColor(Color.GREEN);
        g.fillRect((int)x, (int)y, w, h);
    }
}
