package com.resolvix.lib.utils;

import com.resolvix.lib.util.CheckedBiFunction;
import com.resolvix.lib.util.CheckedFunction;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A utility class containing methods to assist with invoking functions.
 *
 */
public class FunctionUtils {

    private FunctionUtils() {
        //
    }

    /**
     * Invokes a function that throws one or more checked exceptions,
     * returns the result of the function or, if an exception is thrown,
     * invokes a further bi-function to map the input parameter and
     * the exception to an appropriate value in the range of the
     * checked function.
     *
     * @param checkedFunction the checked function
     * @param input the input value
     * @param doOnException the bi-function to perform upon occurrence
     *  of an exception
     * @return the output of the checked function, if it executed without
     *  an exception being thrown; the output of the {@code doOnException}
     *  function, if an exception is thrown
     * @param <I> the input type
     * @param <O> the output type
     * @param <E> the exception type that may be thrown by the
     *  {@code checkedFunction} method
     */
    @SuppressWarnings("unchecked")
    public static <I, O, E extends Exception> O onExceptionDo(
        CheckedFunction<I, O, E> checkedFunction, I input, BiFunction<I, E, O> doOnException)
    {
        try {
            return checkedFunction.apply(input);
        } catch (Exception e) {
            return doOnException.apply(input, (E) e);
        }
    }

    /**
     * Invokes a function that throws one or more checked exceptions,
     * returns the result of the function or, if an exception is thrown,
     * invokes a further bi-function to map the input parameter and
     * the exception to an appropriate value in the range of the
     * checked function, if possible.
     *
     * @param checkedFunction the checked function
     * @param input the input value
     * @param doOnException the checked bi-function to perform upon
     *  occurrence of an exception
     * @return the output of the checked function, if it executed without
     *  an exception being thrown; the output of the {@code doOnException}
     *  function, if an exception is thrown and that subsequent operation
     *  was successful
     * @throws E if the checked function threw an exception, and the
     *  subsequent call to the {@code doOnException} function could not
     *  return an appropriate value in the range
     * @throws X if the checked function threw an exception,and the
     *  subsequent call to the {@code doOnException} function resulted
     *  in a further checked exception to be thrown
     * @param <I> the input type
     * @param <O> the output type
     * @param <E> the exception type that may be thrown by the
     *  {@code checkedFunction} method
     * @param <X> the exception types that may be thrown by the
     *  {@code doOnException} method
     */
    @SuppressWarnings("unchecked")
    public static <I, O, E extends Exception, X extends Exception> O onExceptionTry(
        CheckedFunction<I, O, E> checkedFunction, I input, CheckedBiFunction<I, E, O, X> doOnException)
        throws E, X
    {
        try {
            return checkedFunction.apply(input);
        } catch (Exception e) {
            return doOnException.apply(input, (E) e);
        }
    }

    /**
     * Invokes a function that throws one or more checked exceptions,
     * returning the result of the function or, if an exception is thrown,
     * throwing a new unchecked exception generated by the function given
     * by {@code exceptionMapper}.
     *
     * @param checkedFunction the checked function
     * @param input the input value
     * @param exceptionMapper the exception mapper
     * @return the output of the checked function if it executes without
     *  an exception being thrown
     * @throws X if a checked exception is thrown, invoking the
     *  {@code exceptionMapper} function
     * @param <I> the input type
     * @param <O> the output type
     * @param <E> the exception type that may be thrown by the
     *  {@code checkedFunction} method
     * @param <X> the exception type that may be thrown by the
     *  {@code exceptionMapper} method
     */
    @SuppressWarnings("unchecked")
    public static <I, O, E extends Exception, X extends RuntimeException> O toUnchecked(
        CheckedFunction<I, O, E> checkedFunction, I input, Function<E, X> exceptionMapper)
    {
        try {
            return checkedFunction.apply(input);
        } catch (Exception e) {
            throw exceptionMapper.apply((E) e);
        }
    }
}
