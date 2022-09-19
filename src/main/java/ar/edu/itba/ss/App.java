package ar.edu.itba.ss;

import ar.edu.itba.ss.interfaces.Exporter;
import ar.edu.itba.ss.models.EventDriven;
import ar.edu.itba.ss.models.Grid;
import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.tools.CsvExporter;
import ar.edu.itba.ss.tools.FpExporter;
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
        final int iterations = 30000;

        String particlePath = args[0];
        String positionPath = args[1];
        String outputFilename = args.length < 3 ? "output.csv" : args[2];
        String fpFilename = args.length < 4 ? "output_fp.csv" : args[3];

        Instant start = Instant.now();

        ParticleReader particleReader = new ParticleReader(particlePath, positionPath);
        List<Particle> particles = new ArrayList<>();
        var enclosure = particleReader.read(particles);

        Grid grid = new Grid(particles, enclosure.width(), enclosure.height(), enclosure.slot());

        Exporter csvExporter = new CsvExporter(outputFilename);
        Exporter fpExporter = new FpExporter(fpFilename, enclosure.width());

        List<Exporter> exporters = List.of(csvExporter, fpExporter);
        exporters.forEach(Exporter::open);

        EventDriven eventDriven = new EventDriven(iterations, grid, exporters);
        eventDriven.simulate();

        exporters.forEach(Exporter::close);

        Instant end = Instant.now();
        System.out.println("Simulation: " + Duration.between(start, end));
    }
}
