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
                LocalDate.of(2020, 8, 12), null, null);
        assertThat(xmlGregorianCalendar.toString(), equalTo("2020-08-12"));
    }

    @Test
    public void toXMLGregorianCalendarWithLocalDateLocalTime()
        throws Exception
    {
        XMLGregorianCalendar xmlGregorianCalendar
            = DateTimeUtils.toXMLGregorianCalendar(
            LocalDate.of(2020, 8, 12),
            LocalTime.of(18, 30, 15), null);
        assertThat(xmlGregorianCalendar.toString(), equalTo("2020-08-12T18:30:15"));
    }

    @Test
    public void toXMLGregorianCalendarWithLocalDateLocalTimeZoneId()
        throws Exception
    {
        XMLGregorianCalendar xmlGregorianCalendarGMT
            = DateTimeUtils.toXMLGregorianCalendar(
            LocalDate.of(2020, 1, 12),
            LocalTime.of(18, 30, 15),
            ZoneId.of("Europe/London"));
        assertThat(xmlGregorianCalendarGMT.toString(), equalTo("2020-01-12T18:30:15Z"));

        XMLGregorianCalendar xmlGregorianCalendarBST
            = DateTimeUtils.toXMLGregorianCalendar(
            LocalDate.of(2020, 8, 12),
            LocalTime.of(18, 30, 15),
            ZoneId.of("Europe/London"));
        assertThat(xmlGregorianCalendarBST.toString(), equalTo("2020-08-12T18:30:15+01:00"));
    }
}
