package com.resolvix.lib.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.*;

public class DateTimeUtils {

    private static final int NO_OF_SECONDS_PER_MINUTE = 60;

    private static final int NO_OF_NANOSECONDS_PER_MILLISECOND = 1_000_000;

    private DateTimeUtils() {
        //
    }

    private static void setDate(XMLGregorianCalendar xmlGregorianCalendar, LocalDate localDate) {
        assert localDate != null;
        xmlGregorianCalendar.setYear(localDate.getYear());
        xmlGregorianCalendar.setMonth(localDate.getMonthValue());
        xmlGregorianCalendar.setDay(localDate.getDayOfMonth());
    }

    private static void setTime(
        XMLGregorianCalendar xmlGregorianCalendar, LocalTime localTime, boolean includeMilliseconds) {
        assert localTime != null;

        xmlGregorianCalendar.setTime(
            localTime.getHour(), localTime.getMinute(), localTime.getSecond());
        if (!includeMilliseconds)
            return;

        int milliseconds = localTime.getNano() / NO_OF_NANOSECONDS_PER_MILLISECOND;
        xmlGregorianCalendar.setFractionalSecond(
            BigDecimal.valueOf(milliseconds, 3));
    }

    private static void setOffset(
        XMLGregorianCalendar xmlGregorianCalendar, LocalDate localDate, LocalTime localTime, ZoneId zoneId)
    {
        assert localDate != null && localTime != null && zoneId != null;
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(localDateTime);
        xmlGregorianCalendar.setTimezone(zoneOffset.getTotalSeconds() / NO_OF_SECONDS_PER_MINUTE);
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(
        LocalDate localDate, LocalTime localTime, ZoneId zoneId, boolean includeMilliseconds)
        throws DatatypeConfigurationException
    {
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar xmlGregorianCalendar = datatypeFactory.newXMLGregorianCalendar();
        if (localDate != null)
            setDate(xmlGregorianCalendar, localDate);

        if (localTime != null)
            setTime(xmlGregorianCalendar, localTime, includeMilliseconds);

        if (zoneId != null)
            setOffset(xmlGregorianCalendar, localDate, localTime, zoneId);

        return xmlGregorianCalendar;
    }
}
