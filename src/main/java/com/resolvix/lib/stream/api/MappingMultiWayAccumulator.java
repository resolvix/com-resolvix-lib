package com.resolvix.lib.stream.api;

import java.util.function.Consumer;
import java.util.stream.Collector;

/**
 * @deprecated
 *
 * Defines the interface to an accumulator for a {@link Collector} of
 * one or more elements of a data set that form two different views
 * of the input elements, using two separate {@link Collector}.
 *
 * @param <T> the input type
 */
@Deprecated
public interface MappingMultiWayAccumulator<T>
    extends Consumer<T>
{

}