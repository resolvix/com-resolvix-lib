package com.resolvix.lib.util.function;

/**
 * Represents an operation that accepts four input arguments and returns
 * no results.
 *
 * @param <I> the type of the first argument to the operation
 * @param <J> the type of the second argument to the operation
 * @param <K> the type of the third argument to the operation
 * @param <L> the type of the fourth argument to the operation
 */
@FunctionalInterface
public interface QuadConsumer<I, J, K, L> {

    /**
     * Performs the operation on the arguments provided.
     *
     * @param i the first argument
     * @param j the second argument
     * @param k the third argument
     * @param l the fourth argument
     */
    void accept(I i, J j, K k, L l);
}