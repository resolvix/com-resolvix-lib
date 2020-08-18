package com.resolvix.lib.utils;

import org.junit.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class DateTimeUtilsUT {

    @Test
    public void toXMLGregorianCalendarWithLocalDate()
        throws Exception
    {
        XMLGregorianCalendar xmlGregorianCalendar
            = DateTimeUtils.toXMLGregorianCalendar(
                LocalDate.of(2020, 8, 12), null, null, false);
        assertThat(xmlGregorianCalendar.toString(), equalTo("2020-08-12"));
    }

    @Test
    public void toXMLGregorianCalendarWithLocalDateLocalTime()
        throws Exception
    {
        XMLGregorianCalendar xmlGregorianCalendarWithoutNano
            = DateTimeUtils.toXMLGregorianCalendar(
            LocalDate.of(2020, 8, 12),
            LocalTime.of(18, 30, 15), null, false);
        assertThat(xmlGregorianCalendarWithoutNano.toString(), equalTo("2020-08-12T18:30:15"));

        XMLGregorianCalendar xmlGregorianCalendarWithNano
            = DateTimeUtils.toXMLGregorianCalendar(
            LocalDate.of(2020, 8, 12),
            LocalTime.of(18, 30, 15, 999999999), null, true);
        assertThat(xmlGregorianCalendarWithNano.toString(), equalTo("2020-08-12T18:30:15.999"));
    }

    @Test
    public void toXMLGregorianCalendarWithLocalDateLocalTimeZoneId()
        throws Exception
    {
        XMLGregorianCalendar xmlGregorianCalendarGMT
            = DateTimeUtils.toXMLGregorianCalendar(
            LocalDate.of(2020, 1, 12),
            LocalTime.of(18, 30, 15),
            ZoneId.of("Europe/London"), false);
        assertThat(xmlGregorianCalendarGMT.toString(), equalTo("2020-01-12T18:30:15Z"));

        XMLGregorianCalendar xmlGregorianCalendarGMTWithMilliseconds
            = DateTimeUtils.toXMLGregorianCalendar(
            LocalDate.of(2020, 1, 12),
            LocalTime.of(18, 30, 15, 999_999_999),
            ZoneId.of("Europe/London"), true);
        assertThat(xmlGregorianCalendarGMTWithMilliseconds.toString(), equalTo("2020-01-12T18:30:15.999Z"));

        XMLGregorianCalendar xmlGregorianCalendarBST
            = DateTimeUtils.toXMLGregorianCalendar(
            LocalDate.of(2020, 8, 12),
            LocalTime.of(18, 30, 15),
            ZoneId.of("Europe/London"), false);
        assertThat(xmlGregorianCalendarBST.toString(), equalTo("2020-08-12T18:30:15+01:00"));

        XMLGregorianCalendar xmlGregorianCalendarBSTWithMilliseconds
            = DateTimeUtils.toXMLGregorianCalendar(
            LocalDate.of(2020, 8, 12),
            LocalTime.of(18, 30, 15, 999_999_999),
            ZoneId.of("Europe/London"), true);
        assertThat(xmlGregorianCalendarBSTWithMilliseconds.toString(), equalTo("2020-08-12T18:30:15.999+01:00"));
    }
}
