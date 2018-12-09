package com.resolvix.lib.collector;

import com.google.common.collect.ImmutableSet;
import com.resolvix.lib.collector.impl.CartesianProductMappingAccumulator;
import com.resolvix.lib.collector.impl.CollectorImpl;

import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;

public class CartesianProductMapping
{
    //implements Collector<T, CartesianProductMappingAccumulator<T, K, U, V>, D> {

//    private final Function<T, K> classifier;
//
//    private final Function<T, U> fullMapper;
//
//    private final Function<T, V> partialMapper;
//
//    private final BiFunction<U, V, U> fold;
//
//    private final BinaryOperator<U> combine;

//    private CartesianProductMappingAccumulator<T, K, U, V> supply() {
//        return new CartesianProductMappingAccumulator<T, K, U, V>(
//            classifier, fullMapper, partialMapper, fold, combine);
//    }

// <T, K, U, V, W, D>


    public static <T, K, U, V, D, R> Collector<T, CartesianProductMappingAccumulator<T, K, U, V>, R>
        toCartesianProductMappingCollector(
                Function<T, K> classifier,
                Function<T, U> fullMapper,
                Function<T, V> partialMapper,
                BiFunction<U, V, U> fold,
                BinaryOperator<U> combine
    ) {
        return new CollectorImpl<>(
            () -> new CartesianProductMappingAccumulator<T, K, U, V>(
                classifier, fullMapper, partialMapper, fold, combine),
            CartesianProductMappingAccumulator::accept,
            CartesianProductMappingAccumulator::combine,
            null,
            ImmutableSet.of(Collector.Characteristics.UNORDERED)
        );
    }
}
