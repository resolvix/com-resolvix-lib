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
    protected R r;

    protected BiConsumer<R, T> accumulator;

    protected BinaryOperator<R> combiner;

    protected Set<Characteristics> characteristics;

    public BaseInjectorImpl(
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
