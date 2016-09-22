import processing.core.*;

import java.util.ArrayList;
import java.util.List;

public class Main extends PApplet {

    public List<Bubble> bubbles;
    PVector wind;
    float windNoiseOffset;

    String[] words = { "snill",
                        "prinsesse",
                        "brannmann",
                        ":-)",
                        "hjelpsom",
                        "kul",
                        "glad",
                        "hoppe",
                        "gravemaskin"
                     };
    String writingBuffer = "";

    int bg;


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
        windNoiseOffset += 0.01;
        wind.x = ( noise(windNoiseOffset) - 0.5f ) * 80;
        //wind.x = random(-10, 10);
        background(100, 50, 200);

        fill(0, 255, 150);
        noStroke();
        float sx = (width / 2) + wind.x;
        float sw = abs(wind.x);
        rect(sx, height-10, sw, 10);

        for (Bubble b : bubbles) {
            b.applyForce(wind);
            b.update();
            b.draw();
        }

        fill(255);
        textSize(30f);
        textAlign(CENTER, CENTER);
        text(writingBuffer, width/2f, 20f);
    }

    public void keyTyped() {
        if ((key == ENTER || key == RETURN)) {
            Bubble b = new Bubble(this);
            b.text = writingBuffer;
            bubbles.add(b);
            writingBuffer = "";
        }
        else if (key == BACKSPACE) {
            try {
                writingBuffer = writingBuffer.substring(0, writingBuffer.length()-1);
            }
            catch (StringIndexOutOfBoundsException e) {}
        }
        else writingBuffer += key;
    }

}
