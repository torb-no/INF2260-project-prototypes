import java.util.ArrayList;
import processing.video.*;

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
  
  cropX = 30;
  cropY = 20;
  cropW = 40;
  cropH = 70;

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
  
  // Draw camera image
  if (camVisible) {
    if (cam.available()) cam.read();
    //crop = cam.get(cam.width/3, cam.height/3, cam.width/3, cam.height/3);
    
    imageMode(CORNER);
    image(cam, 0, 0, prevW, prevH);
    
    noFill();
    stroke(255);
    strokeWeight(2);
    rect(prevW/100*cropX, prevH/100*cropY, prevW/100*cropW, prevH/100*cropH);
    //rect(width - prevW + cropW, 0, prevW
  }
  
}

void keyTyped() {
  if (key == ENTER || key == RETURN) {
    Bubble b = new Bubble();
    
    b.setImg(cam.get((cam.width/100) * cropX, (cam.height/100) * cropY, (cam.width/100) * cropW, (cam.height/100) * cropH));
    bubbles.add(b);
  }
  else if (key == ' ') {
    camVisible = !camVisible;
  }
}