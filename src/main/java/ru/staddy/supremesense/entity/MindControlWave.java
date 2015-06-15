package ru.staddy.supremesense.entity;

import java.util.List;
import ru.staddy.supremesense.Input;

public class MindControlWave extends Wave {

    public MindControlWave(Entity source, double x, double y, double xa, double ya) {
        super(source, x, y, xa, ya);
        this.input = source.input;
    }
    
    @Override
    public void tick() {
        super.tick();
        List<Entity> entities = level.getEntities((int) x, (int) y, w, h);
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (source==e) continue;

            if (e.catchMind(source, input)) {
                source.input = null;
                remove();
                break;
            }
        }
    }
}
