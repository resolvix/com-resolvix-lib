package com.resolvix.lib.stream.api;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Defines the interface to an {@link Injector} that, given a stream or a data
 * structure capable of generating a stream, will inject values from that data
 * structure into the specified data structure using a mapper function, and
 * an consumer function.
 *
 * @param <T>
 */
public interface Injector<T, R>
    extends Collector<T, R, R>
{

}
