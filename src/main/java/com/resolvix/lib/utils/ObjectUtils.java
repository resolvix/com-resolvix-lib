package com.resolvix.lib.utils;

import java.util.function.Function;

public class ObjectUtils {

    private ObjectUtils() {
        //
    }

    /**
     * Returns the output of a given function applied to a given value,
     * unless the given value is null.
     *
     * @param s the value
     * @param function the function
     * @param <S> the value type
     * @param <T> the output value type
     * @return if the value is non-null, the output of the function applied to
     *  the value; null, otherwise
     */
    public static <S, T> T safe(S s, Function<S, T> function) {
        if (s == null)
            return null;
        return function.apply(s);
    }
}
