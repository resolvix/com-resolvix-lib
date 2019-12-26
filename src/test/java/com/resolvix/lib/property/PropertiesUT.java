package com.resolvix.lib.property;

import com.resolvix.lib.property.api.Property;
import com.resolvix.lib.property.impl.PropertyImpl;
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class PropertiesUT {

    @Test
    public void testPropertyIdTypeValue() {
        assertThat(
                Properties.property("id", String.class, "value"),
                allOf(
                        instanceOf(PropertyImpl.class),
                        hasProperty("id", equalTo("id")),
                        hasProperty("type", equalTo(String.class)),
                        hasProperty("value", equalTo("value"))));
    }

    @Test
    public void testPropertyProperty() {
        assertThat(
                Properties.property(
                        new Property<String, String>() {
                            @Override
                            public String getId() {
                                return "id";
                            }

                            @Override
                            public Class<String> getType() {
                                return String.class;
                            }

                            @Override
                            public String getValue() {
                                return "value";
                            }
                        }
                ),
                allOf(
                        instanceOf(PropertyImpl.class),
                        hasProperty("id", equalTo("id")),
                        hasProperty("type", equalTo(String.class)),
                        hasProperty("value", equalTo("value"))));
    }
}
