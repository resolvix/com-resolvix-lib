package com.resolvix.lib.property.api;

/**
 * Defines an interfacesfor a class or entity that supports the writing of
 * properties.
 *
 * @param <P> the property identifier type
 */
public interface PropertyWriter<P> {

    /**
     * Associates {@code value} with the property given by {@code id} with
     * the relevant class or entity instance.
     *
     * @param id the property identifier
     * @param value the property value
     * @param <T> the property value type
     */
    <T> void setProperty(P id, T value);

    /**
     * Associates {@code value} with the property given by {@code id} with
     * the relevant class or entity instance.
     *
     * @param id the property identifier
     * @param classT the property value type
     * @param value the property value
     * @param <T> the property value type
     */
    <T> void setProperty(P id, Class<T> classT, T value);

    /**
     * Associates the property, given by {@code property}, with the relevant
     * class or entity instance.
     *
     * @param property the property
     * @param <T> the property value type
     */
    <T> void setProperty(Property<P, T> property);

    /**
     * Disassociates the property identified by {@code id} with the relevant
     * class or entity instance.
     *
     * @param id the property identifier
     */
    void unsetProperty(P id);
}
