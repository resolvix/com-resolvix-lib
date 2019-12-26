package com.resolvix.lib.property.api;

/**
 * Defines an interface for a class or entity that supports the reading
 * of properties.
 *
 * @param <P> the property identifier type
 */
public interface PropertyReader<P> {

    /**
     * Returns the property associated with the class or entity instance
     * with the property identifier given by {@code id}.
     *
     * @param id the property identifier
     * @param <T> the property value type
     * @return the property, if present; null, otherwise
     */
    <T> Property<P, T> getProperty(P id);

    /**
     * Returns the property associated with the class or entity instance
     * with the property identified given by {@code id} or, if not present,
     * returns the property given by {@code defaultProperty}.
     *
     * @param id the property identifier
     * @param defaultProperty the default property value
     * @param <T> the property value type
     * @return the property, if present; {@code defaultProperty}, otherwise
     */
    <T> Property<P, T> getOrDefaultProperty(P id, Property<P, T> defaultProperty);
}
