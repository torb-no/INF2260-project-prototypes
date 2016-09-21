import processing.core.*;


public class Bubble {

    PApplet p;
    PVector position,
            velocity,
            acceleration;
    float mass;
    int color;

    float radius() { return mass / 2; }
    float diameter() { return mass;  }

    double oscillationSpeed;
    double oscillateTheta = 0;

    public String text = "";

    public Bubble(PApplet p) {
        this.p = p;
        this.mass = p.random(50, 400);
        this.position = new PVector(p.random(radius(), p.width-radius() ), p.random(radius(), p.height-radius() ));

        this.acceleration = new PVector();
        this.velocity = new PVector();
        this.color = p.color(p.random(0,255), p.random(0,255), p.random(100, 255));
        this.oscillationSpeed = p.random(0.0001f, 0.15f);

    }

    public void applyForce(PVector force) {
        PVector forceDivByMass = new PVector();
        PVector.div(force, mass, forceDivByMass);
        acceleration.add(forceDivByMass);
    }

    public void update() {

        // Oscilation / jumping
        oscillateTheta += oscillationSpeed;
        float oscilationMovement = ( (float) Math.sin(oscillateTheta) * 0.5f);
        position.y += oscilationMovement;

        // Friction
        PVector friction = velocity.copy();
        friction.mult(-1);
        friction.normalize();
        friction.mult(0.01f);
        applyForce(friction);

        // Movement
        velocity.add(acceleration);
        velocity.limit(3);
        position.add(velocity);
        acceleration.set(0, 0);

        if (position.x - radius() < 0 || position.x + radius() > p.width) velocity.x *= -1;
        if (position.y - radius() < 0 || position.y + radius() > p.width) velocity.y *= -1;

    }

    public void draw() {
        p.fill(color);
        p.ellipse(position.x, position.y, diameter(), diameter());

        if (!text.isEmpty()) {
            p.fill(255);
            p.textSize(diameter() / (text.length() / 1.5f) );
            p.textAlign(p.CENTER, p.CENTER);
            p.text(text, position.x, position.y);
        }

    }

}