package com.resolvix.lib.stream.api;

import java.util.stream.Collector;

/**
 * Defines the interface to a {@link MultiplexedInjector}, a specialised form
 * of {@link Collector} that, given one or more collectors capable of consuming
 * an object of type {@code T}, collects
 *
 * @param <T>
 * @param <A>
 * @param <R>
 */
public interface MultiplexedInjector<T, R>
    extends Injector<T, R[]>
{

}
