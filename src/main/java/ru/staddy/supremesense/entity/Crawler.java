package ru.staddy.supremesense.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import ru.staddy.supremesense.Art;
import ru.staddy.supremesense.level.Camera;
import ru.staddy.supremesense.level.Level;

public class Crawler extends Entity {
    boolean direction;
    double speed = 0.05;
    static double X_FRICTION = 0.7;
    int frame = 1;
    Random rnd = new Random();
    
    public Crawler(double x, double y, boolean direction) {
        this.x = x;
        this.y = y;
        w = 10;
        h = 5;
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
        level.add(new Slime(x, y + 4, 2, -5));
    }
    
    @Override
    public void render(Graphics g, Camera camera) {
        frame = (int)Math.round(x) % 4;
        if(direction)
            g.drawImage(Art.monsters1[frame][0], (int)x, (int)y, null);
        else
            g.drawImage(Art.monsters2[frame][0], (int)x, (int)y, null);
    }
}
