package com.resolvix.lib.stream;

import com.resolvix.lib.stream.api.MatrixAccumulator;
import com.resolvix.lib.stream.impl.MatrixAccumulatorImpl;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@Deprecated
public class Matrix {

    private Matrix() { }


    public static class X<R1, R2> {

        R1 r1;

        R2 r2;

        public X(R1 r1, R2 r2) {
            this.r1 = r1;
            this.r2 = r2;
        }
    }

    public static class MatrixCollector<T,
        U, A1, R1, C1 extends Collector<U, A1, R1>,
        V, A2, R2, C2 extends Collector<V, A2, R2>>
        implements Collector<T, MatrixAccumulatorImpl<T, U, A1, R1, C1, V, A2, R2, C2>, X>
    {
        private C1 firstCollector;

        private Function<T, U> firstMapper;

        private C2 secondCollector;

        private Function<T, V> secondMapper;

        MatrixCollector(
            C1 firstCollector,
            Function<T, U> firstMapper,
            C2 secondCollector,
            Function<T, V> secondMapper
        ) {
            this.firstCollector = firstCollector;
            this.firstMapper = firstMapper;

            this.secondCollector = secondCollector;
            this.secondMapper = secondMapper;
        }

        @Override
        public Supplier<MatrixAccumulatorImpl<T, U, A1, R1, C1, V, A2, R2, C2>> supplier() {
            return () -> new MatrixAccumulatorImpl<T, U, A1, R1, C1, V, A2, R2, C2>(
                firstCollector, firstMapper,
                secondCollector, secondMapper);
        }

        @Override
        public BiConsumer<MatrixAccumulatorImpl<T, U, A1, R1, C1, V, A2, R2, C2>, T> accumulator() {
            return MatrixAccumulator::accept;
        }

        @Override
        public BinaryOperator<MatrixAccumulatorImpl<T, U, A1, R1, C1, V, A2, R2, C2>> combiner() {
            return MatrixAccumulatorImpl::combine;
        }

        @Override
        public Function<MatrixAccumulatorImpl<T, U, A1, R1, C1, V, A2, R2, C2>, X> finisher() {
            return MatrixAccumulatorImpl::finish;
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
