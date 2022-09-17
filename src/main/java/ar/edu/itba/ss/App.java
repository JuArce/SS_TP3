package ar.edu.itba.ss;

import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.tools.ParticleReader;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        final double dt = 1;
        final int iterations = 500;

        String particlePath = args[0];
        String positionPath = args[1];
        String outputFilename = args.length < 3 ? "output.csv" : args[2];

        Instant start = Instant.now();

        ParticleReader particleReader = new ParticleReader(particlePath, positionPath);
        List<Particle> particles = new ArrayList<>();
        var l = particleReader.read(particles);

        Instant end = Instant.now();
        System.out.println("Simulation: " + Duration.between(start, end));
    }
}
