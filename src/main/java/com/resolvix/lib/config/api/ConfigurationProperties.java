package com.resolvix.lib.config.api;

/**
 * Defines the interface for a lookup table of configuration
 * properties.
 *
 */
public interface ConfigurationProperties {

    /**
     * Returns the value of the property given by {@code name},
     * or null if the property is undefined by the configuration.
     *
     * @param name the name of the property
     * @param <T> the type of the property
     * @return the property value, if available; otherwise, null
     */
    <T> T get(String name);

    /**
     * Returns the value of the property given by {@code name},
     * or the value of {@code defaultValue} if the property is
     * undefined by the configuration.
     *
     * @param name the name of the property
     * @param defaultValue the default value for the property
     * @param <T> the type of the property
     * @return the property value, if available; otherwise, the value of
     *  defaultValue
     */
    <T> T get(String name, T defaultValue);
}
