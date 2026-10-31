package com.resolvix.lib.utils;

import java.util.function.Function;
import java.util.function.Predicate;

public class ObjectUtils {

    private ObjectUtils() {
        //
    }

    /**
     * Returns the first non-null value in a given list of values.
     *
     * @param values the values
     * @param <T> the value type
     * @return the first non-null value in the given list of values, null,
     *  if all values are null
     */
    public static <T> T coalesce(T... values) {
        if (values == null)
            return null;

        for (T value : values)
            if (value != null)
                return value;
        return null;
    }

    /**
     * Returns null if the value satisfies the predicate, otherwise
     * returns the given value.
     *
     * @param value the value
     * @param predicate the predicate
     * @param <T> the value type
     * @return null if the given value satisfies the given predicate,
     *  otherwise returns the given value
     * @throws IllegalArgumentException if the predicate is null
     */
    public static <T> T nullIf(T value, Predicate<T> predicate) {
        if (predicate == null)
            throw new IllegalArgumentException("predicate is null");

        if (value == null || predicate.test(value))
            return null;
        return value;
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
