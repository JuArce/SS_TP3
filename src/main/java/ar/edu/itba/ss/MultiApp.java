package ar.edu.itba.ss;

import ar.edu.itba.ss.models.Particle;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MultiApp {
    public static void main(String[] args) {
        final List<Integer> N = Arrays.asList(100, 200);
        final List<Double> slot = Arrays.asList(0.01, 0.02, 0.05);

        final String extension = ".csv";

        N.forEach(n -> {
            slot.forEach(s -> {
                final String staticNFile = String.format(Locale.ROOT, "src/main/resources/input/parameters/static_N_%d_slot_%.2f%s", n, s, extension);
                final String dynamicNFile = String.format(Locale.ROOT, "src/main/resources/input/parameters/dynamic_N_%d_slot_%.2f%s", n, s, extension);
                final String outputFilename = String.format(Locale.ROOT, "parameters/output_N_%d_slot_%.2f%s", n, s, extension);
                final String fpFilename = String.format(Locale.ROOT, "fp/output_fp_N_%d_slot_%.2f%s", n, s, extension);
                try {
                    App.main(new String[]{staticNFile, dynamicNFile, outputFilename, fpFilename});
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Particle.sequence = 1;
            });
        });
    }
}
