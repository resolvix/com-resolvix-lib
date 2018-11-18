package com.resolvix.lib.collector;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

public class CartesianProductMappingCollector<T, I, II, IP>
    implements Collector<T, CartesianProductMappingCollector.LocalAccumulator<T, I, II, IP>, List<I>> {

    private Function<T, I> mappingFunction;

    private Function<T, I> partialMappingFunction;

    private Function<T, II> indexMapping;

    private Function<T, IP> indexPartialMapping;

    private BinaryOperator<I> foldingFunction;

    private BinaryOperator<I>  appendingPartialFunction;

    public static class LocalAccumulator<T, I, II, IP> {

        private Function<T, I> mappingFunction;

        private Function<T, I> partialMappingFunction;

        private Function<T, II> indexMapping;

        private Function<T, IP> indexPartialMapping;

        private BinaryOperator<I> foldingFunction;

        private BinaryOperator<I>  appendingPartialFunction;

        private Map<II, I> mapI;

        LocalAccumulator(
            Function<T, I> mappingFunction, Function<T, I> partialMappingFunction,
            Function<T, II> indexMapping, Function<T, IP> indexPartialMapping,
            BinaryOperator<I> foldingFunction) {
            this.mappingFunction = mappingFunction;
            this.partialMappingFunction = partialMappingFunction;
            this.indexMapping = indexMapping;
            this.indexPartialMapping = indexPartialMapping;
            this.foldingFunction = foldingFunction;
        }

        public void transformAndAppend(T t) {
            II ii = indexMapping.apply(t);
            I i = mapI.get(ii);
            if (i == null) {
                i = mappingFunction.apply(t);
                mapI.put(ii, i);
            } else {
                I ip = partialMappingFunction.apply(t);
                appendingPartialFunction.apply(i, ip);
            }
        }

        public LocalAccumulator<T, I, II, IP> combine(LocalAccumulator<T, I, II, IP> r) {
            Map<II, I> inMapI, outMapI;
            if (mapI.size() >= r.mapI.size()) {
                inMapI = r.mapI;
                outMapI = mapI;
            } else {
                inMapI = mapI;
                outMapI = r.mapI;
            }

            inMapI.forEach((II ii, I i) -> {
                I destI = outMapI.get(ii);
                I foldedI = foldingFunction.apply(destI, i);
                outMapI.put(ii, foldedI);
            });

            this.mapI = outMapI;
            return this;
        }

        public List<I> toList() {
            List<I> listI = new ArrayList<>();
            listI.addAll(mapI.values());
            return listI;
        }
    }

    public CartesianProductMappingCollector(
        Function<T, I> mappingFunction,
        Function<T, I> partialMappingFunction,
        BinaryOperator<I> foldingFunction,
        BinaryOperator<I> appendingPartialFunction,
        Function<T, II> indexMapping,
        Function<T, IP> indexPartialMapping
    ) {
        this.mappingFunction = mappingFunction;
        this.partialMappingFunction = partialMappingFunction;
        this.foldingFunction = foldingFunction;
        this.appendingPartialFunction = appendingPartialFunction;
        this.indexMapping = indexMapping;
        this.indexPartialMapping = indexPartialMapping;
    }

    public static <T, I, II, IP> CartesianProductMappingCollector<T, I, II, IP> asList(
        Function<T, I> mappingFunction, Function<T, I> partialMappingFunction,
        BinaryOperator<I> foldingFunction, BinaryOperator<I> appendingPartialFunction,
        Function<T, II> indexMapping, Function<T, IP> indexPartialMapping
    ) {
        return new CartesianProductMappingCollector<>(
            mappingFunction, partialMappingFunction,
            foldingFunction, appendingPartialFunction,
            indexMapping, indexPartialMapping
        );
    }

    @Override
    public Supplier<LocalAccumulator<T, I, II, IP>> supplier() {
        return new Supplier<LocalAccumulator<T, I, II, IP>>() {
            @Override
            public LocalAccumulator<T, I, II, IP> get() {
                return new LocalAccumulator<>(
                    mappingFunction, partialMappingFunction,
                    indexMapping, indexPartialMapping,
                    foldingFunction
                );
            }
        };
    }

    @Override
    public BiConsumer<LocalAccumulator<T, I, II, IP>, T> accumulator() {
        return LocalAccumulator::transformAndAppend;
    }

    @Override
    public BinaryOperator<LocalAccumulator<T, I, II, IP>> combiner() {
        return LocalAccumulator::combine;
    }

    @Override
    public Function<LocalAccumulator<T, I, II, IP>, List<I>> finisher() {
        return LocalAccumulator::toList;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
