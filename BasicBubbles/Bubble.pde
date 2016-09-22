class Bubble {
  
  PVector pos;
  PVector vel = new PVector();
  PVector acc = new PVector();
  
  float mass;
  color col;
  
  double oscSpd;
  float oscTheta = 0;
  
  String text = "";
  
  Bubble() {
    mass = abs(randomGaussian() * width/12) + width/34;
    
    do {
      pos = new PVector( random(rad(), width-rad()), 
                         random(rad(), height-rad()) );
    } while (overlapping());
    
    col = color( random(0,255), random(0,255), random(100, 175) );
    oscSpd = random(0.0001, 0.15);
  }
  
  void update() {
    // Oscilation / floating / jumping
    oscTheta += oscSpd;
    float oscMove = sin(oscTheta) * 0.5;
    pos.y += oscMove;
    
    // Generall movement
    vel.add(acc);
    vel.limit(3);
    pos.add(vel);
    acc.set(0,0);
    
    // Simple friction
    vel.mult(0.95);
    
    // Bounce of other bubbles
    if (overlapping()) vel.mult(-7);
    
    // Bouncy wall collision
    if      (pos.x - rad() < 0)      vel.x =  abs(vel.x) * 3;
    else if (pos.x + rad() > width)  vel.x = -abs(vel.x) * 3;
    if      (pos.y - rad() < 0)      vel.y =  abs(vel.y) * 3;
    else if (pos.y + rad() > height) vel.y = -abs(vel.y) * 3;
  }
  
  void draw() {
    fill(col, 50);
    stroke(col);
    strokeWeight(1);
    if (overlapping()) strokeWeight(10);
    ellipse(pos.x, pos.y, dia(), dia());
    
    if (!text.isEmpty()) {
      fill(col);
      textSize(dia() / (text.length() / 1.5) );
      textAlign(CENTER, CENTER);
      text(text, pos.x, pos.y);
    }
  }
  
  float rad() { return mass / 2; }
  float dia() { return mass; }
  
  void applyForce(PVector force) {
    PVector forceDivByMass = new PVector();
    PVector.div(force, mass, forceDivByMass);
    acc.add(forceDivByMass);
  }
  
  Boolean overlapping() {
    for (Bubble b : bubbles) {
      if (this == b) continue;
      float minDist = this.rad() + b.rad();
      if (minDist > pos.dist(b.pos)) return true;
    }
    return false;
  }
  
}