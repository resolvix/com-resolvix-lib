package com.resolvix.lib.entity.impl.base;

import com.resolvix.lib.property.api.Property;
import com.resolvix.lib.property.api.PropertyReader;
import com.resolvix.lib.property.api.PropertyWriter;
import com.resolvix.lib.property.impl.PropertyImpl;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseEntityImpl<P>
    implements PropertyReader<P>,
        PropertyWriter<P>
{
    private Map<P, Property<P, ?>> properties;

    protected BaseEntityImpl() {
        this(new HashMap<>());
    }

    protected BaseEntityImpl(Map<P, Property<P, ?>> properties) {
        this.properties = properties;
    }

    @Override
    public <T> Property<P, T> getProperty(P id) {
        return (Property<P, T>) properties.get(id);
    }

    @Override
    public <T> Property<P, T> getPropertyOrDefault(P id, Property<P, T> defaultProperty) {
        return (Property<P, T>) properties.getOrDefault(id, defaultProperty);
    }

    @Override
    public <T> void setProperty(P id, T value) {
        properties.put(id, PropertyImpl.of(id, (Class<T>) value.getClass(), value));
    }

    @Override
    public <T> void setProperty(P id, Class<T> classT, T value) {
        properties.put(id, PropertyImpl.of(id, classT, value));
    }

    @Override
    public <T> void setProperty(Property<P, T> property) {
        properties.put(property.getId(), PropertyImpl.of(property));
    }

    @Override
    public void unsetProperty(P id) {
        properties.remove(id);
    }
}
