package com.resolvix.lib.map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class MapBuilderUT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testMapBuilderWithDefaultMapType() {
        Map<String, String> map = MapBuilder.getBuilder(String.class, String.class)
            .put("1", "one")
            .put("2", "two")
            .put("3", "three")
            .put("4", "four")
            .build();
        assertThat(map, allOf(
            instanceOf(HashMap.class),
            hasEntry("1", "one"),
            hasEntry("2", "two"),
            hasEntry("3", "three"),
            hasEntry("4", "four")));
    }

    @Test
    public void testMapBuilderWithCustomMapType() {
        Map<String, String> map = MapBuilder.getBuilder(TreeMap.class, String.class, String.class)
            .put("1", "one")
            .put("2", "two")
            .put("3", "three")
            .put("4", "four")
            .build();
        assertThat(map, allOf(
            instanceOf(TreeMap.class),
            hasEntry("1", "one"),
            hasEntry("2", "two"),
            hasEntry("3", "three"),
            hasEntry("4", "four")));
    }
}
