package com.resolvix.lib.collector;

import com.google.common.collect.ImmutableSet;
import com.resolvix.lib.collector.api.MatrixAccumulator;
import com.resolvix.lib.collector.impl.MatrixAccumulatorImpl;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Matrix {

    private Matrix() { }


    public static class X { }


    public static class MatrixCollector<T,
        U, A1, R1, C1 extends Collector<U, A1, R1>,
        V, A2, R2, C2 extends Collector<V, A2, R2>>
        implements Collector<T, MatrixAccumulator, X>
    {
        private Collector<U, A1, R1> firstCollector;

        private Function<T, U> firstMapper;

        private Collector<V, A2, R2> secondCollector;

        private Function<T, V> secondMapper;

        MatrixCollector(
            Collector<U, A1, R1> firstCollector,
            Function<T, U> firstMapper,
            Collector<V, A2, R2> secondCollector,
            Function<T, V> secondMapper
        ) {
            this.firstCollector = firstCollector;
            this.firstMapper = firstMapper;

            this.secondCollector = secondCollector;
            this.secondMapper = secondMapper;
        }

        @Override
        public Supplier<MatrixAccumulator> supplier() {
            return null;
        }

        @Override
        public BiConsumer<MatrixAccumulator, T> accumulator() {
            return null;
        }

        @Override
        public BinaryOperator<MatrixAccumulator> combiner() {
            return null;
        }

        @Override
        public Function<MatrixAccumulator, X> finisher() {
            return null;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return null;
        }
    }

    public static <T, U, A1, R1, C1 extends Collector<U, A1, R1>,
        V, A2, R2, C2 extends Collector<V, A2, R2>> Collector<T, ?, X>
        matrix(
            Collector<U, A1, R1> firstCollector,
            Function<T, U> firstMapper,
            Collector<V, A2, R2> secondCollector,
            Function<T, V> secondMapper) {
        return new MatrixCollector<>( //T, U, A1, R1, C1, V, A2, R2, C2>(
            firstCollector,
            firstMapper,
            secondCollector,
            secondMapper);
    }
}
