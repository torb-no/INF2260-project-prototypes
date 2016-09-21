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
        this.mass = p.abs(p.randomGaussian() * 250) + 75;

        this.position = new PVector( p.random(radius(), p.width-radius()), p.random(radius(), p.height-radius()) );

        this.acceleration = new PVector();
        this.velocity = new PVector();
        this.color = p.color(p.random(0,255), p.random(0,255), p.random(100, 175));
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
/*
        PVector friction = velocity.copy();
        friction.mult(-200);
        friction.normalize();
        //friction.mult(0.1f);
        applyForce(friction);
*/

        // Movement
        velocity.add(acceleration);
        velocity.limit(3);
        position.add(velocity);
        acceleration.set(0, 0);

        // Simple friction
        velocity.mult(0.95f);

        // Bouncy wall collision
        if (position.x - radius() < 0) velocity.x = p.abs(velocity.x) * 3f;
        if (position.x + radius() > p.width) velocity.x = -p.abs(velocity.x) * 3f;

        if (position.y - radius() < 0) velocity.y = p.abs(velocity.y) * 3f;
        if (position.y + radius() > p.height) velocity.y = -p.abs(velocity.y) * 3f;

    }

    public void draw() {
        p.fill(color, 50f);
        p.stroke(color);
        p.strokeWeight(3);
        p.ellipse(position.x, position.y, diameter(), diameter());

        if (!text.isEmpty()) {
            p.fill(color);
            p.textSize(diameter() / (text.length() / 1.5f) );
            p.textAlign(p.CENTER, p.CENTER);
            p.text(text, position.x, position.y);
        }

    }

}