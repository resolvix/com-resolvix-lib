package com.resolvix.lib.exception.api;

import java.util.function.Function;

public class ThrowableMaplet<E extends Throwable> {

    private Class<E> throwableClass;

    private Function<E, String> throwableTransformer;

    private ThrowableMaplet(
        Class<E> throwableClass,
        Function<E, String> throwableTransformer
    ) {
        this.throwableClass = throwableClass;
        this.throwableTransformer = throwableTransformer;
    }

    public static <E extends Throwable> ThrowableMaplet<E> of(
        Class<E> throwableClass,
        Function<E, String> throwableTransformer
    ) {
        return new ThrowableMaplet<>(
            throwableClass,
            throwableTransformer);
    }

    public Class<E> getThrowableClass() {
        return throwableClass;
    }

    public Function<E, String> getThrowableTransform() {
        return throwableTransformer;
    }
}
