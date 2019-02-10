package com.resolvix.lib.stream.impl;

import com.resolvix.lib.stream.api.CollectorInjector;
import com.resolvix.lib.stream.api.Injector;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;

/**
 * Reference implementation of the {@link CollectorInjector} interface,
 * enabling a downstream {@link Collector} to be fed through the general
 * {@link Injector} interface, and the output from the collector on
 * conclusion of the stream to be output to a method satisfying the
 * {@link Consumer} interface requirements.
 *
 * @param <T> the type of objects consumed by the injector and downstream
 *  collector
 *
 * @param <A> the type of the accumulator of the downstream collector
 *
 * @param <R> the type of the output of the downstream collector
 */
public class CollectorInjectorImpl<T, A, R>
    implements CollectorInjector<T, A, R>
{
    private Collector<T, A, R> collectorT;

    private Consumer<R> consumerR;

    private Set<Collector.Characteristics> characteristics;

    public CollectorInjectorImpl(
            Collector<T, A, R> collectorT,
            Consumer<R> consumerR,
            Collector.Characteristics... characteristics) {
        this.collectorT = collectorT;
        this.consumerR = consumerR;
        this.characteristics = new HashSet<>();
        this.characteristics.addAll(
                Arrays.asList(characteristics));
    }

    @Override
    public Supplier<A> supplier() {
        return () -> collectorT.supplier().get();
    }

    @Override
    public BiConsumer<A, T> accumulator() {
        return collectorT.accumulator();
    }

    @Override
    public BinaryOperator<A> combiner() {
        return collectorT.combiner();
    }

    private R finish(A accumulator) {
        R r = collectorT.finisher().apply(accumulator);
        //@SuppressWarnings("unchecked")
        //S s = (S) r;
        consumerR.accept(r);
        return r;
    }

    @Override
    public Function<A, R> finisher() {
        return this::finish;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(characteristics);
    }
}
