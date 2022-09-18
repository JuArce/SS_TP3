package ar.edu.itba.ss;

import ar.edu.itba.ss.models.Particle;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MultiGenerator {
    public static void main(String[] args) {
        final List<Integer> N = Arrays.asList(10, 50, 100, 200);
        final List<Double> slot = Arrays.asList(0.01, 0.02, 0.05);
        // 200 0.12 0.09 0.01 static.csv dynamic.csv
        final double Lx = 0.12;
        final double Ly = 0.09;
        final String extension = ".csv";

        N.forEach(n -> {
            slot.forEach(s -> {
                final String staticNFile = String.format(Locale.ROOT, "parameters/static_N_%d_slot_%.2f%s", n, s, extension);
                final String dynamicNFile = String.format(Locale.ROOT, "parameters/dynamic_N_%d_slot_%.2f%s", n, s, extension);
                Generator.main(new String[]{String.valueOf(n), String.valueOf(Lx), String.valueOf(Ly), String.valueOf(s), staticNFile, dynamicNFile});
                Particle.sequence = 1;
            });
        });

    }
}
