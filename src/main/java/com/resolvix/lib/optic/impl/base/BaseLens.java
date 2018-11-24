package com.resolvix.lib.optic.impl.base;

import com.resolvix.lib.optic.api.Lens;
import com.resolvix.lib.optic.impl.ComposedLensImpl;
import com.resolvix.lib.optic.impl.GetOrInitialiseLensImpl;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BaseLens<T, U>
    implements Lens<T, U> {

    protected abstract Lens<T, U> self();

    public <V> Lens<T, V> andThen(
        Function<U, V> getterV, BiConsumer<U, V> setterV, Supplier<V> supplierV
    ) {
        return new ComposedLensImpl(
            self(),
            new GetOrInitialiseLensImpl<>(getterV, setterV, supplierV));
    }

    public <V> Lens<T, V> andThen(
        Lens<U, V> lens
    ) {
        return new ComposedLensImpl<>(
            self(),
            lens
        );
    }
}
