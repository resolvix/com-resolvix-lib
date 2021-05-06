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

    /**
     * Returns the output of a chain of functions applied to a given
     * value, unless the given value or the output of a previous function
     * is null.
     *
     * @param s the value
     * @param firstFunction the first function
     * @param secondFunction the second function
     * @param <S> the value type
     * @param <U> the intermediate type
     * @param <T> the output value type
     * @return if the value is non-null, and the output of the first function
     *  applied to the value is also non-null, the output of the second
     *  function applied to the output of the first functional; null, otherwise
     */
    public static <S, T, U> U safe(S s, Function<S, T> firstFunction, Function<T, U> secondFunction) {
        if (s == null)
            return null;
        T t = firstFunction.apply(s);
        if (t == null)
            return null;
        return secondFunction.apply(t);
    }
}
