package ru.staddy.supremesense.screen;

import java.awt.*;
import java.util.ArrayList;
import ru.staddy.supremesense.Art;
import ru.staddy.supremesense.Input;

public class TitleScreen extends Screen {
    private int time = 0;

    // DEBUG
    String msg = "PRESS X TO WIN";
    
    public TitleScreen(ArrayList<Input> inputs) {
        this.inputs = inputs;
    }
    
    public void render(Graphics g) {
        int yOffs = 480 - time * 2;
        if (yOffs < 0) yOffs = 0;
        g.drawImage(Art.bg, 0, -yOffs / 2, null);
        g.drawImage(Art.titleScreen, 0, -yOffs, null);
        if (time > 240) {
            //drawAnimatedString(msg, g, 160 - msg.length() * 3, 140 - 3 - (int) (Math.abs(Math.sin(time * 0.1) * 10)));
            int rnd = random.nextInt(37);
            drawAnimatedString(msg, g, 160 - msg.length() * 3 - (((time % 5 + rnd) > 35) ? 2 : 0), 140 - 3 - (((time % 5 + rnd) > 35) ? 2 : 0));
        }
        if (time >=0) {
            String copyright = "COPYRIGHT STADDY 2015";
            drawString(copyright, g, 2, 240-6-2);
        }
    }

    public void tick() {
        time++;
        if (time > 240) {
            if (inputs.get(0).getButton(Input.Key.SHOOT) && !inputs.get(0).getOldButton(Input.Key.SHOOT)) {
                resetAnimation();
                msg = "What does victory mean to you?";
                //setScreen(new GameScreen());
                inputs.get(0).releaseAllKeys();
            }
        }
        if (time > 60*10) {
            //setScreen(new ExpositionScreen());
        }
    }
}
