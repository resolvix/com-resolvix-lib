package com.resolvix.lib.stream;

import com.resolvix.lib.stream.api.CollectorInjector;
import com.resolvix.lib.stream.api.Injector;
import com.resolvix.lib.stream.api.MultiplexedInjector;
import com.resolvix.lib.stream.api.StreamInjector;
import com.resolvix.lib.stream.impl.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Injectors {

    public static <T, R> Injector<T, R, R> of(
                R r, Consumer<T> consumerT) {
        return null;
    }

    public static <T, R> Injector<T, ?, R> of(
            Collector<T, ?, R> collector) {
        return null;
    }

    public static <T, R extends Collection<T>> Injector<T, ?, R> of(R r) {
        return new CollectionInjectorImpl<>(
                r,
                R::add,
                (R r1, R r2) -> {
                    //
                    //  The process of injecting a data stream into a specific
                    //  data structure cannot take place in parallel.
                    //
                    throw new UnsupportedOperationException();
                },
                Collector.Characteristics.UNORDERED,
                Collector.Characteristics.IDENTITY_FINISH);
    }

    public static <T, K, R extends Map<K, T>> Injector<T, ?, R> of(
            R r, Function<T, K> classifier) {
        return new MapInjectorImpl<T, K, T, R>(
            r,
            classifier,
            R::put,
            Collector.Characteristics.UNORDERED,
            Collector.Characteristics.IDENTITY_FINISH);
    }

    public static <T, K, V, R extends Map<K, V>> Injector<T, ?, R> of(
            R r, Function<T, K> classifier, Function<T, V> valuer) {
        return new MapInjectorImpl<>(
            r,
            classifier,
            valuer,
            R::put,
            Collector.Characteristics.UNORDERED,
            Collector.Characteristics.IDENTITY_FINISH);
    }

    public static <T, R> CollectorInjector<T, ?, R> of(
            Collector<T, ?, R> collectorT,
            Consumer<R> consumerR) {
        return new CollectorInjectorImpl<>(
                collectorT,
                consumerR,
                Collector.Characteristics.UNORDERED);
    }

    public static <T, R> StreamInjector<T, ?, R> of(
            Function<Stream<T>, R> processorT,
            Consumer<R> consumerR) {
        return new StreamInjectorImpl<>(
                processorT,
                consumerR,
                Collector.Characteristics.UNORDERED);
    }

    /**
     * Returns a {@link MultiplexedInjector}, an injector that injects values
     * of type {@code T} into one or more injectors that each, individually
     * and collectively, return a value of type {@code R}.
     *
     * @param classR the {@link Class} object representing the return type,
     *  {@code R}
     *
     * @param injectors zero, one or more instances of an {@link Injector}
     *
     * @param <T> the type accepted by each of {@link Injector} instance
     *
     * @param <R> the return type
     *
     * @return an array of values of type {@code R} containing the result
     *  returned by each of the injector instances, upon completion of
     *  stream processing
     */
    public static <T, R> Injector<T, R[], R[]> of(
            Class<R> classR,
            Injector<T, ?, ? extends R>... injectors
    ) {
        return new MultiplexedInjectorImpl<>(
                classR, injectors);
    }
}
