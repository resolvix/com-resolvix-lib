package com.resolvix.lib.stream.impl;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;

public class CollectionInjectorImpl<T, R extends Collection<T>>
    extends BaseInjectorImpl<T, R>
{
    public CollectionInjectorImpl(
        R r,
        BiConsumer<R, T> accumulator,
        BinaryOperator<R> combiner,
        Collector.Characteristics... characteristics) {
        super(r, accumulator, combiner, characteristics);
    }
}
