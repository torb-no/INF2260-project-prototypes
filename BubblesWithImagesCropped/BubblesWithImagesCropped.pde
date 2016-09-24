import java.util.ArrayList;
import processing.video.*;
import processing.sound.*;
SoundFile popSound;

ArrayList<Bubble> bubbles = new ArrayList();
PVector wind = new PVector();
float windNoiseOffset;

Capture cam;
PImage crop;
float prevW, prevH;
int cropX, cropY, cropW, cropH;
Boolean camVisible = true;

void setup() {
  //size(800, 500);
  fullScreen();
  colorMode(HSB);
  
  prevW = (width/100) * 20;
  prevH = (height/100) * 20;
  
  cropX = 33;
  cropY = 26;
  cropW = 43;
  cropH = 64;
  
  popSound = new SoundFile(this, "boble 1 (low pitch).wav");

  cam = new Capture(this, Capture.list()[1]);
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
  
  if (bubbles.size() > 12) {
    bubbles.remove(0);
  }
}

void keyTyped() {
  if ((key == ENTER || key == RETURN) && cam.available()) {
    cam.read();
    Bubble b = new Bubble();
    
    b.setImg(cam.get((cam.width/100) * cropX, (cam.height/100) * cropY, (cam.width/100) * cropW, (cam.height/100) * cropH));
    bubbles.add(b);
    popSound.play();
  }
  else if (key == ' ') {
    camVisible = !camVisible;
  }
  else if (key == 'd') {
    bubbles.remove(0); 
  }
  else if (key == BACKSPACE) {
    try {
      bubbles.remove(bubbles.size()-1); 
    }
    catch (IndexOutOfBoundsException e) {
     // no fs given 
    }
  }
}