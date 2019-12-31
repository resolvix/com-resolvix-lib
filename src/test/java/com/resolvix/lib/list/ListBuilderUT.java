package com.resolvix.lib.list;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class ListBuilderUT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testListBuilderWithDefaultListType() {
        List<String> ls = ListBuilder.getBuilder(String.class)
            .add("one")
            .add("two")
            .add("three")
            .build();
        assertThat(ls, contains("one", "two", "three"));
    }

    @Test
    public void testListBuilderWithCustomListType() {
        LinkedList<String> ls = ListBuilder.getBuilder(new LinkedList<String>())
            .add("one")
            .add("two")
            .add("three")
            .build();
        assertThat(ls, instanceOf(LinkedList.class));
    }
}
