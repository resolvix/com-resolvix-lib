package com.resolvix.lib.lifecycle.api;

/**
 * Defines the interface for an object enabling an object, of type {@code T}
 * to be initialised progressively using a chain of method calls concluding
 * with a call to the {@code build()} operation.
 *
 * @param <T> the type constructed by the builder
 */
public interface Builder<T> {

    /**
     * Returns the object, of type {@code T}, at the conclusion of
     * the initialisation process.
     *
     * @return the built object
     */
    T build();
}
