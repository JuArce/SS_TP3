package ar.edu.itba.ss.tools;

import ar.edu.itba.ss.interfaces.Event;
import ar.edu.itba.ss.interfaces.Exporter;
import ar.edu.itba.ss.models.Particle;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class FpExporter implements Exporter {
    private static final String baseFilename = "src/main/resources/output/";

    private final String filename;
    private final double width;
    private double t;

    private CSVWriter csvWriterAppender;

    public FpExporter(String filename, double width) {
        this.filename = filename;
        this.width = width;
        this.t = 0;
    }

    @Override
    public void open() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(baseFilename + filename));
            writer.writeNext(new String[]{"t", "fp"});
            writer.close();

            this.csvWriterAppender = new CSVWriter(new FileWriter(baseFilename + filename, true), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void export(int i, Set<Particle> particles, Event event) {
        try {
            double fp = getFp(particles);
            this.t += event != null ? event.getTc() : 0;
            csvWriterAppender.writeNext(new String[]{String.valueOf(t), String.valueOf(fp)});
        } catch (Exception e) {
            e.printStackTrace(); //TODO: handle exception
        }
    }

    private double getFp(Set<Particle> particles) {
        int right = 0;
        int left = 0;
        for (Particle p : particles) {
            if (p.getPosition().getX() > width / 2) {
                right++;
            } else {
                left++;
            }
        }
        return (double) right / (right + left);
    }

    @Override
    public void close() {
        try {
            csvWriterAppender.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
