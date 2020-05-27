package com.resolvix.lib.transform.api;

public interface Transform<T, U> {

    /**
     * Transforms an input object of type {@code T} to an output object of
     * type {@code U}.
     *
     * @param t the input object
     * @return the output object
     */
    U transform(T t);
}
