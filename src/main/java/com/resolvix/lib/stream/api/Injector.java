package com.resolvix.lib.stream.api;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Defines the interface to an {@link Injector}, a specialised form of
 * {@link Collector} that, given a object capable of consuming objects
 * of type {@code T}, consumes them.
 *
 * @param <T> the type of object representing the input to the
 *  {@link Injector}
 *
 * @param <A> the type of object representing the accumulator for the stream
 *  of input objects
 *
 * @param <R> the type of object representing output of the {@link Injector}
 */
public interface Injector<T, A, R>
    extends Collector<T, A, R>
{

}
