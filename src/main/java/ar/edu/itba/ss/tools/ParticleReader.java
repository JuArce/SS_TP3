package ar.edu.itba.ss.tools;

import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.models.Point;
import ar.edu.itba.ss.models.Velocity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ParticleReader {
    private final File particleFile;
    private final File positionFile;


    public ParticleReader(String particlePath, String positionPath) {
        this.particleFile = new File(particlePath);
        this.positionFile = new File(positionPath);
    }

    public Enclosure read(List<Particle> particles) {
        Enclosure enclosure = readParticles(particles);
        readPositions(particles);
        return enclosure;
    }

    private Enclosure readParticles(List<Particle> particles) {
        int particleCount;
        List<String> lines = getLines(particleFile);

        particleCount = Integer.parseInt(lines.remove(0).trim());
        Enclosure enclosure = new Enclosure(Double.parseDouble(lines.remove(0).trim()),
                Double.parseDouble(lines.remove(0).trim()),
                Double.parseDouble(lines.remove(0).trim()));

        if (lines.size() != particleCount) {
            throw new IllegalArgumentException("Particle count and particle lines don't match");
        }

        lines.forEach(l -> {
            String[] s = l.trim().split(",");
            // radio | masa
            particles.add(new Particle(Double.parseDouble(s[0]), Double.parseDouble(s[1])));
        });
        return enclosure;
    }

    private void readPositions(List<Particle> particles) {
        List<String> lines = getLines(positionFile);

        Iterator<Particle> particleIterator = particles.iterator();

        lines.remove(0); // First element -> frame number

        lines.forEach(l -> {
            String[] s = l.trim().split(",");
            if (particleIterator.hasNext()) {
                Particle p = particleIterator.next();
                Point point = new Point(Double.parseDouble(s[0]), Double.parseDouble(s[1]));
                p.setPosition(point);
                Velocity velocity = new Velocity(Double.parseDouble(s[2]), Double.parseDouble(s[3]));
                p.setVelocity(velocity);
            }
        });
    }

    private List<String> getLines(File file) {
        List<String> lines = new ArrayList<>();

        try (Stream<String> stream = Files.lines(file.toPath())) {
            lines = stream
                    .map(l -> l.split("\n"))
                    .map(l -> l[0])
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
