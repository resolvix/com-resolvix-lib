package com.resolvix.lib.utils;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;

public class ChronoUtils {

    private ChronoUtils() {
        //
    }

    public static <T extends ChronoLocalDate> T earlierOf(T left, T right) {
        if (left.isBefore(right))
            return left;
        return right;
    }

    public static <T extends ChronoLocalDate> T laterOf(T left, T right) {
        if (left.isAfter(right))
            return left;
        return right;
    }

    public static <T extends ChronoLocalDateTime<?>> T earlierOf(T left, T right) {
        if (left.isBefore(right))
            return left;
        return right;
    }

    public static <T extends ChronoLocalDateTime<?>> T laterOf(T left, T right) {
        if (left.isAfter(right))
            return left;
        return right;
    }

    public static <T extends ChronoZonedDateTime<?>> T earlierOf(T left, T right) {
        if (left.isBefore(right))
            return left;
        return right;
    }

    public static <T extends ChronoZonedDateTime<?>> T laterOf(T left, T right) {
        if (left.isAfter(right))
            return left;
        return right;
    }
}
