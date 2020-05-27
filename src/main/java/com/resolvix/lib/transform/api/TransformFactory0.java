package com.resolvix.lib.transform.api;

public interface TransformFactory0<T extends Transform<?, ?>> {

    /**
     * Instantiates a transform object of type {@code T}.
     *
     * @return the transform object
     */
    T get();
}
