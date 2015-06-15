package ru.staddy.supremesense.entity;

import java.awt.Color;
import java.awt.Graphics;
import ru.staddy.supremesense.level.Camera;

public class Wave extends Entity {
    public Entity source;

    public Wave(Entity source, double x, double y, double xa, double ya) {
        this.source = source;
        this.x = x;
        this.y = y;
        this.w = 4;
        this.h = 4;
        this.xa = xa;
        this.ya = ya;
    }

    @Override
    public void tick() {
        x += xa;
        y += ya;
    }

    @Override
    protected void hitWall(double xa, double ya) {
    }

    @Override
    public void render(Graphics g, Camera camera) {
        g.setColor(Color.YELLOW);
        g.drawRect((int)x, (int)y, w, h);
    }
}
