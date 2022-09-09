package ar.edu.itba.ss.interfaces;

import ar.edu.itba.ss.models.Particle;

import java.io.IOException;
import java.util.Set;

public interface Exporter {

    void open() throws IOException;

    void export(int i, Set<Particle> particles);

    void close();
}
