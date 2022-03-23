package com.resolvix.lib.utils;

import java.util.Iterator;
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

    /**
     * Determines the equality of two iterable data structure operands, where
     * {@code left} is equal to {@code right} if the iterable data structures
     * -
     *
     *  (1) are of the same length; and
     *  (2) contain elements that are equal in the same order.
     *
     * @param left the left operand
     * @param right the right operand
     * @param <T> the type of element contained in each iterable data structure
     * @return true, if the iterable data structures are equal; false,
     *  otherwise
     */
    public static <T> boolean safeContentsEquals(Iterable<T> left, Iterable<T> right) {
        if (left == right)
            return true;

        if (left == null || right == null)
            return false;

        Iterator<T> itLeft = left.iterator();
        Iterator<T> itRight = right.iterator();
        do {
            if (!(itLeft.hasNext() && itRight.hasNext()))
                return !(itLeft.hasNext() || itRight.hasNext());

            if (!safeEquals(itLeft.next(), itRight.next()))
                return false;
        } while (true);
    }

    /**
     * Determines the equality of two values, the de-referenced operands,
     * obtained by de-referencing the left and right operands, one or both of
     * which may be null.
     *
     * @param leftT the left operand
     * @param rightT the right operand
     * @param fn the de-referencing function
     * @param <T> the operand type
     * @param <U> the de-referenced operand type
     * @return true, if the left and right operands are both contain
     *  {@code null} values or are equal after being de-referenced; false,
     *  otherwise
     */
    public static <T, U> boolean safeDereferenceEquals(T leftT, T rightT, Function<T, U> fn) {
        if (fn == null)
            throw new AssertionError("fn must not be null");

        if (leftT == null && rightT == null)
            return true;

        return (leftT != null && rightT != null && safeEquals(fn.apply(leftT), fn.apply(rightT)));
    }

    /**
     * Determines the equality of two values, one or both of which may be
     * {@code null}.
     *
     * @param leftT the left operand
     * @param rightT the right operand
     * @param <T> the operand type
     * @return true, if the left operand and the right operand are equal;
     *  false, otherwise
     */
    public static <T> boolean safeEquals(T leftT, T rightT) {
        if (leftT == null && rightT == null)
            return true;

        if (leftT == null)
            return false;

        return leftT.equals(rightT);
    }
}
