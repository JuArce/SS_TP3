package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Exporter;

public class EventDriven {
    private final Grid grid;
    private final Exporter exporter;

    public EventDriven(Grid grid, Exporter exporter) {
        this.grid = grid;
        this.exporter = exporter;
    }

    public void simulate() {
        // TODO
    }
}
