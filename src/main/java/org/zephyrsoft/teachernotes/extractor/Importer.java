package org.zephyrsoft.teachernotes.extractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.zephyrsoft.teachernotes.extractor.model.Group;
import org.zephyrsoft.teachernotes.extractor.model.Pupil;
import org.zephyrsoft.teachernotes.extractor.model.Remark;
import org.zephyrsoft.teachernotes.extractor.model.RemarkType;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Importer {
    private static final Pattern ENCODED_COMMA = Pattern.compile("#!");

    public static List<Group> read(final File teacherNotesBackup) throws IOException {
        try (Stream<String> lineStream = Files.lines(teacherNotesBackup.toPath())) {
            Deque<String> lines = lineStream
                .skip(2)
                .collect(Collectors.toCollection(ArrayDeque::new));

            // header
            Deque<String> groupNames = Arrays.stream(lines.removeFirst().split(","))
                .filter(s -> !s.isBlank())
                .collect(Collectors.toCollection(ArrayDeque::new));

            // ignore category names, remark list and pupil base data
            while (!lines.getFirst().startsWith("Class ")) {
                lines.removeFirst();
            }

            List<Group> groups = new ArrayList<>();
            for (String groupName : groupNames) {
                Group group = new Group();
                group.setName(groupName);
                lines.removeFirst();

                Deque<String> pupilNames = Arrays.stream(lines.removeFirst().split(","))
                    .skip(1)
                    .collect(Collectors.toCollection(ArrayDeque::new));

                SortedSet<Pupil> pupils = new TreeSet<>();
                group.setPupils(pupils);
                while (pupilNames.size() >= 3) {
                    String pupilFirstName = pupilNames.removeFirst();
                    String pupilLastName = pupilNames.removeFirst();
                    pupilNames.removeFirst();

                    String pupilName;
                    if (pupilFirstName.isBlank()) {
                        pupilName = pupilLastName;
                    } else if (pupilLastName.isBlank()) {
                        pupilName = pupilFirstName;
                    } else {
                        pupilName = pupilLastName + ", " + pupilFirstName;
                    }

                    Deque<String> remarkStrings = Arrays.stream(lines.removeFirst().split(","))
                        .skip(1)
                        .collect(Collectors.toCollection(ArrayDeque::new));

                    Pupil pupil = new Pupil();
                    pupils.add(pupil);
                    pupil.setName(pupilName);
                    SortedSet<Remark> remarks = new TreeSet<>();
                    pupil.setRemarks(remarks);

                    while (remarkStrings.size() >= 3) {
                        Remark r = new Remark();
                        remarks.add(r);
                        r.setTimestamp(
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(remarkStrings.removeFirst())), ZoneId.of("Europe/Berlin")));
                        r.setText(ENCODED_COMMA.matcher(remarkStrings.removeFirst()).replaceAll(","));
                        r.setType(RemarkType.of(remarkStrings.removeFirst()));
                    }

                    // remove blank line after remarks
                    lines.removeFirst();
                }

                while (!lines.isEmpty() && !lines.getFirst().startsWith("Class ")) {
                    lines.removeFirst();
                }

                if (!group.getPupils().isEmpty()) {
                    groups.add(group);
                }
            }
            return groups;
        }
    }

}
