package com.resolvix.lib.optic;

import com.resolvix.lib.optic.impl.GetOrInitialiseLensImpl;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Optic {

    public static <T, U> com.resolvix.lib.optic.api.Lens<T, U> toLens(
        Function<T, U> getterU, BiConsumer<T, U> setterU, Supplier<U> supplierU
    ) {
        return new GetOrInitialiseLensImpl<>(getterU, setterU, supplierU);
    }
}
