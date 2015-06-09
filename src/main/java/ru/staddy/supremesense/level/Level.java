package ru.staddy.supremesense.level;

import java.awt.*;
import java.util.*;
import java.util.List;
import ru.staddy.supremesense.Input;
import ru.staddy.supremesense.SupremeSense;
import ru.staddy.supremesense.entity.Entity;
import ru.staddy.supremesense.screen.GameScreen;

public class Level {
    public static final double FRICTION = 0.99;
    public static final double GRAVITY = 0.10;
    public List<Entity> entities = new ArrayList<Entity>();
    public byte[] walls;
    public List<Entity>[] entityMap;
    private int width, height;
    
    public List<Entity> players;
    public Entity cameraHolder;
    
    public int xSpawn, ySpawn;
    private Random random = new Random(1000);
    private GameScreen screen;
    private int tick;
    private List<Entity> hits = new ArrayList<Entity>();

    public Level(GameScreen screen, int w, int h, Entity cameraHolder, ArrayList<Entity> players) {
        this.screen = screen;
        this.cameraHolder = cameraHolder;
        this.players = players;
        int[] pixels = new int[32 * 24];

        walls = new byte[w * h];
        entityMap = new ArrayList[w * h];
        this.width = w;
        this.height = h;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                entityMap[x + y * w] = new ArrayList<Entity>();
                byte wall = 0;
                walls[x + y * w] = wall;
            }
        }
    }

    public void add(Entity e) {
        entities.add(e);
        e.init(this);

        e.xSlot = (int) ((e.x + e.w / 2.0) / 10);
        e.ySlot = (int) ((e.y + e.h / 2.0) / 10);
        if (e.xSlot >= 0 && e.ySlot >= 0 && e.xSlot < width && e.ySlot < height) {
            entityMap[e.xSlot + e.ySlot * width].add(e);
        }
    }
    
    public void establishCamera(Camera camera) {
        camera.x = (int)(cameraHolder.x + cameraHolder.w / 2 - camera.width / 2);
        camera.y = (int)(cameraHolder.y + cameraHolder.h / 2 - camera.height / 2);
        int w = width * 10 * SupremeSense.SCREEN_SCALE;
        int h = height * 10 * SupremeSense.SCREEN_SCALE;
        if(camera.x < 0) camera.x = 0;
        else if(camera.x > (w - camera.width)) camera.x = (w - camera.width);
        if(camera.y < 0) camera.y = 0;
        else if(camera.y > (h - camera.height)) camera.y = (h - camera.height);
    }

    public void tick(ArrayList<Input> inputs) {
        tick++;
        for(Entity p : players) {
            p.setInput(inputs.get(players.indexOf(p)));
        }
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            int xSlotOld = e.xSlot;
            int ySlotOld = e.ySlot;
            if (!e.removed) e.tick();
            e.xSlot = (int) ((e.x + e.w / 2.0) / 10);
            e.ySlot = (int) ((e.y + e.h / 2.0) / 10);
            if (e.removed) {
                if (xSlotOld >= 0 && ySlotOld >= 0 && xSlotOld < width && ySlotOld < height) {
                    entityMap[xSlotOld + ySlotOld * width].remove(e);
                }
                entities.remove(i--);
            } else {
                if (e.xSlot != xSlotOld || e.ySlot != ySlotOld) {
                    if (xSlotOld >= 0 && ySlotOld >= 0 && xSlotOld < width && ySlotOld < height) {
                        entityMap[xSlotOld + ySlotOld * width].remove(e);
                    }
                    if (e.xSlot >= 0 && e.ySlot >= 0 && e.xSlot < width && e.ySlot < height) {
                        entityMap[e.xSlot + e.ySlot * width].add(e);
                    } else {
                        e.outOfBounds();
                    }

                }
            }
        }
    }

    public List<Entity> getEntities(double xc, double yc, double w, double h) {
        hits.clear();
        int r = 20;
        int x0 = (int) ((xc - r) / 10);
        int y0 = (int) ((yc - r) / 10);
        int x1 = (int) ((xc + w + r) / 10);
        int y1 = (int) ((yc + h + r) / 10);
        for (int x = x0; x <= x1; x++)
            for (int y = y0; y <= y1; y++) {
                if (x >= 0 && y >= 0 && x < width && y < height) {
                    List<Entity> es = entityMap[x + y * width];
                    for (int i = 0; i < es.size(); i++) {
                        Entity e = es.get(i);
                        double xx0 = e.x;
                        double yy0 = e.y;
                        double xx1 = e.x + e.w;
                        double yy1 = e.y + e.h;
                        if (xx0 > xc + w || yy0 > yc + h || xx1 < xc || yy1 < yc) continue;

                        hits.add(e);
                    }
                }
            }
        return hits;
    }

    public void render(Graphics g, Camera camera) {
        establishCamera(camera);
        g.translate(-camera.x, -camera.y);

        int xo = camera.x / 10;
        int yo = camera.y / 10;
        for (int x = xo; x <= xo + camera.width / 10; x++) {
            for (int y = yo; y <= yo + camera.height / 10; y++) {
                if (x >= 0 && y >= 0 && x < width && y < height) {
                    int ximg = 0;
                    int yimg = 0;
                    byte w = walls[x + y * width];
                    if (w == 0) yimg = 1;

                    //g.drawImage(Art.walls[ximg][yimg], x * 10, y * 10, null);
                }
            }
        }
        for (int i = entities.size() - 1; i >= 0; i--) {
            Entity e = entities.get(i);
            e.render(g, camera);
        }
    }

    public boolean isFree(Entity ee, double xc, double yc, int w, int h, double xa, double ya) {
        double e = 0.1;
        int x0 = (int) (xc / 10);
        int y0 = (int) (yc / 10);
        int x1 = (int) ((xc + w - e) / 10);
        int y1 = (int) ((yc + h - e) / 10);
        boolean ok = true;
        for (int x = x0; x <= x1; x++)
            for (int y = y0; y <= y1; y++) {
                if (x >= 0 && y >= 0 && x < width && y < height) {
                    byte ww = walls[x + y * width];
                    if (ww != 0) ok = false;
                    if (ww == 8) ok = true;
                    if (ww == 6) {
                        ee.xa += 0.12;
                    }
                    if (ww == 7) {
                        ee.xa -= 0.12;
                    }
                }
            }

        return ok;
    }
}
