package ar.edu.itba.ss.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Velocity {
    private double speed;
    private double angle;

    // Polar coordinates
    public Velocity(double speed, double angle) {
        this.speed = speed;
        this.angle = angle;
    }

    public double getXSpeed() {
        return this.speed * Math.cos(this.angle);
    }

    public void setXSpeed(double xSpeed) {
        this.speed = Math.sqrt(xSpeed * xSpeed + this.getYSpeed() * this.getYSpeed());
        this.angle = Math.atan2(this.getYSpeed(), xSpeed);
    }

    public double getYSpeed() {
        return this.speed * Math.sin(this.angle);
    }

    public void setYSpeed(double ySpeed) {
        this.speed = Math.sqrt(this.getXSpeed() * this.getXSpeed() + ySpeed * ySpeed);
        this.angle = Math.atan2(ySpeed, this.getXSpeed());
    }

    @Override
    public String toString() {
        return "Velocity{" +
                "speed=" + speed +
                ", angle=" + angle +
                '}';
    }
}
