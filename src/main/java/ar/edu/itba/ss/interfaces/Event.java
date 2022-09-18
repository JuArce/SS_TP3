package ar.edu.itba.ss.interfaces;

import ar.edu.itba.ss.models.Particle;

import java.util.List;

public interface Event extends Comparable<Event> {

    List<Particle> getParticles();

    void collide();

    double getTc();

    void setTc(double tc);
}
