import processing.core.*;

import java.util.ArrayList;
import java.util.List;

public class Main extends PApplet {

    List<Bubble> bubbles;
    PVector wind;

    String[] words = { "snill",
                        "prinsesse",
                        "brannmann",
                        ":-)",
                        "hjelpsom",
                        "kul",
                        "glad",
                        "hoppe",
                        "gravemaskin",
                        "malepensel"
                     };

    public void settings() {
        //size(800, 500);
        fullScreen();
    }

    public void setup() {
        noLoop();
        colorMode(HSB);
        noStroke();
        bubbles = new ArrayList<>();
        for (String w : words) {
            Bubble b = new Bubble(this);
            b.text = w;
            bubbles.add(b);

        }
        wind = new PVector(20f, 0);
        loop();
    }

    public void draw() {
        wind.x = random(randomGaussian() * 40);
        background(100, 50, 200);

        for (Bubble b : bubbles) {
            b.applyForce(wind);
            b.update();
            b.draw();
        }

    }

}
