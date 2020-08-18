package com.resolvix.lib.utils;

import org.junit.Test;

import java.time.*;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class ChronoUtilsUT {

    private LocalDate earlierLocalDate
        = LocalDate.of(2020, 8, 18);

    private LocalDate laterLocalDate
        = LocalDate.of(2020, 8, 19);

    private LocalDateTime earlierLocalDateTime
        = LocalDateTime.of(2020, 8, 18, 0, 0, 59);

    private LocalDateTime laterLocalDateTime
        = LocalDateTime.of(2020, 8, 18, 1, 0, 0);

    private ZonedDateTime earlierZonedDateTime
        = ZonedDateTime.of(
            LocalDate.of(2020, 8, 18),
            LocalTime.of(0, 0, 59),
            ZoneId.systemDefault());

    private ZonedDateTime laterZonedDateTime
        = ZonedDateTime.of(
            LocalDate.of(2020, 8, 18),
            LocalTime.of(1, 0, 0),
            ZoneId.systemDefault());

    //
    //  LocalDate
    //

    @Test
    public void earlierOfLocalDate() {
        assertThat(
            ChronoUtils.earlierOf(earlierLocalDate, laterLocalDate),
            sameInstance(earlierLocalDate));

        assertThat(
            ChronoUtils.earlierOf(laterLocalDate, earlierLocalDate),
            sameInstance(earlierLocalDate));
    }

    @Test
    public void laterOfLocalDate() {
        assertThat(
            ChronoUtils.laterOf(laterLocalDate, earlierLocalDate),
            sameInstance(laterLocalDate));

        assertThat(
            ChronoUtils.laterOf(earlierLocalDate, laterLocalDate),
            sameInstance(laterLocalDate));
    }

    //
    //  ChronoLocalDateTime
    //

    @Test
    public void earlierOfLocalDateTime() {
        assertThat(
            ChronoUtils.earlierOf(earlierLocalDateTime, laterLocalDateTime),
            sameInstance(earlierLocalDateTime));

        assertThat(
            ChronoUtils.earlierOf(laterLocalDateTime, earlierLocalDateTime),
            sameInstance(earlierLocalDateTime));
    }

    @Test
    public void laterOfLocalDateTime() {
        assertThat(
            ChronoUtils.laterOf(laterLocalDateTime, earlierLocalDateTime),
            sameInstance(laterLocalDateTime));

        assertThat(
            ChronoUtils.laterOf(earlierLocalDateTime, laterLocalDateTime),
            sameInstance(laterLocalDateTime));
    }

    //
    //  ChronoZonedDateTime
    //

    @Test
    public void earlierOfZonedDateTime() {
        assertThat(
            ChronoUtils.earlierOf(earlierZonedDateTime, laterZonedDateTime),
            sameInstance(earlierZonedDateTime));

        assertThat(
            ChronoUtils.earlierOf(laterZonedDateTime, earlierZonedDateTime),
            sameInstance(earlierZonedDateTime));
    }

    @Test
    public void laterOfZonedDateTime() {
        assertThat(
            ChronoUtils.laterOf(laterZonedDateTime, earlierZonedDateTime),
            sameInstance(laterZonedDateTime));

        assertThat(
            ChronoUtils.laterOf(earlierZonedDateTime, laterZonedDateTime),
            sameInstance(laterZonedDateTime));
    }
}
