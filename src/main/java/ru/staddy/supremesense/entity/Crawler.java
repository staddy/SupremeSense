package ru.staddy.supremesense.entity;

import java.awt.Graphics;
import java.util.Random;
import ru.staddy.supremesense.Art;
import ru.staddy.supremesense.Input;
import ru.staddy.supremesense.level.Camera;
import ru.staddy.supremesense.level.Level;

public class Crawler extends Entity {
    boolean direction;
    double speed = 0.05;
    static double X_FRICTION = 0.7;
    int frame = 1;
    Random rnd = new Random();
    
    boolean isControlled = false;
    
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
        if(isControlled && input != null) {
            if (input.getButton(Input.Key.LEFT)) {
                xa -= speed;
                direction = true;
            }
            if (input.getButton(Input.Key.RIGHT)) {
                xa += speed;
                direction = false;
            }
            if (input.getButton(Input.Key.WAVE)) {
                level.cameraHolder = host;
                host.input = this.input;
                this.input = null;
                isControlled = false;
            }
        } else {
            if(onGround) {
                if(level.isFree(x + xa - 1 + (direction ? 0 : w), y + 1, 1, h)) {
                    direction = !direction;
                }
                xa -= (direction ? speed : -speed);
            }
        }
        
        xa *= X_FRICTION;
        ya *= Level.FRICTION;
        ya += Level.GRAVITY;
        if(rnd.nextInt() % 4 == 0)
            level.add(new Slime(x + w / 2 + rnd.nextInt() % 6, y + h - 1, (double)((rnd.nextInt() % 6)) / 10, 0));
    }
    
    @Override
    public void render(Graphics g, Camera camera) {
        frame = (int)Math.round(x) % 4;
        if(direction)
            g.drawImage(Art.monsters1[frame][0], (int)x, (int)y, null);
        else
            g.drawImage(Art.monsters2[frame][0], (int)x, (int)y, null);
    }
    
    @Override
    public boolean shot(Entity e) {
        double speed = Math.sqrt(e.xa*e.xa + e.ya*e.ya);
        for(int i = 0; i < 10; ++i)
            level.add(new Slime(x + w / 2, y + h / 2, -e.xa / speed * 2.0 + (double)((rnd.nextInt() % 10)) / 10, -e.ya / speed * 2.0 + (double)((rnd.nextInt() % 10)) / 10));
        xa += e.xa * 0.2;
        ya += e.ya * 0.2;
        return true;
    }
    
    @Override
    public boolean catchMind(Entity e) {
        host = e;
        this.input = e.input;
        level.cameraHolder = this;
        isControlled = true;
        return true;
    }
}
