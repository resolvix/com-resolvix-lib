package com.resolvix.lib.config;

interface ValueParser<V> {

    /**
     * Parses {@code s} to produce a value of type {@link V}.
     *
     * @param s the string {@code s}
     * @return the value of type {@link V}
     */
    V parse(String s);
}
