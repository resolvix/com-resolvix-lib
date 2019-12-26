package com.resolvix.lib.property;

import com.resolvix.lib.property.api.Property;
import com.resolvix.lib.property.impl.PropertyImpl;

public class Properties {

    public static <Q, U> Property<Q, U> property(
            Q id, Class<U> classU, U value) {
        return PropertyImpl.of(id, classU, value);
    }
}
