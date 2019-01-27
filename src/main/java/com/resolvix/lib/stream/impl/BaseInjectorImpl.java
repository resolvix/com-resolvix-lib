package com.resolvix.lib.stream.impl;

import com.resolvix.lib.stream.api.Injector;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

abstract class BaseInjectorImpl<T, A, R>
    implements Injector<T, A, R>
{
    protected Set<Characteristics> characteristics;

    public BaseInjectorImpl(
        Collector.Characteristics... characteristics) {
        this.characteristics = new HashSet<>();
        this.characteristics.addAll(
            Arrays.asList(characteristics));
    }

    protected abstract A supply();

    protected abstract void accumulate(A a, T t);

    protected A combine(A left, A right) {
        //
        //  A {@code combine} operating cannot be supported where stream
        //  values are being injected to a single instance of a data
        //  structure.
        //
        throw new UnsupportedOperationException();
    }

    protected abstract R finish(A a);

    @Override
    public Supplier<A> supplier() {
        return this::supply;
    }

    @Override
    public BiConsumer<A, T> accumulator() {
        return this::accumulate;
    }

    @Override
    public BinaryOperator<A> combiner() {
        return this::combine;
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
