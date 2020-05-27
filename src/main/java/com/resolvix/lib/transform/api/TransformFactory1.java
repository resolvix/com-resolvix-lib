package com.resolvix.lib.transform.api;

public interface TransformFactory1<T extends Transform<?, ?>, P> {

    /**
     * Instantiates a transform object of type {@code T}.
     *
     * @param p the first parameter, of type {@code P}
     * @return the transform object
     */
    T get(P p);
}
