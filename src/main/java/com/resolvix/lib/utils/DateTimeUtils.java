package com.resolvix.lib.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.*;
import java.util.GregorianCalendar;
import java.util.function.Function;

public class DateTimeUtils {

    private static final int NO_OF_SECONDS_PER_MINUTE = 60;

    private static final int NO_OF_NANOSECONDS_PER_MILLISECOND = 1_000_000;

    private static DatatypeFactory getDatatypeFactory() {
        try {
            return DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static final DatatypeFactory DATATYPE_FACTORY = getDatatypeFactory();

    private DateTimeUtils() {
        //
    }

    /**
     * Returns the value returned by the function as applied to the value
     * given by {@code localDate}, unless {@code localDate} is {@code null}.
     *
     * @param localDate the local date
     * @param function the function to apply to the local date
     * @param <T> the return type of the function
     * @return if the local date is non-null, the value returned by the
     *  function applied to the local date; null, otherwise
     */
    public static <T> T safeLocalDate(LocalDate localDate, Function<LocalDate, T> function) {
        if (localDate == null)
            return null;
        return function.apply(localDate);
    }

    /**
     * Returns the value returned by the function as applied to the value
     * given by {@code localTime}, unless {@code localTime} is {@code null}.
     *
     * @param localTime the local time
     * @param function the function to apply to the local time
     * @param <T> the return type of the function
     * @return if the local time is non-null, the value returned by the
     *  function applied to the local time; null, otherwise
     */
    public static <T> T safeLocalTime(LocalTime localTime, Function<LocalTime, T> function) {
        if (localTime == null)
            return null;
        return function.apply(localTime);
    }

    /**
     * Returns the value returned by the function as applied to the value
     * given by {@code localDateTime}, unless {@code localDateTime} is
     * {@code null}.
     *
     * @param localDateTime the local date time
     * @param function the function to apply to the local date time
     * @param <T> the return type of the function
     * @return if the local time is non-null, the value returned by the
     *  function applied to the local date; null, otherwise
     */
    public static <T> T safeLocalDateTime(LocalDateTime localDateTime, Function<LocalDateTime, T> function) {
        if (localDateTime == null)
            return null;
        return function.apply(localDateTime);
    }

    /**
     * Returns the value returned by the function as applied to the value
     * given by {@code zonedDateTime}, unless {@code zonedDateTime} is
     * {@code null}.
     *
     * @param zonedDateTime the zoned date time
     * @param function the function to apply to the zoned date time
     * @param <T> the return type of the function
     * @return if the zoned date time is non-null, the value returned by the
     *  function applied to the zoned date time; null, otherwise
     */
    public static <T> T safeZonedLocalDateTime(ZonedDateTime zonedDateTime, Function<ZonedDateTime, T> function) {
        if (zonedDateTime == null)
            return null;
        return function.apply(zonedDateTime);
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
            localTime.getHour(), localTime.getMinute(), localTime.getSecond());
        int nano = localTime.getNano();
        if (nano != 0) {
            xmlGregorianCalendar.setMillisecond(
                localTime.getNano() / NO_OF_NANOSECONDS_PER_MILLISECOND);
        }
    }

    private static void setOffset(
        XMLGregorianCalendar xmlGregorianCalendar, LocalDate localDate, LocalTime localTime, ZoneId zoneId)
    {
        assert localDate != null && localTime != null && zoneId != null;
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(localDateTime);
        xmlGregorianCalendar.setTimezone(zoneOffset.getTotalSeconds() / NO_OF_SECONDS_PER_MINUTE);
    }

    /**
     * Returns a {@link XMLGregorianCalendar} object initialized using the
     * relevant {@link LocalDate}, {@link LocalTime}, and {@link ZoneId}.
     *
     * @param localDate the date
     * @param localTime the time
     * @param zoneId the zone identifier
     * @return the {@link XMLGregorianCalendar} object
     * @throws DatatypeConfigurationException
     */
    public static XMLGregorianCalendar toXMLGregorianCalendar(
        LocalDate localDate, LocalTime localTime, ZoneId zoneId)
    {
        XMLGregorianCalendar xmlGregorianCalendar = DATATYPE_FACTORY.newXMLGregorianCalendar();
        if (localDate != null)
            setDate(xmlGregorianCalendar, localDate);

        if (localTime != null)
            setTime(xmlGregorianCalendar, localTime);

        if (zoneId != null)
            setOffset(xmlGregorianCalendar, localDate, localTime, zoneId);

        return xmlGregorianCalendar;
    }

    private static void setOffset(
        XMLGregorianCalendar xmlGregorianCalendar, ZoneOffset zoneOffset)
    {
        assert zoneOffset != null;
        xmlGregorianCalendar.setTimezone(zoneOffset.getTotalSeconds() / NO_OF_SECONDS_PER_MINUTE);
    }

    /**
     * Returns a {@link XMLGregorianCalendar} object initialized using the
     * relevant {@link LocalDate}, {@link LocalTime}, and {@link ZoneOffset}.
     *
     * @param localDate the date
     * @param localTime the time
     * @param zoneOffset the offset
     * @return the {@link XMLGregorianCalendar} object
     */
    public static XMLGregorianCalendar toXMLGregorianCalendar(
        LocalDate localDate, LocalTime localTime, ZoneOffset zoneOffset)
    {
        XMLGregorianCalendar xmlGregorianCalendar = DATATYPE_FACTORY.newXMLGregorianCalendar();
        if (localDate != null)
            setDate(xmlGregorianCalendar, localDate);

        if (localTime != null)
            setTime(xmlGregorianCalendar, localTime);

        if (zoneOffset != null)
            setOffset(xmlGregorianCalendar, zoneOffset);

        return xmlGregorianCalendar;
    }

    /**
     * Returns a {@link XMLGregorianCalendar} object initialized using a
     * {@link ZonedDateTime}.
     *
     * @param zonedDateTime the zoned date time
     * @return the {@link XMLGregorianCalendar} object
     */
    public static XMLGregorianCalendar toXMLGregorianCalendar(
        ZonedDateTime zonedDateTime)
    {
        return DATATYPE_FACTORY.newXMLGregorianCalendar(
            GregorianCalendar.from(zonedDateTime));
    }
}
