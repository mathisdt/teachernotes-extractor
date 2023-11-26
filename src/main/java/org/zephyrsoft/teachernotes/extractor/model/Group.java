package org.zephyrsoft.teachernotes.extractor.model;

import java.util.Comparator;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Group implements Comparable<Group> {
    private static final Comparator<Group> COMPARATOR = Comparator.comparing(Group::getName, Comparator.nullsFirst(Comparator.naturalOrder()));

    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Pupil> pupils;

    @Override
    public int compareTo(final Group o) {
        return COMPARATOR.compare(this, o);
    }
}
