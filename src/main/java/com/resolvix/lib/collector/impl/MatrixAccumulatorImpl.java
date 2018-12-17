package com.resolvix.lib.collector.impl;

import com.resolvix.lib.collector.Matrix;
import com.resolvix.lib.collector.api.MatrixAccumulator;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class MatrixAccumulatorImpl<
    T,
    U, A1, R1, C1 extends Collector<U, A1, R1>,
    V, A2, R2, C2 extends Collector<V, A2, R2>>
    implements MatrixAccumulator<T, U, V>
{
    private Function<T, U> firstMapper;

    private C1 firstCollector;

    private A1 firstAccumulator;

    private BiConsumer<A1, U> firstAccumulate;

    private Function<T, V> secondMapper;

    private C2 secondCollector;

    private A2 secondAccumulator;

    private BiConsumer<A2, V> secondAccumulate;

    public MatrixAccumulatorImpl(
        C1 firstCollector,
        Function<T, U> firstMapper,
        C2 secondCollector,
        Function<T, V> secondMapper)
    {
        this.firstMapper = firstMapper;
        this.firstCollector = firstCollector;
        this.secondMapper = secondMapper;
        this.secondCollector = secondCollector;
    }

    @Override
    public void accept(T t) {
        U u = firstMapper.apply(t);
        firstAccumulate.accept(firstAccumulator, u);

        V v = secondMapper.apply(t);
        secondAccumulate.accept(secondAccumulator, v);
    }

    public MatrixAccumulatorImpl<T, U, A1, R1, C1, V, A2, R2, C2>
        combine(MatrixAccumulatorImpl<T, U, A1, R1, C1, V, A2, R2, C2> operand) {
        return null;
    }

    /*public static <T, U, A1, R1, C1 extends Collector<U, A1, R1>,
        V, A2, R2, C2 extends Collector<V, A2, R2>> MatrixAccumulatorImpl<T, U, A1, R1, C1, V, A2, R2, C2> combine(
        MatrixAccumulatorImpl<T, U, A1, R1, C1, V, A2, R2, C2> left,
        MatrixAccumulatorImpl<T, U, A1, R1, C1, V, A2, R2, C2> right
    ) {
        //A1 combinedFirstAccumulators = left.firstAccumulator


        //return new MatrixAccumulatorImpl<>(


        //)

        return null;
    }*/

    public Matrix.X finish() {
        return new Matrix.X<>(
            firstCollector.finisher().apply(firstAccumulator),
            secondCollector.finisher().apply(secondAccumulator)
        );
    }

    /*public static <T, U, A1, R1, C1 extends Collector<U, A1, R1>,
        V, A2, R2, C2 extends Collector<V, A2, R2>> Matrix.X finish(
        MatrixAccumulatorImpl<T, U, A1, R1, C1, V, A2, R2, C2> accumulator
    ) {
        C1 firstCollector = accumulator.firstCollector;
        C2 secondCollector = accumulator.secondCollector

        return new Matrix.X(
            accumulator.firstCollector.finisher().apply(firstAccumulator),
            accumulator.secondCollector.finisher().apply()
        );
    }*/
}
