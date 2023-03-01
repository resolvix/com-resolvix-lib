package com.resolvix.lib.util;

/**
 * Defines an interface for a function mapping an input value in the
 * domain to an output value in the range; emitting one or more
 * checked exceptions, if necessary.
 *
 * @param <I> the type of the input value
 * @param <O> the type of the output value
 * @param <E> the type of the checked exception(s) emitted
 */
@FunctionalInterface
public interface CheckedFunction<I, O, E extends Exception>
{

    /**
     * Applies this function to the {@code input} argument.
     *
     * @param input the input value
     * @return the function output
     * @throws E one or more checked exceptions that
     *  may be thrown by the method
     */
    O apply(I input) throws E;
}