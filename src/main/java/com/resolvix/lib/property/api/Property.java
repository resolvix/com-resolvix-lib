package com.resolvix.lib.property.api;

import com.google.common.collect.TreeTraverser;

/**
 * Defines an interface for a property associated with a class or entity
 * instance.
 *
 * @param <P> the property identifier type
 */
public interface Property<P, T> {

    /**
     * Returns the property identifier.
     *
     * @return the property identifier
     */
    P getId();

    /**
     * Returns the value type.
     *
     * @return the value type
     */
    Class<T> getType();

    /**
     * Returns the value.
     *
     * @return the value
     */
    T getValue();
}
