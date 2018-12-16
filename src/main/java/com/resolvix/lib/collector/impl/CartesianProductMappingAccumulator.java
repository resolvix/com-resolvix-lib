package com.resolvix.lib.collector.impl;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

public class CartesianProductMappingAccumulator<T, K, U, V>
    implements Consumer<T>
{

    private Function<T, K> classifier;

    private Function<T, U> fullMapper;

    private Function<T, V> partialMapper;

    private BiFunction<U, V, U> fold;

    private BinaryOperator<U> combine;

    private Map<K, U> map;

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

    public void accept(T t) {
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

    @Deprecated
    public List<U> toList() {
        List<U> us = new ArrayList<>();
        us.addAll(map.values());
        return us;
    }

    public Set<U> toSet() {
        Set<U> us = new HashSet<>();
        us.addAll(map.values());
        return us;
    }

    public void appendTo(Set<U> setU) {
        setU.addAll(map.values());
    }

    public Stream<U> stream() {
        return map.values().stream();
    }
}
