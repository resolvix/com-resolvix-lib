package com.resolvix.lib.entity.impl.base;

import com.resolvix.lib.property.api.Property;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class BaseEntityImplUT {

    @Mock
    private Map<String, Property<String, ?>> properties;

    private class LocalEntity
        extends BaseEntityImpl<String>
    {
        LocalEntity() {
            super(properties);
        }
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetProperty () {
        LocalEntity le = new LocalEntity();
        le.setProperty("id", "value");
        verify(properties).put(any(String.class), any(Property.class));
    }
}


