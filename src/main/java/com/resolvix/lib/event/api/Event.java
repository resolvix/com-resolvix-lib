package com.resolvix.lib.event.api;

/**
 * Defines the interface for a generic event.
 *
 */
public interface Event {

    /**
     * Returns the {@link EventCategory} of the {@link Event}.
     *
     * @return the category
     */
    <E extends EventCategory> E getCategory();
}
