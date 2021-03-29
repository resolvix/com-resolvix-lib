package com.resolvix.lib.utils;

import org.junit.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.*;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class DateTimeUtilsUT {

    @Test
    public void toXMLGregorianCalendarWithLocalDate()
    {
        XMLGregorianCalendar xmlGregorianCalendar
            = DateTimeUtils.toXMLGregorianCalendar(
                LocalDate.of(2020, 8, 12), null, null);
        assertThat(xmlGregorianCalendar.toString(), equalTo("2020-08-12"));
    }

    @Test
    public void toXMLGregorianCalendarWithLocalDateLocalTime()
    {
        XMLGregorianCalendar xmlGregorianCalendarWithoutNano
            = DateTimeUtils.toXMLGregorianCalendar(
                LocalDate.of(2020, 8, 12),
                LocalTime.of(18, 30, 15), null);
        assertThat(xmlGregorianCalendarWithoutNano.toString(), equalTo("2020-08-12T18:30:15"));

        XMLGregorianCalendar xmlGregorianCalendarWithNano
            = DateTimeUtils.toXMLGregorianCalendar(
                LocalDate.of(2020, 8, 12),
                LocalTime.of(18, 30, 15,  999999999), null);
        assertThat(xmlGregorianCalendarWithNano.toString(), equalTo("2020-08-12T18:30:15.999"));
    }

    @Test
    public void toXMLGregorianCalendarWithLocalDateLocalTimeZoneId()
    {
        XMLGregorianCalendar xmlGregorianCalendarGMT
            = DateTimeUtils.toXMLGregorianCalendar(
                LocalDate.of(2020, 1, 12),
                LocalTime.of(18, 30, 15),
                ZoneId.of("Europe/London"));
        assertThat(xmlGregorianCalendarGMT.toString(), equalTo("2020-01-12T18:30:15Z"));

        XMLGregorianCalendar xmlGregorianCalendarGMTWithMilliseconds
            = DateTimeUtils.toXMLGregorianCalendar(
                LocalDate.of(2020, 1, 12),
                LocalTime.of(18, 30, 15, 999_999_999),
                ZoneId.of("Europe/London"));
        assertThat(xmlGregorianCalendarGMTWithMilliseconds.toString(), equalTo("2020-01-12T18:30:15.999Z"));

        XMLGregorianCalendar xmlGregorianCalendarBST
            = DateTimeUtils.toXMLGregorianCalendar(
                LocalDate.of(2020, 8, 12),
                LocalTime.of(18, 30, 15),
                ZoneId.of("Europe/London"));
        assertThat(xmlGregorianCalendarBST.toString(), equalTo("2020-08-12T18:30:15+01:00"));

        XMLGregorianCalendar xmlGregorianCalendarBSTWithMilliseconds
            = DateTimeUtils.toXMLGregorianCalendar(
                LocalDate.of(2020, 8, 12),
                LocalTime.of(18, 30, 15, 999_999_999),
                ZoneId.of("Europe/London"));
        assertThat(xmlGregorianCalendarBSTWithMilliseconds.toString(), equalTo("2020-08-12T18:30:15.999+01:00"));
    }

    @Test
    public void toXMLGregorianCalendarWithLocalDateLocalTimeZoneOffset()
    {
        XMLGregorianCalendar xmlGregorianCalendarUTC
            = DateTimeUtils.toXMLGregorianCalendar(
                LocalDate.of(2021, 3, 29),
                LocalTime.of(13, 40, 0, 0),
                ZoneOffset.UTC);
        assertThat(xmlGregorianCalendarUTC.toString(), equalTo("2021-03-29T13:40:00Z"));
    }

    @Test
    public void toXMLGregorianCalendarWithZonedDateTime() {
        XMLGregorianCalendar xmlGregorianCalendarGMT
            = DateTimeUtils.toXMLGregorianCalendar(
            ZonedDateTime.of(
                LocalDate.of(2021, 3, 27),
                LocalTime.of(13, 40, 0),
                ZoneId.of("Europe/London")));
        assertThat(xmlGregorianCalendarGMT.toString(), equalTo("2021-03-27T13:40:00.000Z"));

        XMLGregorianCalendar xmlGregorianCalendarBST
            = DateTimeUtils.toXMLGregorianCalendar(
                ZonedDateTime.of(
                    LocalDate.of(2021, 3, 29),
                    LocalTime.of(13, 40, 0),
                    ZoneId.of("Europe/London")));
        assertThat(xmlGregorianCalendarBST.toString(), equalTo("2021-03-29T13:40:00.000+01:00"));
    }
}
