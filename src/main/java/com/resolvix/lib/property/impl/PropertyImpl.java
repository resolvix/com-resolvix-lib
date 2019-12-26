package com.resolvix.lib.property.impl;

import com.resolvix.lib.property.api.Property;

public class PropertyImpl<P, T>
    implements Property<P, T> {

    private P id;

    private Class<T> classT;

    private T value;

    protected PropertyImpl(P id, Class<T> classT, T value) {
        this.id = id;
        this.classT = classT;
        this.value = value;
    }

    protected PropertyImpl(Property<P, T> property) {
        this.id = property.getId();
        this.classT = property.getType();
        this.value = property.getValue();
    }

    public static <Q, U> Property<Q, U> of(
            Q id, Class<U> classU, U value) {
        return new PropertyImpl<>(id, classU, value);
    }

    public static <Q, U> Property<Q, U> of(
            Property<Q, U> property) {
        return new PropertyImpl(property);
    }

    @Override
    public P getId() {
        return id;
    }

    @Override
    public Class<T> getType() {
        return classT;
    }

    @Override
    public T getValue() {
        return value;
    }
}
