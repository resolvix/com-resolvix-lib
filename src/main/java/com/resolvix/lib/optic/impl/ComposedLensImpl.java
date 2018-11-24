package com.resolvix.lib.optic.impl;

import com.resolvix.lib.optic.api.Lens;
import com.resolvix.lib.optic.impl.base.BaseLens;

public class ComposedLensImpl<T, U, V>
    extends BaseLens<T, V>
{
    private Lens<T, U> left;

    private Lens<U, V> right;

    public ComposedLensImpl(
        Lens<T, U> left, Lens<U, V> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    protected com.resolvix.lib.optic.api.Lens<T, V> self() {
        return this;
    }

    @Override
    public V apply(T t) {
        U u = left.apply(t);
        if (u != null)
            return right.apply(u);
        return null;
    }
}
