package ru.staddy.supremesense.screen;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import ru.staddy.supremesense.Art;
import ru.staddy.supremesense.Input;
import ru.staddy.supremesense.SupremeSense;
import ru.staddy.supremesense.entity.Crawler;
import ru.staddy.supremesense.entity.Entity;
import ru.staddy.supremesense.entity.Locust;
import ru.staddy.supremesense.entity.Player;
import ru.staddy.supremesense.level.Camera;
import ru.staddy.supremesense.level.Level;

public class GameScreen extends Screen {
    
    private static final boolean DEBUG_MODE = false;
    private int xLevel = DEBUG_MODE?8:0;
    private int yLevel = DEBUG_MODE?4:0;
    
    private String levels[] = {
        "/res/level",
        "",
        ""
    };
    
    private ArrayList<Entity> players;

    Level level;
    private Camera camera = new Camera(SupremeSense.GAME_WIDTH, SupremeSense.GAME_HEIGHT);

    public boolean mayRespawn = false;

    public GameScreen(ArrayList<Input> inputs) throws IOException {
        players = new ArrayList<>();
        players.add(new Player(20, 20));
        this.inputs = inputs;
        for(Entity p : players) {
            p.setInput(this.inputs.get(players.indexOf(p)));
        }
        changeLevel(0, 20, 20);
        level.add(new Crawler(15, 10, true));
        level.add(new Locust(15, 10, true));
    }

    public void tick() {
        level.tick();
    }
    
    public void changeLevel(int i, int x, int y) throws IOException {
        level = new Level(this, levels[i], players.get(0), players);
        players.get(0).x = x;
        players.get(0).y = y;
    }

    public void render(Graphics g) {
        g.drawImage(Art.bg, -xLevel*160, -yLevel*120, null);
        level.render(g, camera);
        if (mayRespawn) {
            String msg = "PRESS X TO TRY AGAIN";
            drawString(msg, g, 160 - msg.length() * 3, 120 - 3);
        }
    }
}
