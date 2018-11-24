package com.resolvix.lib.lens;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Lens<T, U>
    implements Function<T, U>
{
    private Supplier<U> supplierU;

    private Function<T, U> getterU;

    private BiConsumer<T, U> setterU;

    public Lens(Supplier<U> supplierU, Function<T, U> getterU, BiConsumer<T, U> setterU) {
        this.supplierU = supplierU;
        this.getterU = getterU;
        this.setterU = setterU;
    }

    public static <T, U> Lens<T, U> toLens(
        Supplier<U> supplierU, Function<T, U> getterU, BiConsumer<T, U> setterU
    ) {
        return new Lens(supplierU, getterU, setterU);
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

    public <V> Lens<T, V> andThen(
        Supplier<V> supplierV, Function<U, V> getterV, BiConsumer<U, V> setterV
    ) {
        return new Lens<T, V> (
            supplierV,
            getterU.andThen(getterV),
            (T t, V v) -> {
                setterV.accept(
                    getterU.apply(t), v);
            }
        );
    }
}
