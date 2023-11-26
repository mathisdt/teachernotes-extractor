package org.zephyrsoft.teachernotes.extractor;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.zephyrsoft.teachernotes.extractor.model.Group;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Start {

    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("missing argument: the TeacherNotes backup file which should be read has to be provided");
            System.exit(1);
        }

        File input = new File(args[0]);
        if (!input.exists() || !input.canRead()) {
            System.out.println("the TeacherNotes backup file which was provided cannot be read: " + args[0]);
            System.exit(1);
        }

        File output = new File(args[0] + "_" + TIMESTAMP_FORMAT.format(LocalDateTime.now()) + ".pdf");

        try {
            List<Group> groups = Importer.read(input);
            Exporter.write(groups, output);
        } catch(Exception e) {
            System.err.println("error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
