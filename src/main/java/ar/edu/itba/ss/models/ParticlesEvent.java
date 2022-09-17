package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Event;
import lombok.Getter;

public class ParticlesEvent implements Event {
    private final Particle particle1;
    private final Particle particle2;
    @Getter
    private final double tc;

    public ParticlesEvent(Particle particle1, Particle particle2, double tc) {
        this.particle1 = particle1;
        this.particle2 = particle2;
        this.tc = tc;
    }

    @Override
    public int compareTo(Event o) {
        return Double.compare(tc, o.getTc());
    }
}
