package com.resolvix.lib.property.impl;

import com.resolvix.lib.property.api.Property;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

public class PropertyImplUT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testPropertyOf() {
        Property<String, String> property = PropertyImpl.of("id", String.class, "value");
        assertThat(property, allOf(
            hasProperty("id", equalTo("id")),
            hasProperty("type", equalTo(String.class)),
            hasProperty("value", equalTo("value"))));
    }
}
