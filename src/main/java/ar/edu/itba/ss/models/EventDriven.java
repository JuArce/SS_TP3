package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Exporter;

public class EventDriven {
    private final int iterations;
    private final Grid grid;
    private final Exporter exporter;

    public EventDriven(int iterations, Grid grid, Exporter exporter) {
        this.iterations = iterations;
        this.grid = grid;
        this.exporter = exporter;
    }

    public void simulate() {
        for (int i = 0; i < this.iterations; i++) {
            if(i % 100 == 0) {
                System.out.println(i);
            }
            this.exporter.export(i, this.grid.getParticles(), this.grid.getCurrentCollision());
            this.grid.update();
        }
    }
}
