package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Movable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Particle implements Movable {
    public static int sequence = 1;

    @Getter @Setter
    private int id;
    @Getter @Setter
    private double radius;
    @Getter @Setter
    private double mass;
    @Getter @Setter
    private Point position;
    @Getter @Setter
    private Velocity velocity;

    public Particle(double radius, double mass, Point position, Velocity velocity) {
        this.id = sequence++;
        this.radius = radius;
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
    }

    public Particle(int id, double radius, double mass, Point position, Velocity velocity) {
        this.id = id;
        this.radius = radius;
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
    }

    public Particle(double radius, double mass) {
        this(radius, mass, new Point(0, 0), new Velocity(0, 0));
    }

    public double distanceTo(Particle particle) {
        return this.position.distanceTo(particle.position) - this.radius - particle.radius;
    }

    @Override
    public void move(double dt) {
        this.position.setX(this.position.getX() + this.velocity.getXSpeed() * dt);
        this.position.setY(this.position.getY() + this.velocity.getYSpeed() * dt);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s", this.id, this.position.getX(), this.position.getY(), this.velocity.getSpeed(), this.velocity.getAngle());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
