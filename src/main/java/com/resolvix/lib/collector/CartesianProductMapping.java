package com.resolvix.lib.collector;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CartesianProductMapping<T, K, U, V, W, A, D>
    implements Collector<T, A, D> {

    @Override
    public Supplier<A> supplier() {
        return null;
    }

    @Override
    public BiConsumer<A, T> accumulator() {
        return null;
    }

    @Override
    public BinaryOperator<A> combiner() {
        return null;
    }

    @Override
    public Function<A, D> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
