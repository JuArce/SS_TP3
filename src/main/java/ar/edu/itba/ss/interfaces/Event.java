package ar.edu.itba.ss.interfaces;

public interface Event extends Comparable<Event> {

    void collide();

    double getTc();

    void setTc(double tc);

    default boolean isValid() {
        return getTc() >= 0;
    }
}
