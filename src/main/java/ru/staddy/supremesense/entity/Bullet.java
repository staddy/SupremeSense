package ru.staddy.supremesense.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import ru.staddy.supremesense.level.Camera;

public class Bullet extends Entity {
    public Entity source;
    private int tick = 0;

    public Bullet(Entity source, double x, double y, double xa, double ya) {
        this.source = source;
        this.x = x;
        this.y = y;
        this.w = 1;
        this.h = 1;
        this.xa = xa + (random.nextDouble() - random.nextDouble()) * 0.1;
        this.ya = ya + (random.nextDouble() - random.nextDouble()) * 0.1;
    }

    public void tick() {
        tick++;
        tryMove(xa, ya);

        List<Entity> entities = level.getEntities((int) x, (int) y, w, h);
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (source==e) continue;

            if (e.shot(this)) {
                remove();
            }
        }
    }

    protected void hitWall(double xa, double ya) {
        remove();
    }

    public void render(Graphics g, Camera camera) {
        if (tick % 2 == 0) {
            g.setColor(Color.YELLOW);
            int x1 = (int) (x + w / 2 - xa * 3);
            int y1 = (int) (y + h / 2 - ya * 3);
            int x2 = (int) (x + w / 2);
            int y2 = (int) (y + h / 2);

            g.drawLine(x1, y1, x2, y2);
            g.setColor(Color.WHITE);

            x1 = (int) (x + w / 2 - xa);
            y1 = (int) (y + h / 2 - ya);
            x2 = (int) (x + w / 2 + xa);
            y2 = (int) (y + h / 2 + ya);

            g.drawLine(x1, y1, x2, y2);
        } else {
            g.setColor(Color.YELLOW);
            int x1 = (int) (x + w / 2 - xa);
            int y1 = (int) (y + h / 2 - ya);
            int x2 = (int) (x + w / 2 + xa);
            int y2 = (int) (y + h / 2 + ya);

            g.drawLine(x1, y1, x2, y2);
        }
    }
}
