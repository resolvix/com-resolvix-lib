package com.resolvix.lib.collector;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

@Deprecated
public class CartesianProductMappingCollector<T, I, II>
    implements Collector<T, CartesianProductMappingListAccumulator<T, I, II>, List<I>> {

    private Function<T, I> mappingFunction;

    private Function<T, I> partialMappingFunction;

    private Function<T, II> indexMapping;

    private BinaryOperator<I> foldingFunction;

    private BinaryOperator<I>  appendingPartialFunction;

    public CartesianProductMappingCollector(
        Function<T, I> mappingFunction,
        Function<T, I> partialMappingFunction,
        BinaryOperator<I> foldingFunction,
        BinaryOperator<I> appendingPartialFunction,
        Function<T, II> indexMapping
    ) {
        this.mappingFunction = mappingFunction;
        this.partialMappingFunction = partialMappingFunction;
        this.foldingFunction = foldingFunction;
        this.appendingPartialFunction = appendingPartialFunction;
        this.indexMapping = indexMapping;
    }

    public static <T, I, II, IP> CartesianProductMappingCollector<T, I, II> asList(
        Function<T, I> mappingFunction, Function<T, I> partialMappingFunction,
        BinaryOperator<I> foldingFunction, BinaryOperator<I> appendingPartialFunction,
        Function<T, II> indexMapping, Function<T, IP> indexPartialMapping
    ) {
        return new CartesianProductMappingCollector<>(
            mappingFunction, partialMappingFunction,
            foldingFunction, appendingPartialFunction,
            indexMapping
        );
    }

    @Override
    public Supplier<CartesianProductMappingListAccumulator<T, I, II>> supplier() {
        return new Supplier<CartesianProductMappingListAccumulator<T, I, II>>() {
            @Override
            public CartesianProductMappingListAccumulator<T, I, II> get() {
                return new CartesianProductMappingListAccumulator<>(
                    mappingFunction,
                    partialMappingFunction,
                    indexMapping,
                    foldingFunction,
                    appendingPartialFunction
                );
            }
        };
    }

    @Override
    public BiConsumer<CartesianProductMappingListAccumulator<T, I, II>, T> accumulator() {
        return CartesianProductMappingListAccumulator::transformAndAppend;
    }

    @Override
    public BinaryOperator<CartesianProductMappingListAccumulator<T, I, II>> combiner() {
        return CartesianProductMappingListAccumulator::combine;
    }

    @Override
    public Function<CartesianProductMappingListAccumulator<T, I, II>, List<I>> finisher() {
        return CartesianProductMappingListAccumulator::toList;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
