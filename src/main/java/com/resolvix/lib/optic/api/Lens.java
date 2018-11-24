package com.resolvix.lib.optic.api;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Lens<T, U> extends Function<T, U> {

    <V> Lens<T, V> andThen(
        Function<U, V> getterU, BiConsumer<U, V> setterV, Supplier<V> supplierV
    );

    <V> Lens<T, V> andThen(
        Lens<U, V> lens
    );
}
