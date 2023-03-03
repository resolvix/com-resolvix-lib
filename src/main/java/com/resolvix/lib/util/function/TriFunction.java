package com.resolvix.lib.util.function;

/**
 * Represents a function that accepts three arguments and
 * produces a result. This is the three-arity specialization
 * of {@link java.util.function.Function}.
 *
 * @param <I>
 * @param <J>
 * @param <K>
 * @param <O>
 */
@FunctionalInterface
public interface TriFunction<I, J, K, O> {

    /**
     * Applies this function to the arguments provided.
     *
     * @param i the first function argument
     * @param j the second function argument
     * @param k the third function argument
     * @return the function result
     */
    O apply(I i, J j, K k);
}