package com.resolvix.lib.stream.impl;

import com.resolvix.lib.stream.api.StreamInjector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class AltStreamInjectorImpl<T, U, R>
    extends BaseStreamInjectorImpl<T, U, R>
{
    public static class StreamBuffer<T, U>
        extends BaseStreamBuffer<U>
    {

    }

    private Function<Stream<T>, Stream<U>> operationT;

    private Function<Stream<U>, R> terminalU;

    private Consumer<R> consumerR;

    private Set<Collector.Characteristics> characteristics;

    public AltStreamInjectorImpl(
            Function<Stream<T>, Stream<U>> operationT,
            Function<Stream<U>, R> terminalU,
            Consumer<R> consumerR,
            Collector.Characteristics... characteristics) {
        this.operationT = operationT;
        this.terminalU = terminalU;
        this.consumerR = consumerR;
        this.characteristics = new HashSet<>();
        this.characteristics.addAll(
                Arrays.asList(characteristics));
    }

    @Override
    public Supplier<BaseStreamBuffer<U>> supplier() {
        return StreamBuffer::new;
    }

    @Override
    public BiConsumer<BaseStreamBuffer<U>, T> accumulator() {
        return null;
    }

//    @Override
//    public BiConsumer<BaseStreamBuffer<U>, T> accumulator() {
//
//        Stream<T> x;
//
//        x.
//
//        //return ;
//    }

    @Override
    public BinaryOperator<BaseStreamBuffer<U>> combiner() {
        return null;
    }

    @Override
    public Function<BaseStreamBuffer<U>, R> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
