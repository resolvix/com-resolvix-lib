package com.resolvix.lib.collector;

import com.resolvix.lib.collector.api.MappingMultiWayAccumulator;
import com.resolvix.lib.collector.impl.MappingMultiWayAccumulatorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;

public class MultiWayMapping {

    private static class LocalMapperCollectorPairImpl<T, U, A, R>
        implements MappingMultiWayAccumulatorImpl.MapperCollectorPair<T, U, A, R>
    {
        private Function<T, U> mapper;

        private Collector<U, A, R> collector;

        LocalMapperCollectorPairImpl(Function<T, U> mapper, Collector<U, A, R> collector) {
            this.mapper = mapper;
            this.collector = collector;
        }

        @Override
        public Function<T, U> getMapper() {
            return null;
        }

        @Override
        public Collector<U, A, R> getCollector() {
            return null;
        }
    }

    public static class MultiWayMappingCollectorBuilder<T> {

        private List<LocalMapperCollectorPairImpl<T, ?, ?, ?>> mapperCollectorPairs;

        <T> MultiWayMappingCollectorBuilder() {
            this.mapperCollectorPairs = new ArrayList<>();
        }

        public <U, A, R> MultiWayMappingCollectorBuilder<T> mapperCollector(
            Function<T, U> mapper, Collector<U, A, R> collector) {
            mapperCollectorPairs.add(
                new LocalMapperCollectorPairImpl<>(mapper, collector));
            return this;
        }

        public MappingMultiWayAccumulator<T> build() {
            @SuppressWarnings("unchecked")
            MappingMultiWayAccumulatorImpl.MapperCollectorPair<T, ?, ?, ?>[] mcps = mapperCollectorPairs.toArray(
                (MappingMultiWayAccumulatorImpl.MapperCollectorPair<T, ?, ?, ?>[]) new Collector[] { });
            return new MappingMultiWayAccumulatorImpl<>(mcps);
        }
    }

    public static <T> MultiWayMappingCollectorBuilder<T> getBuilder() {
        return new MultiWayMappingCollectorBuilder<>();
    }
}
