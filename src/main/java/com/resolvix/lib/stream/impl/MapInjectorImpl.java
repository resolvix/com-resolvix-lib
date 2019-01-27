package com.resolvix.lib.stream.impl;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class MapInjectorImpl<T, K, V, R extends Map<K, V>>
    extends BaseInjectorImpl<T, R, R>
{
    private R r;

    private Function<T, K> classifier;

    private Function<T, V> valuer;

    private Putter<R, K, V> putter;

    @FunctionalInterface
    public interface Putter<R, K, T> {
        void put(R r, K k, T t);
    }

    public MapInjectorImpl(
        R r,
        Function<T, K> classifier,
        Putter<R, K, V> putter,
        Collector.Characteristics... characteristics) {
        super(characteristics);
        this.r = r;
        this.classifier = classifier;
        this.valuer = (Function<T, V>) Function.identity();
        this.putter = putter;
    }

    public MapInjectorImpl(
        R r,
        Function<T, K> classifier,
        Function<T, V> valuer,
        Putter<R, K, V> putter,
        Collector.Characteristics... characteristics) {
        super(characteristics);
        this.r = r;
        this.classifier = classifier;
        this.valuer = valuer;
        this.putter = putter;
    }

    @Override
    protected R supply() {
        return r;
    }

    @Override
    protected void accumulate(R r, T t) {
        K k = classifier.apply(t);
        V v = valuer.apply(t);
        if (k == null)
            throw new RuntimeException("null classifier returned");

        putter.put(r, k, v);
    }

    @Override
    protected R finish(R r) {
        return r;
    }
}
