package com.resolvix.lib.optic.impl;

import com.resolvix.lib.optic.api.Lens;
import com.resolvix.lib.optic.impl.base.BaseLens;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class GetOrInitialiseLensImpl<T, U>
    extends BaseLens<T, U>
{
    private Supplier<U> supplierU;

    private Function<T, U> getterU;

    private BiConsumer<T, U> setterU;

    public GetOrInitialiseLensImpl(Function<T, U> getterU, BiConsumer<T, U> setterU, Supplier<U> supplierU) {
        this.getterU = getterU;
        this.setterU = setterU;
        this.supplierU = supplierU;
    }

    @Override
    protected Lens<T, U> self() {
        return this;
    }

    @Override
    public U apply(T t) {
        if (t == null)
            throw new IllegalStateException();
        U u = getterU.apply(t);
        if (u == null) {
            u = supplierU.get();
            setterU.accept(t, u);
        }
        return u;
    }
}
