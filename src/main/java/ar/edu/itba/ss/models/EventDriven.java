package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Exporter;

import java.util.List;

public class EventDriven {
    private final int iterations;
    private final Grid grid;
    private final List<Exporter> exporters;

    private Double tEq = null;
    private Double tFinal = null;
    private double t = 0;

    public EventDriven(int iterations, Grid grid, List<Exporter> exporters) {
        this.iterations = iterations;
        this.grid = grid;
        this.exporters = exporters;
    }

    public void simulate() {
        double impulse = 0;
        int i = 0;
        while((tFinal == null || t < tFinal) && i < iterations) {
            int finalI = i;
            this.exporters.forEach(e -> e.export(finalI, this.grid.getParticles(), this.grid.getCurrentCollision()));
            this.grid.update();

            t += grid.getCurrentCollision() != null ? grid.getCurrentCollision().getTc() : 0;
            double fP = this.grid.getFp();
            if (tEq == null && Math.abs(fP - 0.5) < 0.01) {
                tEq = t;
                tFinal = tEq * 1.5;
                System.out.println("tEq: " + tEq);
            }

            if(i % 100 == 0) {
                System.out.println(i + ": " + fP);
            }
            i++;

            if (tEq != null) {
                impulse += grid.getCurrentCollision() != null ? grid.getCurrentCollision().getImpulse() : 0;
            }
        }
        double force = impulse / (tFinal - tEq);
        double pressure = force / grid.getPerimeter();
        double avgEnergy = this.grid.getParticles().stream().mapToDouble(Particle::getKineticEnergy).sum() / this.grid.getParticles().size();
        System.out.println("Pressure: " + pressure + "\nAvg Energy: " + avgEnergy);
    }
}
