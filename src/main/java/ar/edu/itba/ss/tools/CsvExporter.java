package ar.edu.itba.ss.tools;

import ar.edu.itba.ss.interfaces.Event;
import ar.edu.itba.ss.interfaces.Exporter;
import ar.edu.itba.ss.models.Particle;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class CsvExporter implements Exporter {
    private static final String baseFilename = "src/main/resources/output/";

    private final String filename;
    private CSVWriter csvWriterAppender;

    public CsvExporter(String filename) {
        this.filename = filename;
    }

    @Override
    public void open() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(baseFilename + filename));
        writer.writeNext(new String[]{"iteration", "id", "x", "y", "speed", "angle"});
        writer.close();

        this.csvWriterAppender = new CSVWriter(new FileWriter(baseFilename + filename, true), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
    }

    @Override
    public void export(int i, Set<Particle> particles, Event event) {
        try {
            csvWriterAppender.writeNext(new String[]{event != null ? event.toString() : ""});
            particles.forEach(p -> {
                String line = i + " " + p.toString();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}