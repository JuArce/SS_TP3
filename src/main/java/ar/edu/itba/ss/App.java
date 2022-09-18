package ar.edu.itba.ss;

import ar.edu.itba.ss.interfaces.Exporter;
import ar.edu.itba.ss.models.EventDriven;
import ar.edu.itba.ss.models.Grid;
import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.tools.CsvExporter;
import ar.edu.itba.ss.tools.ParticleReader;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        final int iterations = 500;

        String particlePath = args[0];
        String positionPath = args[1];
        String outputFilename = args.length < 3 ? "output.csv" : args[2];

        Instant start = Instant.now();

        ParticleReader particleReader = new ParticleReader(particlePath, positionPath);
        List<Particle> particles = new ArrayList<>();
        var enclosure = particleReader.read(particles);

        Grid grid = new Grid(particles, enclosure.width(), enclosure.height(), enclosure.slot());

        Exporter exporter = new CsvExporter(outputFilename);
        exporter.open();

        EventDriven eventDriven = new EventDriven(iterations, grid, exporter);
        eventDriven.simulate();

        exporter.close();

        Instant end = Instant.now();
        System.out.println("Simulation: " + Duration.between(start, end));
    }
}
