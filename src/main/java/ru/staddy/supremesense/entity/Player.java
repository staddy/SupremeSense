package ru.staddy.supremesense.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import ru.staddy.supremesense.Art;
import ru.staddy.supremesense.Input;
import ru.staddy.supremesense.level.Camera;
import ru.staddy.supremesense.level.Level;

public class Player extends Entity {
    private int dir = 1;
    private int yAim = 0;
    private int frame = 0;
    
    int cx = 0, cy = 0;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        w = 8;
        h = 18;
        bounce = 0;
    }

    public void render(Graphics g, Camera camera) {
        g.setColor(Color.GREEN);
        g.fillRect((int)x, (int)y, w, h);
        cx = camera.x;
        cy = camera.y;
        
        //int stepFrame = frame / 4 % 4;

        /*BufferedImage[][] sheet = dir == 1 ? Art.player1 : Art.player2;
        if (!onGround) {
            int yya = (int) Math.round(-ya);
            stepFrame = 4;
            if (yya < -1) stepFrame = 5;
            yp += yya;
        }
        g.drawImage(sheet[3 + stepFrame][hatCount > 0 ? 0 : 1], xp, yp, null);*/
    }

    public void tick() {
        double speed = 0.4;
        double aimAngle = -0.2;
        yAim = 0;
        if (input != null && input.getButton(Input.Key.UP)) {
            aimAngle -= 0.8;
            yAim--;
        }
        if (input != null && input.getButton(Input.Key.DOWN)) {
            aimAngle += 0.8;
            yAim++;
        }
        boolean walk = false;
        if (input != null && input.getButton(Input.Key.LEFT)) {
            walk = true;
            xa -= speed;
            dir = -1;
        }
        if (input != null && input.getButton(Input.Key.RIGHT)) {
            walk = true;
            xa += speed;
            dir = 1;
        }
        if (input != null && input.getButton(Input.Key.SHOOT) && !input.getOldButton(Input.Key.SHOOT)) {
            double k = ((double)(cy + input.y - y) / (cx + input.x - x));
            double bxa = Math.sqrt(64.0 / (1 + k*k)) * (cx + input.x - x > 0 ? 1 : -1);
            double bya = bxa * k;
            level.add(new Bullet(this, x, y, bxa, bya));
        }
        if (input != null && input.getButton(Input.Key.WAVE) && !input.getOldButton(Input.Key.WAVE)) {
            double k = ((double)(cy + input.y - y) / (cx + input.x - x));
            double bxa = Math.sqrt(8.0 / (1 + k*k)) * (cx + input.x - x > 0 ? 1 : -1);
            double bya = bxa * k;
            level.add(new MindControlWave(this, x, y, bxa, bya));
        }
        if (walk) frame++;
        else frame = 0;
        if (input != null && input.getButton(Input.Key.JUMP) && !input.getOldButton(Input.Key.JUMP) && onGround) {
            ya -= 5;// + Math.abs(xa) * 0.5;
        }

        tryMove(xa, ya);

        xa *= 0.7;
        /*if (ya < 0 && input.getButton(Input.Key.JUMP)) {
            ya *= 0.992;
            ya += Level.GRAVITY * 0.5;
        } else {*/
            ya *= Level.FRICTION;
            ya += Level.GRAVITY;
        //}
    }

    public void die() {
        if(!removed) 
            remove();
    }

    public void outOfBounds() {
    }
}
