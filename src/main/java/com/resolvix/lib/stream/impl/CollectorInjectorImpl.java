package com.resolvix.lib.stream.impl;

import com.resolvix.lib.stream.api.CollectorInjector;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;

public class CollectorInjectorImpl<T, A, R>
    implements CollectorInjector<T, CollectorInjectorImpl.LocalAccumulatorWrapper<T, A, R>, R>
{
    private Collector<T, A, R> collectorT;

    private Consumer<R> consumerR;

    private Set<Collector.Characteristics> characteristics;

    public static class LocalAccumulatorWrapper<T, A ,R>
        implements AccumulatorWrapper<T, LocalAccumulatorWrapper<T, A, R>, R>
    {
        private A accumulator;

        private BiConsumer<A, T> accumulate;

        private BinaryOperator<A> combine;

        private Function<A, R> finish;

       public LocalAccumulatorWrapper(
                A accumulator,
                BiConsumer<A, T> accumulate,
                BinaryOperator<A> combine,
                Function<A, R> finish) {
            this.accumulator = accumulator;
            this.accumulate = accumulate;
            this.combine = combine;
            this.finish = finish;
        }

        public void accumulate(T t) {
            accumulate.accept(accumulator, t);
        }

        @Override
        public LocalAccumulatorWrapper<T, A, R> combine(
                LocalAccumulatorWrapper<T, A, R> operand) {
            return new LocalAccumulatorWrapper<T, A, R>(
                    combine.apply(
                            accumulator,
                            operand.accumulator),
                    accumulate,
                    combine,
                    finish);

        }

        public R finish() {
            return finish.apply(accumulator);
        }
    }

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

    private static <T, A, R> A of(
            Collector<T, A, R> collectorT) {
        return new LocalAccumulatorWrapper<T, A, R>(
            collectorT.supplier().get(),
            collectorT.accumulator(),
            collectorT.combiner(),
            collectorT.finisher()
        );
    }

    @Override
    public Supplier<A> supplier() {
        return () -> of(collectorT);
    }

    @Override
    public BiConsumer<AccumulatorWrapper<T, A, R>, T> accumulator() {
        return AccumulatorWrapper::accumulate;
    }

    @Override
    public BinaryOperator<AccumulatorWrapper<T, A, R>> combiner() {
        return AccumulatorWrapper::combine;
    }

    @Override
    public Function<AccumulatorWrapper<T, A, R>, R> finisher() {
        return AccumulatorWrapper::finish;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(characteristics);
    }
}
