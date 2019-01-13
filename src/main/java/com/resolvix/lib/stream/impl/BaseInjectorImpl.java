package com.resolvix.lib.stream.impl;

import com.resolvix.lib.stream.api.Injector;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

abstract class BaseInjectorImpl<T, R>
    implements Injector<T, R>
{
    protected Set<Characteristics> characteristics;

    public BaseInjectorImpl(
        Collector.Characteristics... characteristics) {
        this.characteristics = new HashSet<>();
        this.characteristics.addAll(
            Arrays.asList(characteristics));
    }

    protected abstract R supply();

    protected abstract void accumulate(R r, T t);

    protected R combine(R left, R right) {
        //
        //  A {@code combine} operating cannot be supported where stream
        //  values are being injected to a single instance of a data
        //  structure.
        //
        throw new UnsupportedOperationException();
    }

    protected R finish(R r) {
        return r;
    }

    @Override
    public Supplier<R> supplier() {
        return this::supply;
    }

    @Override
    public BiConsumer<R, T> accumulator() {
        return this::accumulate;
    }

    @Override
    public BinaryOperator<R> combiner() {
        return this::combine;
    }

    @Override
    public Function<R, R> finisher() {
        return this::finish;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(characteristics);
    }
}
