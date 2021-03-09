package com.resolvix.lib.exception.api;

import java.util.function.Function;

public class ThrowableMaplet<E extends Throwable, R> {

    private Class<E> throwableClass;

    private Function<E, R> throwableTransformer;

    private ThrowableMaplet(
        Class<E> throwableClass,
        Function<E, R> throwableTransformer
    ) {
        this.throwableClass = throwableClass;
        this.throwableTransformer = throwableTransformer;
    }

    public static <E extends Throwable, R> ThrowableMaplet<E, R> of(
        Class<E> throwableClass,
        Function<E, R> throwableTransformer
    ) {
        return new ThrowableMaplet<>(
            throwableClass,
            throwableTransformer);
    }

    public Class<E> getThrowableClass() {
        return throwableClass;
    }

    public Function<E, R> getThrowableTransform() {
        return throwableTransformer;
    }
}
