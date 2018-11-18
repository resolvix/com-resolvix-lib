package com.resolvix.lib.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;

public class CartesianProductMappingCollector<T, I>
    implements Collector<T, CartesianProductMappingCollector.LocalAccumulator<T, I>, List<I>> {

    private Function<T, I> mappingFunction;

    private Function<T, I> partialMappingFunction;

    private BinaryOperator<I> foldingFunction;

    private BinaryOperator<I>  appendingPartialFunction;

    public static class LocalAccumulator<T, I> {

        public void transformAndAppend(T t) {

        }

        public LocalAccumulator<T, I> combine(LocalAccumulator<T, I> r) {
            return new LocalAccumulator<>();
        }

        public List<I> toList() {
            return new ArrayList<>();
        }
    }

    public CartesianProductMappingCollector(
        Function<T, I> mappingFunction,
        Function<T, I> partialMappingFunction,
        BinaryOperator<I> foldingFunction,
        BinaryOperator<I> appendingPartialFunction
    ) {
        this.mappingFunction = mappingFunction;
        this.partialMappingFunction = partialMappingFunction;
        this.foldingFunction = foldingFunction;
        this.appendingPartialFunction = appendingPartialFunction;
    }

    public static <T, I> CartesianProductMappingCollector<T, I> asList(
        Function<T, I> mappingFunction, Function<T, I> partialMappingFunction,
        BinaryOperator<I> foldingFunction, BinaryOperator<I> appendingPartialFunction
    ) {
        return new CartesianProductMappingCollector<>(
            mappingFunction, partialMappingFunction,
            foldingFunction, appendingPartialFunction
        );
    }

    @Override
    public Supplier<LocalAccumulator<T, I>> supplier() {
        return LocalAccumulator::new;
    }

    @Override
    public BiConsumer<LocalAccumulator<T, I>, T> accumulator() {
        return LocalAccumulator::transformAndAppend;
    }

    @Override
    public BinaryOperator<LocalAccumulator<T, I>> combiner() {
        return LocalAccumulator::combine;
    }

    @Override
    public Function<LocalAccumulator<T, I>, List<I>> finisher() {
        return LocalAccumulator::toList;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
