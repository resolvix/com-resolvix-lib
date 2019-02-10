package com.resolvix.lib.stream.api;

import java.util.stream.Collector;

/**
 * Represents an {@link Injector} that consumes objects of type {@code T},
 * for onward processing by a downstream {@link Collector} and, upon
 * the finisher of the injector being invoked, proceeds to finish the
 * accumulator, and performs an additional step to assign the output
 * of the downstream collector finisher to an appropriate location for
 * subsequent use.
 *
 * @param <T> the type of objects consumed by the injector and downstream
 *  collector
 *
 * @param <A> the type of the accumulator of the downstream collector
 *
 * @param <R> the type of the output of the downstream collector
 */
public interface CollectorInjector<T, A, R>
    extends Injector<T, A, R> {

}
