package ar.edu.itba.ss.tools;

public record Enclosure(double width, double height, double slot) {
    public Enclosure {
        if (width <= 0 || height <= 0 || slot <= 0) {
            throw new IllegalArgumentException("Enclosure dimensions must be positive");
        }
    }
}
