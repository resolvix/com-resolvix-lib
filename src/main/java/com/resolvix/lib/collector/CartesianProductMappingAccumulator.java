package com.resolvix.lib.collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

class CartesianProductMappingAccumulator<T, K, U, V> {

    Function<T, K> classifier;

    Function<T, U> fullMapper;

    Function<T, V> partialMapper;

    BiFunction<U, V, U> fold;

    BinaryOperator<U> combine;

    Map<K, U> map;

    public CartesianProductMappingAccumulator(
        Function<T, K> classifier,
        Function<T, U> fullMapper,
        Function<T, V> partialMapper,
        BiFunction<U, V, U> fold,
        BinaryOperator<U> combine
    ) {
        this.classifier = classifier;
        this.fullMapper = fullMapper;
        this.partialMapper = partialMapper;
        this.fold = fold;
        this.combine = combine;
        this.map = new HashMap<>();
    }

    public void accumulate(T t) {
        K k = classifier.apply(t);
        U u = map.get(k);
        if (u == null) {
            u = fullMapper.apply(t);
            map.put(k, u);
        } else {
            V v = partialMapper.apply(t);
            fold.apply(u, v);
        }
    }

    public CartesianProductMappingAccumulator<T, K, U, V> combine(
        CartesianProductMappingAccumulator<T, K, U, V> operand)
    {
        Map<K, U> mapIn, mapOut;
        if (map.size() >= operand.map.size()) {
            mapIn = operand.map;
            mapOut = map;
        } else {
            mapIn = map;
            mapOut = operand.map;
        }

        mapIn.forEach((K k, U u) -> {
            U destinationU = mapOut.get(k);
            U combinedU = combine.apply(destinationU, u);
            mapOut.put(k, combinedU);
        });

        this.map = mapOut;
        return this;
    }

    public List<U> toList() {
        List<U> us = new ArrayList<>();
        us.addAll(map.values());
        return us;
    }
}
