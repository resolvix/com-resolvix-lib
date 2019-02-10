package com.resolvix.lib.stream.api;

import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Defines the interface to a {@link StreamInjector}, a specialised form of
 * {@link Collector} that, given a function capable of accepting a stream
 * of values of type {@code T} to generate a value of type {@code R} and a
 * consumer of values of type {@code R}, consumes and buffers values of type
 * T, generates a value of type {@code R} and invokes the consumer with the
 * generated value.
 *
 * @param <T> the type of objects consumed by the injector, and the stream
 *  processor
 *
 * @param <A> the type of the accumulator for the intermediate stream buffer
 *
 * @param <R> the type of the output of the injector
 */
public interface StreamInjector<T, A, R>
    extends Injector<T, A, R> {

}
