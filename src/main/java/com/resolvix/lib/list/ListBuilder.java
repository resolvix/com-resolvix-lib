package com.resolvix.lib.list;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListBuilder<L extends List<T>, T> {

    private L ts;

    private ListBuilder(L ts) {
        this.ts = ts;
    }

    public static <M extends List<U>, U> ListBuilder<M, U> getBuilder(M us) {
        return new ListBuilder<>(us);
    }

    @SuppressWarnings("unchecked")
    public static <M extends List<U>, U> ListBuilder<M, U> getBuilder(Class<U> valueClass) {
        M us = (M) new ArrayList<>();
        return new ListBuilder<>(us);
    }

    protected ListBuilder<L, T> self() {
        return this;
    }

    public ListBuilder<L, T> add(T t) {
        ts.add(t);
        return self();
    }

    public L build() {
        return ts;
    }
}
