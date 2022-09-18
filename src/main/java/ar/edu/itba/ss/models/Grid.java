package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Event;
import ar.edu.itba.ss.models.events.ParticlesEvent;
import ar.edu.itba.ss.models.events.Wall;
import ar.edu.itba.ss.models.events.WallEvent;
import lombok.Getter;

import java.util.*;

public class Grid {
    @Getter
    private final Set<Particle> particles;
    private final double width;
    private final double height;
    private final double slot;

    private Set<Event> computedCollisions = new HashSet<>();

    private final Particle middleTopWall;
    private final Particle middleBottomWall;

    public Grid(List<Particle> particles, double width, double height, double slot) {
        this.particles = new HashSet<>(particles);
        this.width = width;
        this.height = height;
        this.slot = slot;
        setWallPositions(height, width);
        this.middleTopWall = new Particle(0, Double.POSITIVE_INFINITY, new Point(width / 2, (height + slot) / 2), new Velocity(0, 0));
        this.middleBottomWall = new Particle(0, Double.POSITIVE_INFINITY, new Point(width / 2, (height - slot) / 2), new Velocity(0, 0));
    }

    private void setWallPositions(double height, double width) {
        Arrays.stream(Wall.values()).forEach(wall -> {
            switch (wall) {
                case TOP -> wall.setPosition(height);
                case BOTTOM, LEFT -> wall.setPosition(0);
                case RIGHT, MIDDLE -> wall.setPosition(width);
            }
        });
    }

    private void computeWallCollisions(Particle particle) {
        final Event xEvent = getMiddleWallCollision(particle);
        computedCollisions.add(xEvent);

        Wall wall;
        if (particle.getVelocity().getYSpeed() > 0) {
            wall = Wall.TOP;
        } else {
            wall = Wall.BOTTOM;
        }

        final Event yEvent = new WallEvent(particle, wall, particle.getCollisionTime(wall));
        computedCollisions.add(yEvent);
    }

    private Event getMiddleWallCollision(Particle particle) {
        if(particle.getId() == 43) {
            System.out.println(43);
        }
        if (particle.getVelocity().getXSpeed() < 0 && particle.getPosition().getX() + particle.getRadius() <= Wall.MIDDLE.getPosition()) {
            return new WallEvent(particle, Wall.LEFT, particle.getCollisionTime(Wall.LEFT));
        } else if (particle.getVelocity().getXSpeed() > 0 && particle.getPosition().getX() - particle.getRadius() >= Wall.MIDDLE.getPosition()) {
            return new WallEvent(particle, Wall.RIGHT, particle.getCollisionTime(Wall.RIGHT));
        }

        double tcTop = particle.getCollisionTime(middleTopWall);
        double tcBot = particle.getCollisionTime(middleBottomWall);
        double tc = Math.min(tcTop, tcBot);
        if (tc != Double.POSITIVE_INFINITY) {
            return new ParticlesEvent(particle, tcTop < tcBot ? middleTopWall : middleBottomWall, tc);
        }

        tc = particle.getCollisionTime(Wall.MIDDLE);
        if (particle.computeY(tc) < (this.height - this.slot) / 2 || particle.computeY(tc) > (this.height + this.slot) / 2) {
            return new WallEvent(particle, Wall.MIDDLE, tc);
        }
        return particle.getVelocity().getXSpeed() > 0 ?
                new WallEvent(particle, Wall.RIGHT, particle.getCollisionTime(Wall.RIGHT)) :
                new WallEvent(particle, Wall.LEFT, particle.getCollisionTime(Wall.LEFT));
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
        if (event.getTc() < 0) {
            System.out.println("Negative time");
        }

        this.particles
                .forEach(particle -> particle.move(event.getTc()));

        event.collide();
        this.computedCollisions.remove(event);

        this.computedCollisions = new HashSet<>();
    }
}
