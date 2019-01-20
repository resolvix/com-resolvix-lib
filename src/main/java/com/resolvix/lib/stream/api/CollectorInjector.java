package com.resolvix.lib.stream.api;

import java.util.stream.Collector;

import com.resolvix.lib.stream.api.CollectorInjector.AccumulatorWrapper;

public interface CollectorInjector<T, A extends AccumulatorWrapper<T, A, R>, R>
    extends Collector<T, A, R> {

    interface AccumulatorWrapper<T, A, R> {

        void accumulate(T t);

        A combine(A operand);

        R finish();
    }
}
