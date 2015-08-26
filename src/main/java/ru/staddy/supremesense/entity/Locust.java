package ru.staddy.supremesense.entity;

import java.awt.Graphics;
import java.util.Random;
import ru.staddy.supremesense.Art;
import ru.staddy.supremesense.Input;
import ru.staddy.supremesense.level.Camera;
import ru.staddy.supremesense.level.Level;

public class Locust extends Entity {
    boolean direction;
    double speed = 0.04;
    static double X_FRICTION = 0.9;
    int JUMPY = 5, JUMPX = 5;
    int frame = 1;
    Random rnd = new Random();
    
    boolean isControlled = false;
    
    public Locust(double x, double y, boolean direction) {
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
            if(input.getButton(Input.Key.LEFT)) {
                xa -= speed;
                direction = true;
            }
            if(input.getButton(Input.Key.RIGHT)) {
                xa += speed;
                direction = false;
            }
            if(input.getButton(Input.Key.JUMP) && !input.getOldButton(Input.Key.JUMP) && onGround) {
                ya -= JUMPY;
                xa += (direction ? -JUMPX : JUMPX);
            }
            if(input.getButton(Input.Key.WAVE)) {
                level.cameraHolder = host;
                host.input = this.input;
                this.input = null;
                isControlled = false;
            }
        } else {
            if(Math.abs(rnd.nextInt()) % 6000 > 5760 && onGround) {
                ya -= JUMPY;
                xa += (direction ? -JUMPX : JUMPX);
            }
            if(Math.abs(rnd.nextInt()) % 9600 > 9540)
                direction = !direction;
            if(!level.isFree(x + xa - 1 + (direction ? 0 : w), y, 1, h) && level.isFree(x + xa - 1 + (!direction ? 0 : w), y, 1, h)) {
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
        int s = (onGround ? 1 : 2);
        frame = Math.abs((int)Math.round(x) / 3 % 4);
        if(direction)
            g.drawImage(Art.monsters1[frame][s], (int)x, (int)y, null);
        else
            g.drawImage(Art.monsters2[frame][s], (int)x, (int)y, null);
    }
    
    @Override
    public boolean shot(Entity e) {
        double s = Math.sqrt(e.xa*e.xa + e.ya*e.ya);
        for(int i = 0; i < 10; ++i)
            level.add(new Slime(x + w / 2, y + h / 2, -e.xa / s * 2.0 + (double)((rnd.nextInt() % 10)) / 10, -e.ya / s * 2.0 + (double)((rnd.nextInt() % 10)) / 10));
        xa += e.xa * 0.2;
        ya += e.ya * 0.2;
        return true;
    }
    
    @Override
    public boolean catchMind(Entity e, Input input) {
        host = e;
        this.input = input;
        level.cameraHolder = this;
        isControlled = true;
        return true;
    }
}