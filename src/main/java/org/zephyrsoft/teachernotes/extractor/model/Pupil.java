package org.zephyrsoft.teachernotes.extractor.model;

import java.util.Comparator;
import java.util.SortedSet;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Pupil implements Comparable<Pupil> {
    private static final Comparator<Pupil> COMPARATOR = Comparator.comparing(Pupil::getName, Comparator.nullsFirst(Comparator.naturalOrder()));

    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private SortedSet<Remark> remarks;

    @Override
    public int compareTo(final Pupil o) {
        return COMPARATOR.compare(o, this);
    }
}
