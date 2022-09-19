package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Movable;
import ar.edu.itba.ss.models.events.Wall;
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

    public double getCollisionTime(Particle particle) {
        // delta_r = (delta_x, delta_y) = (x2 - x1, y2 - y1)
        double dx = particle.position.getX() - this.position.getX();
        double dy = particle.position.getY() - this.position.getY();

        // delta_v = (delta_vx, delta_vy) = (vx2 - vx1, vy2 - vy1)
        double dvx = particle.velocity.getXSpeed() - this.velocity.getXSpeed();
        double dvy = particle.velocity.getYSpeed() - this.velocity.getYSpeed();

        // sigma = r1 + r2
        double sigma = particle.radius + this.radius;

        // dr^2 = (delta_x)^2 + (delta_y)^2
        double drdr = Math.pow(dx, 2) + Math.pow(dy, 2);

        // dv^2 = (delta_vx)^2 + (delta_vy)^2
        double dvdv = Math.pow(dvx, 2) + Math.pow(dvy, 2);

        // drdv = (delta_x)(delta_vx) + (delta_y)(delta_vy)
        double dvdr = dx * dvx + dy * dvy;

        if (dvdr >= 0) {
            return Double.POSITIVE_INFINITY;
        }

        // d = dvdr^2 - dvdv(drdr - sigma^2)
        double d = Math.pow(dvdr, 2) - dvdv * (drdr - Math.pow(sigma, 2));

        if (d < 0) {
            return Double.POSITIVE_INFINITY;
        }

        return -(dvdr + Math.sqrt(d)) / dvdv;
    }

    public double getCollisionTime(Wall wall) {
        return switch (wall) {
            case LEFT -> (this.radius - this.position.getX()) / this.velocity.getXSpeed();
            case RIGHT -> (wall.getPosition() - this.radius - this.position.getX()) / this.velocity.getXSpeed();
            case TOP -> (wall.getPosition() - this.radius - this.position.getY()) / this.velocity.getYSpeed();
            case BOTTOM -> (this.radius - this.position.getY()) / this.velocity.getYSpeed();
            case MIDDLE -> (wall.getPosition() - (this.radius * Math.signum(this.velocity.getXSpeed())) - this.position.getX()) / this.velocity.getXSpeed();
        };
    }

    public void collide(Particle particle) {
        // delta_r = (delta_x, delta_y) = (x2 - x1, y2 - y1)
        double dx = particle.position.getX() - this.position.getX();
        double dy = particle.position.getY() - this.position.getY();

        // delta_v = (delta_vx, delta_vy) = (vx2 - vx1, vy2 - vy1)
        double dvx = particle.velocity.getXSpeed() - this.velocity.getXSpeed();
        double dvy = particle.velocity.getYSpeed() - this.velocity.getYSpeed();

        // sigma = r1 + r2
        double sigma = particle.radius + this.radius;

        // dvdr = (delta_x)(delta_vx) + (delta_y)(delta_vy)
        double dvdr = dx * dvx + dy * dvy;

        // J = 2 * mi mj * dvdr/sigma * (mi +mj)
        double J = (2 * particle.getMass() * this.getMass() * dvdr) / (sigma  * (particle.getMass() + this.getMass()));

        // Jx = J * (delta_x)/(sigma)
        double Jx = (J * dx) / sigma;

        // Jy = J * (delta_y)/(sigma)
        double Jy = (J * dy) / sigma;

        // vx1' = vx1 + Jx / mi
        this.velocity.setXSpeed(this.velocity.getXSpeed() + Jx / this.getMass());

        // vy1' = vy1 + Jy / mi
        this.velocity.setYSpeed(this.velocity.getYSpeed() + Jy / this.getMass());

        if (Double.compare(particle.getMass(), 100_000_000) == 0) {
            return;
        }
        // vx2' = vx2 - Jx / mj
        particle.velocity.setXSpeed(particle.velocity.getXSpeed() - Jx / particle.getMass());

        // vy2' = vy2 - Jy / mj
        particle.velocity.setYSpeed(particle.velocity.getYSpeed() - Jy / particle.getMass());
    }

    public void collide(Wall wall) {
        switch (wall) {
            case LEFT, RIGHT, MIDDLE -> this.velocity.setXSpeed(-this.velocity.getXSpeed());
            case TOP, BOTTOM -> this.velocity.setYSpeed(-this.velocity.getYSpeed());
        }
    }

    public double getKineticEnergy() {
        return 0.5 * this.mass * (Math.pow(this.velocity.getXSpeed(), 2) + Math.pow(this.velocity.getYSpeed(), 2));
    }

    @Override
    public void move(double dt) {
        this.position.setX(this.position.getX() + this.velocity.getXSpeed() * dt);
        this.position.setY(this.position.getY() + this.velocity.getYSpeed() * dt);
    }

    @Override
    public double computeY(double t) {
        return this.position.getY() + this.velocity.getYSpeed() * t;
    }

    @Override
    public double computeX(double t) {
        return this.position.getX() + this.velocity.getXSpeed() * t;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s", this.id, this.position.getX(), this.position.getY(), this.velocity.getXSpeed(), this.velocity.getYSpeed(), this.getKineticEnergy());
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
