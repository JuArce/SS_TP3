package ar.edu.itba.ss;

import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.models.Point;
import ar.edu.itba.ss.models.Velocity;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.ss.tools.Random.getRandom;

public class Generator {
    public static void main(String[] args) {
        final int N = Integer.parseInt(args[0]);
        final double Lx = Double.parseDouble(args[1]);
        final double Ly = Double.parseDouble(args[2]);
        final double slot = Double.parseDouble(args[3]);
        final double r = 0.0015;
        final double m = 1;
        final double minRandomPositionX = 0 + r;
        final double maxRandomPositionX = Lx - r;
        final double minRandomPositionY = 0 + r;
        final double maxRandomPositionY = Ly - r;
        final double speed = 0.01;
        final double minRandomAngle = 0;
        final double maxRandomAngle = 2 * Math.PI;

        final String staticNFile = "src/main/resources/input/" + args[4];
        final String dynamicNFile = "src/main/resources/input/" + args[5];

        final List<Particle> particles = new ArrayList<>();
        int i = 0;
        int retries = 0;
        while (i < N) {
            Particle newParticle = new Particle(r, m,
                    new Point(getRandom(minRandomPositionX, maxRandomPositionX), getRandom(minRandomPositionY, maxRandomPositionY)),
                    new Velocity(speed, getRandom(minRandomAngle, maxRandomAngle)));
            System.out.println(newParticle);
            if (particles.stream().noneMatch(p -> p.distanceTo(newParticle) < 0)) {
                particles.add(newParticle);
                i++;
            } else {
                retries++;
            }
            if (retries == 100) {
                System.out.println("Too many retries");
                // exit(1);
            }
        }

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(staticNFile), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            writer.writeNext(new String[]{String.valueOf(particles.size())});
            writer.writeNext(new String[]{String.valueOf(Lx * 2)});
            writer.writeNext(new String[]{String.valueOf(Ly)});
            writer.writeNext(new String[]{String.valueOf(slot)});
            particles.forEach(p -> {
                List<String> line = new ArrayList<>();
                line.add(String.valueOf(r));
                line.add(String.valueOf(m));
                writer.writeNext(line.toArray(new String[0]));
            });
            writer.close();
        } catch (Exception e) {
            e.printStackTrace(); //TODO: handle exception
        }

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(dynamicNFile), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            writer.writeNext(new String[]{String.valueOf(0)});
            particles.forEach(p -> {
                List<String> line = new ArrayList<>();
                line.add(String.valueOf(p.getPosition().getX()));
                line.add(String.valueOf(p.getPosition().getY()));
                line.add(String.valueOf(p.getVelocity().getSpeed()));
                line.add(String.valueOf(p.getVelocity().getAngle()));
                writer.writeNext(line.toArray(new String[0]));
            });
            writer.close();
        } catch (Exception e) {
            e.printStackTrace(); //TODO: handle exception
        }
    }
}