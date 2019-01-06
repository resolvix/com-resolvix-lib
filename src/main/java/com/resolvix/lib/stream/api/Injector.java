package com.resolvix.lib.stream.api;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Defines the interface to an {@link Injector} that, given a stream or a data
 * structure capable of generating a stream, will inject values from that
 * stream or data structure into the specified data structure using a mapper
 * function, and an associated consumer function.
 *
 * @param <T> the type of object representing the input to the
 *  {@link Injector}.
 *
 * @param <R> the type of object representing consumer of the input
 *  or a value mapped from the input.
 */
public interface Injector<T, R>
    extends Collector<T, R, R>
{

}
