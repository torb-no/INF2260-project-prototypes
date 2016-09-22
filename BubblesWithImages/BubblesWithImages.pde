import java.util.ArrayList;
import processing.video.*;

ArrayList<Bubble> bubbles = new ArrayList();
PVector wind = new PVector();
float windNoiseOffset;

Capture cam;
Boolean camVisible = true;

void setup() {
  size(800, 500);
  colorMode(HSB);

  cam = new Capture(this, Capture.list()[0]);
  cam.start();
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
  
  // Draw camera image
  if (camVisible) {
    if (cam.available()) cam.read();
    imageMode(CORNER);
    // push, scale, pop and weird position in order to flip camera
    pushMatrix();
    scale(-1, 1);
    image(cam, -width, 0, (width/10)*2, (height/10)*2);
    popMatrix();
  }
  
}

void keyTyped() {
  if (key == ENTER || key == RETURN) {
    Bubble b = new Bubble();
    b.setImg(cam);
    bubbles.add(b);
  }
  else if (key == ' ') {
    camVisible = !camVisible;
  }
}