package ar.edu.itba.ss.models.events;

import ar.edu.itba.ss.interfaces.Event;
import ar.edu.itba.ss.models.Particle;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;
import java.util.Objects;

public class WallEvent implements Event {
    private final Particle particle;
    private final Wall wall;
    @Getter @Setter
    private double tc;

    public WallEvent(Particle particle, Wall wall, double tc) {
        this.particle = particle;
        this.wall = wall;
        this.tc = tc;
    }

    public WallEvent(Particle particle, Wall wall) {
        this.particle = particle;
        this.wall = wall;
    }

    @Override
    public void collide() {
        this.particle.collide(this.wall);
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT ,"WC %s %s %g", particle.getId(), wall, tc);
    }

    @Override
    public int compareTo(Event o) {
        return Double.compare(tc, o.getTc());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WallEvent wallEvent = (WallEvent) o;
        return particle.equals(wallEvent.particle) && wall == wallEvent.wall;
    }

    @Override
    public int hashCode() {
        return Objects.hash(particle, wall);
    }
}
