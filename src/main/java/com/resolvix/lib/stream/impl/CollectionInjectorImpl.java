package com.resolvix.lib.stream.impl;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CollectionInjectorImpl<T, R extends Collection<T>>
    extends BaseInjectorImpl<T, R, R>
{
    private R r;

    public CollectionInjectorImpl(
        R r,
        BiConsumer<R, T> accumulator,
        BinaryOperator<R> combiner,
        Collector.Characteristics... characteristics) {
        super(characteristics);
        this.r = r;
    }

    @Override
    protected R supply() {
        return r;
    }

    @Override
    protected void accumulate(R r, T t) {
        //
        //  The {@code accumlate} method does not require an implementation
        //  because the {@link accumulator} method, below, returns
        //  {@link R#add}.
        //
        throw new UnsupportedOperationException();
    }

    @Override
    protected R finish(R r) {
        return r;
    }

    @Override
    public BiConsumer<R, T> accumulator() {
        return R::add;
    }
}