package com.resolvix.lib.stream;

import com.resolvix.lib.stream.api.Injector;
import com.resolvix.lib.stream.api.MultiplexedInjector;
import com.resolvix.lib.stream.impl.CollectionInjectorImpl;
import com.resolvix.lib.stream.impl.MapInjectorImpl;
import com.resolvix.lib.stream.impl.MultiplexedInjectorImpl;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Injectors {



    /**
     * Returns a new {@link Injector}.
     *
     * @param r the data structure for the injected objects
     *
     * @param consumerRT to method responsible for adding the injected objects
     *  to the data structure, {@code r}
     *
     * @param <T> the type of injected object
     *
     * @param <R> the type of the data structure for the injected objects.
     *
     * @return an {@link Injector<T, R>} that accepts the injected objects,
     *  {@link T}, and appends them to the data structure {@code r}.
     *
    public static <T, R> Injector<T, R, R> of(
            R r, BiConsumer<R, T> consumerRT, BinaryOperator<R> combiner) {
        return (Injector<T, R, R>) new SimpleInjectorImpl(
                r,
                consumerRT,
                combiner,
                Collector.Characteristics.UNORDERED,
                Collector.Characteristics.IDENTITY_FINISH
        );
    }*/

    public static <T, R> Injector<T, R> of(
                R r, Consumer<T> consumerT) {
        return null;
    }

    /*public static class InjectorImpl<T, R>
        implements Injector<T, R>
    {


        @Override
        public Supplier<R> supplier() {
            return null;
        }

        @Override
        public BiConsumer<R, T> accumulator() {
            return null;
        }

        @Override
        public BinaryOperator<R> combiner() {
            return null;
        }

        @Override
        public Function<R, R> finisher() {
            return null;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return null;
        }
    }*/

    public static <T, R> Injector<T, R> of(
            Collector<T, ?, R> collector
    ) {
        return null;
    }

    public static <T, R extends Collection<T>> Injector<T, R> of(R r)
    {
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

    public static <T, K, R extends Map<K, T>> Injector<T, R> of(
            R r, Function<T, K> classifier)
    {
        return new MapInjectorImpl<T, K, T, R>(
            r,
            classifier,
            R::put,
            Collector.Characteristics.UNORDERED,
            Collector.Characteristics.IDENTITY_FINISH);
    }

    public static <T, K, V, R extends Map<K, V>> Injector<T, R> of(
            R r, Function<T, K> classifier, Function<T, V> valuer)
    {
        return new MapInjectorImpl<>(
            r,
            classifier,
            valuer,
            R::put,
            Collector.Characteristics.UNORDERED,
            Collector.Characteristics.IDENTITY_FINISH);
    }

    public static <T, R> Injector<T, R> of(
            Collector<T, ?, R> collectorT,
            Consumer<R> consumerR) {
        return new CollectorInjectorImpl<>(
                collectorT,
                consumerR,
                Collector.Characteristics.UNORDERED,
                Collector.Characteristics.IDENTITY_FINISH);
    }

    /*@SuppressWarnings("unchecked")
    public static <T, R extends Set<T>> Injector<T, R> of(R r)
    {
        return (Injector<T, R>) of(
                r,
                (R rX, T t) -> { rX.add(t); },
                (R r1, R r2) -> {
                    if (r1.size() < r2.size()) {
                        r2.addAll(r1);
                        return r2;
                    } else {
                        r1.addAll(r2);
                        return r1;
                    }
                });
    }*/

    /*public static <T> Injector<T, Object[]> of(
        Injector<T, ?>... injectors
    ) {
        return new MultiplexedInjectorImpl<T, Object[]>(
                Object.class, injectors);
    }*/

    /**
     * Returns a {@link MultiplexedInjector}, an injector that injects values
     * of type {@code T} into one or more injectors that each, individually
     * and collectively, return a value of type {@code R}.
     *
     * @param classR the {@link Class} object representing the return type,
     *  {@code R}
     * @param injectors zero, one or more instances of an {@link Injector}
     * @param <T> the type accepted by each of {@link Injector} instance
     * @param <R> the return type
     * @return an array of values of type {@code R} containing the result
     *  returned by each of the injector instances, upon completion of
     *  stream processing
     */
    public static <T, R> Injector<T, R[]> of(
            Class<R> classR,
            Injector<T, R>... injectors
    ) {
        return new MultiplexedInjectorImpl<>(
                classR, injectors);
    }
}
