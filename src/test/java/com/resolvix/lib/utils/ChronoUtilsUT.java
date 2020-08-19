package com.resolvix.lib.utils;

import org.junit.Test;

import java.time.*;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class ChronoUtilsUT {

    private LocalDate earlierLocalDate
        = LocalDate.of(2020, 8, 18);

    private LocalDate sameEarlierLocalDate
        = LocalDate.of(2020, 8, 18);

    private LocalDate laterLocalDate
        = LocalDate.of(2020, 8, 19);

    private LocalDate sameLaterLocalDate
        = LocalDate.of(2020, 8, 19);

    private LocalDateTime earlierLocalDateTime
        = LocalDateTime.of(2020, 8, 18, 0, 0, 59);

    private LocalDateTime sameEarlierLocalDateTime
        = LocalDateTime.of(2020, 8, 18, 0, 0, 59);

    private LocalDateTime laterLocalDateTime
        = LocalDateTime.of(2020, 8, 18, 1, 0, 0);

    private LocalDateTime sameLaterLocalDateTime
        = LocalDateTime.of(2020, 8, 18, 1, 0, 0);

    private ZonedDateTime earlierZonedDateTime
        = ZonedDateTime.of(
            LocalDate.of(2020, 8, 18),
            LocalTime.of(0, 0, 59),
            ZoneId.systemDefault());

    private ZonedDateTime sameEarlierZonedDateTime
        = ZonedDateTime.of(
        LocalDate.of(2020, 8, 18),
        LocalTime.of(0, 0, 59),
        ZoneId.systemDefault());

    private ZonedDateTime laterZonedDateTime
        = ZonedDateTime.of(
            LocalDate.of(2020, 8, 18),
            LocalTime.of(1, 0, 0),
            ZoneId.systemDefault());

    private ZonedDateTime sameLaterZonedDateTime
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
    public void earlierOfSameLocalDate() {
        assertThat(
            ChronoUtils.earlierOf(earlierLocalDate, sameEarlierLocalDate),
            sameInstance(sameEarlierLocalDate));

        assertThat(
            ChronoUtils.earlierOf(laterLocalDate, sameLaterLocalDate),
            sameInstance(sameLaterLocalDate));
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

    @Test
    public void laterOfSameLocalDate() {
        assertThat(
            ChronoUtils.laterOf(laterLocalDate, sameLaterLocalDate),
            sameInstance(sameLaterLocalDate));

        assertThat(
            ChronoUtils.laterOf(earlierLocalDate, sameEarlierLocalDate),
            sameInstance(sameEarlierLocalDate));
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
    public void earlierOfSameLocalDateTime() {
        assertThat(
            ChronoUtils.earlierOf(earlierLocalDateTime, sameEarlierLocalDateTime),
            sameInstance(sameEarlierLocalDateTime));

        assertThat(
            ChronoUtils.earlierOf(laterLocalDateTime, sameLaterLocalDateTime),
            sameInstance(sameLaterLocalDateTime));
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

    @Test
    public void laterOfSameLocalDateTime() {
        assertThat(
            ChronoUtils.laterOf(laterLocalDateTime, sameLaterLocalDateTime),
            sameInstance(sameLaterLocalDateTime));

        assertThat(
            ChronoUtils.laterOf(earlierLocalDateTime, sameLaterLocalDateTime),
            sameInstance(sameLaterLocalDateTime));
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
    public void earlierOfSameZonedDateTime() {
        assertThat(
            ChronoUtils.earlierOf(earlierZonedDateTime, sameEarlierZonedDateTime),
            sameInstance(sameEarlierZonedDateTime));

        assertThat(
            ChronoUtils.earlierOf(laterZonedDateTime, sameLaterZonedDateTime),
            sameInstance(sameLaterZonedDateTime));
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

    @Test
    public void laterOfSameZonedDateTime() {
        assertThat(
            ChronoUtils.laterOf(laterZonedDateTime, sameLaterZonedDateTime),
            sameInstance(sameLaterZonedDateTime));

        assertThat(
            ChronoUtils.laterOf(earlierZonedDateTime, sameEarlierZonedDateTime),
            sameInstance(sameEarlierZonedDateTime));
    }
}
