package com.resolvix.lib.collector.api;

import java.util.List;
import java.util.stream.Collector;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Defines the interface to an accumulator for a {@link Collector} of
 * one or more elements of a data set that maps the input elements
 * to an output element before collection.
 *
 * @param <T> the input type
 *
 * @param <U> the output type
 */
public interface MappingAccumulator<T, U>
    extends Consumer<T>
{
    /**
     * Returns a {@link List} of the mapped input elements.
     *
     * @return the list of the mapped input elements
     */
    List<U> toList();

    /**
     * Returns a {@link Stream} of the mapped input elements.
     *
     * @return a stream of mapped input elements
     */
    Stream<U> stream();
}
