package ar.edu.itba.ss.models.events;

import ar.edu.itba.ss.interfaces.Event;
import ar.edu.itba.ss.models.Particle;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;
import java.util.Objects;

public class ParticlesEvent implements Event {
    private final Particle particle1;
    private final Particle particle2;
    @Getter @Setter
    private double tc;

    public ParticlesEvent(Particle particle1, Particle particle2, double tc) {
        this.particle1 = particle1;
        this.particle2 = particle2;
        this.tc = tc;
    }

    public ParticlesEvent(Particle particle1, Particle particle2) {
        this.particle1 = particle1;
        this.particle2 = particle2;
    }

    @Override
    public void collide() {
        this.particle1.collide(this.particle2);
    }

    @Override
    public double getImpulse() {
        return 0;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "PC %s %s %g", particle1.getId(), particle2.getId(), tc);
    }

    @Override
    public int compareTo(Event o) {
        return Double.compare(tc, o.getTc());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticlesEvent that = (ParticlesEvent) o;
        return (this.particle1.equals(that.particle1) && this.particle2.equals(that.particle2)) ||
                (this.particle1.equals(that.particle2) && this.particle2.equals(that.particle1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.max(particle1.hashCode(), particle2.hashCode()), Math.min(particle1.hashCode(), particle2.hashCode()));
    }
}
