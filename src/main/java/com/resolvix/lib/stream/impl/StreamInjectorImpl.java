package com.resolvix.lib.stream.impl;

import com.resolvix.lib.stream.api.StreamInjector;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class StreamInjectorImpl<T, R>
    implements StreamInjector<T, StreamInjectorImpl.StreamBuffer<T>, R> {

    private Function<Stream<T>, R> processorT;

    private Consumer<R> consumerR;

    private Set<Collector.Characteristics> characteristics;

    public static class StreamBuffer<T>
        implements Stream.Builder<T> {

        private List<T> buffer;

        private StreamBuffer() {
            this.buffer = new ArrayList<>();
        }

        @Override
        public void accept(T t) {
            buffer.add(t);
        }

        @Override
        public Stream<T> build() {
            return buffer.stream();
        }

        private static <T> StreamBuffer<T> combine(
            StreamBuffer<T> left,
            StreamBuffer<T> right) {
            if (left.buffer.size() >= right.buffer.size()) {
                left.buffer.addAll(right.buffer);
                return left;
            } else {
                right.buffer.addAll(left.buffer);
                return right;
            }
        }
    }

    public StreamInjectorImpl(
            Function<Stream<T>, R> processorT,
            Consumer<R> consumerR,
            Collector.Characteristics... characteristics) {
        this.processorT = processorT;
        this.consumerR = consumerR;
        this.characteristics = new HashSet<>();
        this.characteristics.addAll(
                Arrays.asList(characteristics));
    }

    @Override
    public Supplier<StreamBuffer<T>> supplier() {
        return StreamBuffer::new;
    }

    @Override
    public BiConsumer<StreamBuffer<T>, T> accumulator() {
        return StreamBuffer::accept;
    }

    @Override
    public BinaryOperator<StreamBuffer<T>> combiner() {
        return StreamBuffer::combine;
    }

    private R finish(StreamBuffer<T> buffer) {
        Stream<T> streamT = buffer.build();
        R r = processorT.apply(buffer.build());
        consumerR.accept(r);
        return r;
    }

    @Override
    public Function<StreamBuffer<T>, R> finisher() {
        return this::finish;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(characteristics);
    }
}
