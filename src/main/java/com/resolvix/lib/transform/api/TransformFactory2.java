package com.resolvix.lib.transform.api;

public interface TransformFactory2<T extends Transform<?, ?>, P, Q> {

    /**
     * Instantiates a transform object of type {@code T}.
     *
     * @param p the first parameter, of type {@code P}
     * @param q the second parameter, of type {@code Q}
     * @return the transform object
     */
    T get(P p, Q q);
}
