package ar.edu.itba.ss.tools;

import ar.edu.itba.ss.interfaces.Event;
import ar.edu.itba.ss.interfaces.Exporter;
import ar.edu.itba.ss.models.Particle;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.util.Set;

public class OvitoExporter implements Exporter {
    private static final String baseFilename = "src/main/resources/output/";

    private final String filename;
    private CSVWriter csvWriterAppender;

    public OvitoExporter(String filename) {
        this.filename = filename;
    }

    @Override
    public void open() {
        try {
            this.csvWriterAppender = new CSVWriter(new FileWriter(baseFilename + filename, true), ' ', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void export(int i, Set<Particle> particles, Event event) {
        try {
            csvWriterAppender.writeNext(new String[]{particles.size() + ""});
            csvWriterAppender.writeNext(new String[]{event != null ? event.toString() : ""});
            particles.forEach(p -> {
                String line = p.toString();
                csvWriterAppender.writeNext(line.split(" "));
            });
        } catch (Exception e) {
            e.printStackTrace(); //TODO: handle exception
        }
    }

    @Override
    public void close() {
        try {
            csvWriterAppender.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
