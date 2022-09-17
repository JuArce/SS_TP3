package ar.edu.itba.ss.models;

import lombok.Getter;

import java.util.List;

public class Grid {
    @Getter
    private final List<Particle> particles;
    private final double width;
    private final double height;
    private final double slot;

    public Grid(List<Particle> particles, double width, double height, double slot) {
        this.particles = particles;
        this.width = width;
        this.height = height;
        this.slot = slot;
    }

    public List<Double> getWallCollisions(Particle particle) {
        // TODO
        return null;
    }

    public List<Double> getParticleCollisions(Particle particle) {
        // TODO
        return null;
    }

    public void update() {
        // TODO
    }
}
