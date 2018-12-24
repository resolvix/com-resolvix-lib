package com.resolvix.lib.stream.impl;

import com.resolvix.lib.stream.api.MappingMultiWayAccumulator;

import java.lang.reflect.Array;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;

@Deprecated
public class MappingMultiWayAccumulatorImpl<T>
    implements Consumer<T>
{

    public interface MapperCollectorPair<T, U, A, R> {

        Function<T, U> getMapper();

        Collector<U, A, R> getCollector();
    }

    public class XX<T, U, A, R>
        implements MapperCollectorPair<T, U, A, R>
    {

        private Function<T, U> mapper;

        private Collector<U, A, R> collector;

        public XX(Function<T, U> mapper, Collector<U, A, R> collector) {
            this.mapper = mapper;
            this.collector = collector;
        }

        public Function<T, U> getMapper() {
            return mapper;
        }

        public Collector<U, A, R> getCollector() {
            return collector;
        }
    }

    private class CollectorAccumulator<T, U, A, R>
        implements Consumer<T>
    {
        private Function<T, U> mapper;

        private Collector<U, A, R> collector;

        private A a;

        private BiConsumer<A, U> accumulate;

        private Function<A, R> finisher;

        CollectorAccumulator(Function<T, U> mapper, Collector<U, A, R> collector) {
            this.collector = collector;
            this.a = collector.supplier().get();
            this.mapper = mapper;
            this.accumulate = collector.accumulator();
            this.finisher = collector.finisher();
        }

        @Override
        public void accept(T t) {
            accumulate.accept(a, mapper.apply(t));
        }

        public R finish() {
            return finisher.apply(a);
        }
    }

    private CollectorAccumulator<T, ?, ?, ?>[] collectorAccumulators;

    @SuppressWarnings("unchecked")
    public MappingMultiWayAccumulatorImpl(
        MapperCollectorPair<T, ?, ?, ?>... mapperCollectorPairs
    ) {
        int i_max = mapperCollectorPairs.length;
        collectorAccumulators = (CollectorAccumulator<T, ?, ?, ?>[]) Array.newInstance(CollectorAccumulator[].class, i_max);
        for (int i = 0; i < i_max; i++) {
            MapperCollectorPair<T, ?, ?, ?> mapperCollectorPair = mapperCollectorPairs[i];
            CollectorAccumulator<T, ?, ?, ?> ca = new CollectorAccumulator(mapperCollectorPair.getMapper(), mapperCollectorPair.getCollector());
            collectorAccumulators[i] = ca;
        }
    }

    private <U> void accumulate(Function<T, U> mapper, BiConsumer<T, U> accumulator, T t) {
        accumulator.accept(t, mapper.apply(t));
    }

    public void accept(T t) {
        for (CollectorAccumulator<T, ?, ?, ?> collectorAccumulator : collectorAccumulators) {
            collectorAccumulator.accept(t);
        }
    }

    public MappingMultiWayAccumulatorImpl<T>
        combine(MappingMultiWayAccumulatorImpl<T> operand) {
        return null;
    }

    public Object[] finish() {
        int i_max = collectorAccumulators.length;
        Object[] output = new Object[i_max];
        for (int i = 0; i < i_max; i++) {
            output[i] = collectorAccumulators[i].finish();
        }
        return output;
    }
}
