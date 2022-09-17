package ar.edu.itba.ss.models.events;

import lombok.Getter;

public enum Wall {
    LEFT(0),
    RIGHT(1),
    TOP(1),
    BOTTOM(0),
    SLOT(0.5);

    @Getter
    private double position;

    Wall(double position) {
        this.position = position;
    }

    public double setPosition(double length) {
        return this.position *= length;
    }
}
