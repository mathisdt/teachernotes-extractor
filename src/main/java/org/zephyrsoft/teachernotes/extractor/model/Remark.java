package org.zephyrsoft.teachernotes.extractor.model;

import java.time.LocalDateTime;
import java.util.Comparator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Remark implements Comparable<Remark> {
    private static final Comparator<Remark> COMPARATOR = Comparator
        .comparing(Remark::getTimestamp, Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(Remark::getText, Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(Remark::getType, Comparator.nullsFirst(Comparator.naturalOrder()));

    private LocalDateTime timestamp;
    private String text;
    private RemarkType type;

    @Override
    public int compareTo(final Remark o) {
        return COMPARATOR.compare(o, this);
    }
}
