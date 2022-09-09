package ar.edu.itba.ss.tools;

import java.util.concurrent.ThreadLocalRandom;

public class Random {
    public static final double epsilon = 0.000001;

    public static double getRandom(double min, double max) {
        if (Math.abs(max - min) < epsilon || min > max) {
            return 0.0;
        }
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
}
