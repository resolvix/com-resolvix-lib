package com.resolvix.lib.stream;

import com.resolvix.lib.stream.api.Injector;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Injectors {

    public static class InjectorImpl<T, R>
        implements Injector<T, R>
    {
        private R r;

        private BiConsumer<R, T> accumulator;

        private BinaryOperator<R> combiner;

        Set<Collector.Characteristics> characteristics;

        InjectorImpl(
                R r,
                BiConsumer<R, T> accumulator,
                BinaryOperator<R> combiner,
                Collector.Characteristics... characteristics) {
            this.r = r;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.characteristics = new HashSet<>();
            this.characteristics.addAll(
                    Arrays.asList(characteristics));
        }

        @Override
        public Supplier<R> supplier() {
            return () -> r;
        }

        @Override
        public BiConsumer<R, T> accumulator() {
            return accumulator;
        }

        @Override
        public BinaryOperator<R> combiner() {
            return combiner;
        }

        @Override
        public Function<R, R> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(characteristics);
        }
    }

    /**
     *
     *
     * @param r
     * @param consumerRT
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Injector<T, R> of(
            R r, BiConsumer<R, T> consumerRT, BinaryOperator<R> combiner) {
        return (Injector<T, R>) new InjectorImpl(
                r,
                consumerRT,
                combiner,
                Collector.Characteristics.UNORDERED,
                Collector.Characteristics.IDENTITY_FINISH
        );
    }

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

    @SuppressWarnings("unchecked")
    public static <T, R extends Collection<T>> Injector<T, R> of(R r)
    {
        return of(
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

    public static <T> Injector<T, ?> of(
            Injector<T, ?>... injectors
    ) {
        return null;
    }
}
