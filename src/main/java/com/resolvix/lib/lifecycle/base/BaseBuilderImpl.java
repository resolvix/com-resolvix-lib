package com.resolvix.lib.lifecycle.base;

import com.resolvix.lib.lifecycle.api.Builder;

import java.util.function.Consumer;

/**
 * Provides an abstract base implementation of a {@link Builder}.
 *
 * @param <S> the {@link BaseBuilderImpl} subclass type
 * @param <T> the type the builder builds
 */
public abstract class BaseBuilderImpl<S extends BaseBuilderImpl<S, T>, T>
    implements Builder<T>
{

    /**
     * Returns {@code this} of the relevant subclass type.
     *
     * @return {@code this}
     */
    protected abstract S self();

    /**
     * Performs the builder operations given by the {@link Consumer}
     * represented by {@code onTrue} if {@code condition} evaluates to
     * {@code true}.
     *
     * @param condition the condition
     * @param onTrue the {@link Consumer} containing the relevant
     *  builder operations to be executed if {@code condition} evaluates
     *  to {@code true}
     * @return the builder
     */
    public S on(boolean condition, Consumer<S> onTrue) {
        S self = self();
        if (condition)
            onTrue.accept(self);
        return self;
    }

    /**
     * Performs the builder operations given by the {@link Consumer}
     * represented by {@code onTrue} or {@code onFalse} depending on
     * whether {@code condition} evaluates to {@code true} or {@code false}.
     *
     * @param condition the condition
     * @param onTrue the {@link Consumer} containing the relevant
     *  builder operations to be executed if {@code condition} evaluates
     *  to {@code true}
     * @param onFalse the {@link Consumer} containing the relevant
     *  builder operations to be executed if {@code condition} evaluates
     *  to {@code false}
     * @return the builder
     */
    public S on(boolean condition, Consumer<S> onTrue, Consumer<S> onFalse) {
        S self = self();
        if (condition)
            onTrue.accept(self);
        else
            onFalse.accept(self);
        return self;
    }
}
