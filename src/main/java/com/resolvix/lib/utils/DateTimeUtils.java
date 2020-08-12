package com.resolvix.lib.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.*;

public class DateTimeUtils {

    private static final int NO_OF_SECONDS_PER_MINUTE = 60;

    private DateTimeUtils() {
        //
    }

    private static void setDate(XMLGregorianCalendar xmlGregorianCalendar, LocalDate localDate) {
        assert localDate != null;
        xmlGregorianCalendar.setYear(localDate.getYear());
        xmlGregorianCalendar.setMonth(localDate.getMonthValue());
        xmlGregorianCalendar.setDay(localDate.getDayOfMonth());
    }

    private static void setTime(XMLGregorianCalendar xmlGregorianCalendar, LocalTime localTime) {
        assert localTime != null;
        xmlGregorianCalendar.setTime(
            localTime.getHour(), localTime.getMinute(), localTime.getSecond(),
            BigDecimal.valueOf(localTime.getNano()));
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
        LocalDate localDate, LocalTime localTime, ZoneId zoneId)
        throws DatatypeConfigurationException
    {
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar xmlGregorianCalendar = datatypeFactory.newXMLGregorianCalendar();
        if (localDate != null)
            setDate(xmlGregorianCalendar, localDate);

        if (localTime != null)
            setTime(xmlGregorianCalendar, localTime);

        if (zoneId != null)
            setOffset(xmlGregorianCalendar, localDate, localTime, zoneId);

        return xmlGregorianCalendar;
    }
}
