package com.resolvix.lib.utils;

import com.resolvix.lib.log.Encoder;

import java.util.function.Function;

public class LogUtils {

    private static final Encoder ENCODER = Encoder.getEncoder();

    private LogUtils() { }

    public static String sanitize(String s) {
        return ENCODER.encode(s);
    }

    public static <T> String sanitize(T t) {
        return t == null ? null : sanitize(t.toString());
    }

    public static <T, U> String sanitize(T t, Function<T, U> accessor) {
        if (t == null) {
            return null;
        }

        return sanitize(accessor.apply(t));
    }

    public static <T, U, V> String sanitize(T t, Function<T, U> firstAccessor, Function<U, V> secondAccessor) {
        if (t == null) {
            return null;
        }

        return sanitize(firstAccessor.apply(t), secondAccessor);
    }
}
