package ar.edu.itba.ss.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Velocity {
//    private double speed;
//    private double angle;
    private double xSpeed;
    private double ySpeed;

    // Polar coordinates
    public Velocity(double speedX, double speedY) {
        this.xSpeed = speedX;
        this.ySpeed = speedY;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public double getModule() {
        return Math.sqrt(Math.pow(xSpeed, 2) + Math.pow(ySpeed, 2));
    }

    public double getAngle() {
        return Math.atan2(ySpeed, xSpeed);
    }

    @Override
    public String toString() {
        return "Velocity{" +
                "xSpeed=" + xSpeed +
                ", ySpeed=" + ySpeed +
                '}';
    }
}
