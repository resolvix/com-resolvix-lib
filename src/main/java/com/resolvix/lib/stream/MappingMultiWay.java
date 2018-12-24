package com.resolvix.lib.stream;

import com.resolvix.lib.stream.impl.MappingMultiWayAccumulatorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;

@Deprecated
public class MappingMultiWay {

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

        MultiWayMappingCollectorBuilder() {
            this.mapperCollectorPairs = new ArrayList<>();
        }

        public <U, A, R> MultiWayMappingCollectorBuilder<T> mapperCollector(
            Function<T, U> mapper, Collector<U, A, R> collector) {
            mapperCollectorPairs.add(
                new LocalMapperCollectorPairImpl<>(mapper, collector));
            return this;
        }

        public Collector<T, ?, ?> build() {
            @SuppressWarnings("unchecked")
            MappingMultiWayAccumulatorImpl.MapperCollectorPair<T, ?, ?, ?>[] mcps = mapperCollectorPairs.toArray(
                (MappingMultiWayAccumulatorImpl.MapperCollectorPair<T, ?, ?, ?>[]) new Collector[] { });
            return Collector.of(
                () -> new MappingMultiWayAccumulatorImpl<>(mcps),
                MappingMultiWayAccumulatorImpl::accept,
                MappingMultiWayAccumulatorImpl::combine,
                MappingMultiWayAccumulatorImpl::finish,
                Collector.Characteristics.UNORDERED);
        }
    }

    public static <T> MultiWayMappingCollectorBuilder<T> getBuilder(
        Class<T> classT) {
        return new MultiWayMappingCollectorBuilder<>();
    }
}
