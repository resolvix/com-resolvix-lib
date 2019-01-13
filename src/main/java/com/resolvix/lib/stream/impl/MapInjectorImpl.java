package com.resolvix.lib.stream.impl;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;

public class MapInjectorImpl<T, K, V, R extends Map<K, V>>
    extends BaseInjectorImpl<T, R>
{
    @FunctionalInterface
    public interface Putter<R, K, T> {
        void put(R r, K k, T t);
    }

    public static class X<R, K, V, T> implements BiConsumer<R, T>
    {
        private Function<T, K> classifier;

        private Function<T, V> valuer;

        private Putter<R, K, V> putter;

        X(Function<T, K> classifier, Function<T, V> valuer, Putter<R, K, V> putter) {
            this.classifier = classifier;
            this.valuer = valuer;
            this.putter = putter;
        }

        @Override
        public void accept(R r, T t) {
            K k = classifier.apply(t);
            V v = valuer.apply(t);
            if (k == null)
                throw new RuntimeException("null classifier returned");

            putter.put(r, k, v);
        }
    }


    public MapInjectorImpl(
        R r,
        Function<T, K> classifier,
        Putter<R, K, T> putter,
        BinaryOperator<R> combiner,
        Collector.Characteristics... characteristics) {
        super(r, new X<>(classifier, Function.identity(), putter), combiner, characteristics);
    }

    public MapInjectorImpl(
        R r,
        Function<T, K> classifier,
        Function<T, V> valuer,
        Putter<R, K, V> putter,
        BinaryOperator<R> combiner,
        Collector.Characteristics... characteristics) {
        super(r, new X<>(classifier, valuer, putter), combiner, characteristics);
    }
}
