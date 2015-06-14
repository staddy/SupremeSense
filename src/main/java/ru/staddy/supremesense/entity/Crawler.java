package ru.staddy.supremesense.entity;

import java.awt.Color;
import java.awt.Graphics;
import ru.staddy.supremesense.level.Camera;
import ru.staddy.supremesense.level.Level;

public class Crawler extends Entity {
    boolean direction;
    double speed = 0.05;
    static double X_FRICTION = 0.7;
    
    public Crawler(double x, double y, boolean direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    
    @Override
    public void tick() {
        tryMove(xa, ya);
        if(onGround) {
            if(level.isFree(x + xa - 1 + (direction ? 0 : w), y + 1, 1, h)) {
                direction = !direction;
            }
            xa -= (direction ? speed : -speed);
        }
        
        xa *= X_FRICTION;
        ya *= Level.FRICTION;
        ya += Level.GRAVITY;
    }
    
    @Override
    public void render(Graphics g, Camera camera) {
        g.setColor(Color.BLUE);
        g.fillRect((int)x, (int)y, w, h);
    }
}
