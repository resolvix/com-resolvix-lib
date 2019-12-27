package com.resolvix.lib.entity.impl.base;

import com.resolvix.lib.property.api.Property;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class BaseEntityImplUT {

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
        this.properties = spy(new HashMap<>());
        this.properties.put(
            "id2", new Property<String, String>() {

                @Override
                public String getId() {
                    return "id2";
                }

                @Override
                public Class<String> getType() {
                    return String.class;
                }

                @Override
                public String getValue() {
                    return "value2";
                }
            }
        );
    }

    @Test
    public void testGetPropertyWhenPropertyExists() {
        LocalEntity le = new LocalEntity();
        Property<String, String> p = le.getProperty("id2");
        assertThat(p, allOf(
            instanceOf(Property.class),
            hasProperty("id", equalTo("id2")),
            hasProperty("type", equalTo(String.class)),
            hasProperty("value", equalTo("value2"))));
    }

    @Test
    public void testGetPropertyWhenPropertyDoesNotExist() {
        LocalEntity le = new LocalEntity();
        Property<String, String> p = le.getProperty("id3");
        assertThat(p, nullValue());
    }

    @Test
    public void testGetPropertyOrDefaultWhenPropertyExists() {
        LocalEntity le = new LocalEntity();
        Property<String, String> p = le.getPropertyOrDefault("id2", null);
        assertThat(p, allOf(
            instanceOf(Property.class),
            hasProperty("id", equalTo("id2")),
            hasProperty("type", equalTo(String.class)),
            hasProperty("value", equalTo("value2"))));
    }

    @Test
    public void testGetPropertyOrDefaultWhenPropertyDoesNotExist() {
        LocalEntity le = new LocalEntity();
        Property<String, String> p = le.getPropertyOrDefault("id3", new Property<String, String>() {

            @Override
            public String getId() {
                return "id4";
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }

            @Override
            public String getValue() {
                return "value4";
            }
        });
        assertThat(p, allOf(
            instanceOf(Property.class),
            hasProperty("id", equalTo("id4")),
            hasProperty("type", equalTo(String.class)),
            hasProperty("value", equalTo("value4"))));
    }


    @Test
    public void testSetPropertyWithIdValue() {
        LocalEntity le = new LocalEntity();
        le.setProperty("id", "value");
        assertThat(properties.get("id"), allOf(
            instanceOf(Property.class),
            hasProperty("id", equalTo("id")),
            hasProperty("type", equalTo(String.class)),
            hasProperty("value", equalTo("value"))));
        verify(properties).put(eq("id"), isA(Property.class));
    }

    @Test
    public void testSetPropertyWithIdTypeValue() {
        LocalEntity le = new LocalEntity();
        le.setProperty("id", String.class,"value");
        assertThat(properties.get("id"), allOf(
            instanceOf(Property.class),
            hasProperty("id", equalTo("id")),
            hasProperty("type", equalTo(String.class)),
            hasProperty("value", equalTo("value"))));
        verify(properties).put(eq("id"), isA(Property.class));
    }

    @Test
    public void testSetPropertyWithProperty() {
        LocalEntity le = new LocalEntity();
        le.setProperty(new Property<String, String>() {

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
        });
        assertThat(properties.get("id"), allOf(
            instanceOf(Property.class),
            hasProperty("id", equalTo("id")),
            hasProperty("type", equalTo(String.class)),
            hasProperty("value", equalTo("value"))));
        verify(properties).put(eq("id"), isA(Property.class));
    }

    @Test
    public void testUnsetProperty() {
        LocalEntity le = new LocalEntity();
        le.unsetProperty("id2");
        assertTrue(properties.isEmpty());
        verify(properties).remove(eq("id2"));
    }
}


