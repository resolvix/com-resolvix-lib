package com.resolvix.lib.util;

/**
 * Defines an interface for a function accepting two values in the domain
 * and mapping them to a single value in the range; emitting one or more
 * checked exceptions, if necessary.
 *
 * @param <I> the type of the first input value
 * @param <J> the type of the second input value
 * @param <O> the type of the output value
 * @param <E> the type of checked exception(s) emitted
 */
@FunctionalInterface
public interface CheckedBiFunction<I, J, O, E extends Exception>
{

    /**
     * Applies this function to the arguments provided.
     *
     * @param firstInput the first input value
     * @param secondInput the second input value
     * @return the function result
     * @throws E one or more checked exceptions that
     *  may be thrown by the method
     */
    O apply(I firstInput, J secondInput) throws E;
}
