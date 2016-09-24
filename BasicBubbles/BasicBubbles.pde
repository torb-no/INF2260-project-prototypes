import java.util.ArrayList;

ArrayList<Bubble> bubbles = new ArrayList();
PVector wind = new PVector();
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

void setup() {
  //size(800, 500);
  fullScreen();
  colorMode(HSB);
  
  for (String w : words) {
    Bubble b = new Bubble();
    b.text = w;
    bubbles.add(b);
  }
}

void draw() {
  background(100, 50, 200);
  
  // Wind
  windNoiseOffset += 0.01;
  wind.x = ( noise(windNoiseOffset) -0.5) * 80;
  fill(0, 255, 150);
  noStroke();
  float sx = (width / 2) + wind.x;
  float sw = abs(wind.x);
  rect(sx, height-10, sw, 10);
  
  // Bubbles
  for (Bubble b : bubbles) {
    b.applyForce(wind);
    b.update();
    b.draw();
  }
  
  // Render writing buffer
  fill(255);
  textSize(30);
  textAlign(CENTER, CENTER);
  text(writingBuffer, width/2, 20);
}

void keyTyped() {
  if (key == ENTER || key == RETURN) {
    Bubble b = new Bubble();
    b.text = writingBuffer;
    bubbles.add(b);
    writingBuffer = "";
  }
  else if (key == BACKSPACE) {
    try {
      writingBuffer = writingBuffer.substring(0, writingBuffer.length()-1); 
    }
    catch (StringIndexOutOfBoundsException e) {
      // No F’s given
    }
  }
  else {
    writingBuffer += key; 
  }
}