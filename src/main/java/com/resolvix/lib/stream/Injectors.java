package com.resolvix.lib.stream;

import com.resolvix.lib.stream.api.Injector;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Injectors {

    private static <T, R> Injector<T, R> of(
            R r, BiConsumer<R, T> consumerRT) {

        return (Injector<T, R>) Collector.of(
                () -> r,
                consumerRT,
                null,
                Function.identity(),
                Collector.Characteristics.UNORDERED,
                Collector.Characteristics.IDENTITY_FINISH
        )
    }

    public static <T, R> Injector<T, R> injector(
                R r, BiConsumer<R, T> consumerRT) {
        return

    }
}
