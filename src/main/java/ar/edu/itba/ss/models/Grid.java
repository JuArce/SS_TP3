package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Event;
import ar.edu.itba.ss.models.events.ParticlesEvent;
import ar.edu.itba.ss.models.events.Wall;
import ar.edu.itba.ss.models.events.WallEvent;
import lombok.Getter;

import java.util.*;

public class Grid {
    @Getter
    private final List<Particle> particles;
    private final double width;
    private final double height;
    private final double slot;

    private Set<Event> computedCollisions = new HashSet<>();

    public Grid(List<Particle> particles, double width, double height, double slot) {
        this.particles = particles;
        this.width = width;
        this.height = height;
        this.slot = slot;
        setWallPositions(height, width);
    }

    private void setWallPositions(double height, double width) {
        Arrays.stream(Wall.values()).forEach(wall -> {
            switch (wall) {
                case TOP -> wall.setPosition(height);
                case BOTTOM, LEFT -> wall.setPosition(0);
                case RIGHT -> wall.setPosition(width);
                case SLOT -> wall.setPosition(width / 2);
            }
        });
    }


    private void computeWallCollisions(Particle particle){
        Wall wall;

        if (particle.getVelocity().getXSpeed() > 0) {
            wall = Wall.RIGHT;
        } else {
            wall = Wall.LEFT;
        }

        Event xEvent = new WallEvent(particle, wall);
        if (!computedCollisions.contains(xEvent)) {
            xEvent.setTc(particle.getCollisionTime(wall));
            computedCollisions.add(xEvent);
        }

        if (particle.getVelocity().getYSpeed() > 0) {
            wall = Wall.TOP;
        } else {
            wall = Wall.BOTTOM;
        }

        Event yEvent = new WallEvent(particle, wall);
        if (!computedCollisions.contains(yEvent)) {
            yEvent.setTc(particle.getCollisionTime(wall));
            computedCollisions.add(yEvent);
        }
    }

    private void computeParticleCollisions(Particle particle) {
        this.particles.stream()
                .filter(p -> !p.equals(particle))
                .forEach(p -> {
                    Event event = new ParticlesEvent(particle, p);
                    if (!this.computedCollisions.contains(event)) {
                        event.setTc(particle.getCollisionTime(p));
                        this.computedCollisions.add(event);
                    }
                });
    }

    public void update() {
        this.particles.forEach(particle -> {
            computeWallCollisions(particle);
            computeParticleCollisions(particle);
        });

        Event event = this.computedCollisions.stream().min(Comparator.comparing(Event::getTc)).orElseThrow();
        if(event.getTc() < 0) {
            System.out.println("Negative time");
        }

        this.particles
                .forEach(particle -> particle.move(event.getTc()));

        event.collide();
        this.computedCollisions.remove(event);

        this.computedCollisions = new HashSet<>();
    }
}
