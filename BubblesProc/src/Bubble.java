import processing.core.*;


public class Bubble {

    Main p;
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

    public Bubble(Main p) {
        this.p = p;
        this.mass = p.abs(p.randomGaussian() * p.width/12) + p.width/34;


        do {
            this.position = new PVector( p.random(radius(), p.width-radius()), p.random(radius(), p.height-radius()) );
        } while (isOverlappingOtherBubbles());


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

        // Movement
        velocity.add(acceleration);
        velocity.limit(3);
        position.add(velocity);
        acceleration.set(0, 0);

        // Simple friction
        velocity.mult(0.95f);

        if (isOverlappingOtherBubbles()) {
            velocity.mult(-7f);
        }

        // Bouncy wall collision
        if (position.x - radius() < 0) velocity.x = p.abs(velocity.x) * 3f;
        if (position.x + radius() > p.width) velocity.x = -p.abs(velocity.x) * 3f;
        if (position.y - radius() < 0) velocity.y = p.abs(velocity.y) * 3f;
        if (position.y + radius() > p.height) velocity.y = -p.abs(velocity.y) * 3f;
    }

    Boolean isOverlappingOtherBubbles() {
        for (Bubble b : p.bubbles) {
            if (isOverlapping(this, b)) return true;
        }
        return false;
    }

    public static Boolean isOverlapping(Bubble b1, Bubble b2) {
        if (b1 == b2) return false;
        float minDistance = b1.radius() + b2.radius();
        return minDistance > PVector.dist(b1.position, b2.position);
    }

    public void draw() {
        p.fill(color, 50f);
        p.stroke(color);
        p.strokeWeight(1);
        if (isOverlappingOtherBubbles()) p.strokeWeight(10);
        p.ellipse(position.x, position.y, diameter(), diameter());

        if (!text.isEmpty()) {
            p.fill(color);
            p.textSize(diameter() / (text.length() / 1.5f) );
            p.textAlign(p.CENTER, p.CENTER);
            p.text(text, position.x, position.y);
        }

    }

    public String toString() {
        return text + " x:" + position.x + " y:" + position.y;
    }
}