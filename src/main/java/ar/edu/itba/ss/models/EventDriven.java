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
        // TODO
        for (int i = 0; i < this.iterations; i++) {
            this.grid.update();
        }
    }
}
