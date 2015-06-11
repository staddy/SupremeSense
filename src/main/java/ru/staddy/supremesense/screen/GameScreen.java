package ru.staddy.supremesense.screen;

import java.awt.Graphics;
import java.util.ArrayList;
import ru.staddy.supremesense.Art;
import ru.staddy.supremesense.Input;
import ru.staddy.supremesense.SupremeSense;
import ru.staddy.supremesense.entity.Entity;
import ru.staddy.supremesense.entity.Player;
import ru.staddy.supremesense.level.Camera;
import ru.staddy.supremesense.level.Level;

public class GameScreen extends Screen {
    public static final int MAX_HATS = 7;
    
    private static final boolean DEBUG_MODE = false;
    private int xLevel = DEBUG_MODE?8:0;
    private int yLevel = DEBUG_MODE?4:0;

    Level level;
    private Camera camera = new Camera(SupremeSense.GAME_WIDTH, SupremeSense.GAME_HEIGHT);

    public boolean mayRespawn = false;
    private int gunLevel = DEBUG_MODE?2:0;
    private int hatCount = 1;

    public GameScreen(ArrayList<Input> inputs) {
        ArrayList<Entity> players = new ArrayList<Entity>();
        this.inputs = inputs;
        for(Entity p : players) {
            p.setInput(this.inputs.get(players.indexOf(p)));
        }
        players.add(new Player(0, 0));
        level = new Level(this, 32, 24, players.get(0), players);
    }

    public void tick() {
        level.tick();
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
