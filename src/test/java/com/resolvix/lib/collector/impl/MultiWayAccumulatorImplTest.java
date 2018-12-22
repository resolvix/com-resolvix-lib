package com.resolvix.lib.collector.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

public class MultiWayAccumulatorImplTest {

    private class X {

        private String key;

        private String[] refs;

        X(String key, String[] refs) {
            this.key = key;
            this.refs = refs;
        }

        public String getKey() {
            return key;
        }

        public String[] getRefs() {
            return refs;
        }
    }

    private X[] xs = new X[] {
        new X("a", new String[] { "a", "b", "c", "d" }),
        new X("b", new String[] { "e", "f", "g", "h" }),
        new X("c", new String[] { "i", "j", "k", "l" }),
        new X("d", new String[] { "m", "n", "o", "p" }),
        new X("e", new String[] { "q", "r", "s", "t" })
    };

    @Before
    public void before() {
        //
    }

    @Ignore
    @Test
    public void createMultiWayAccumulatorWithMultipleCollectorSpecifications() {

        /*Arrays.stream(xs)
            .collect(
               MappingMultiWayAccumulatorImpl.getBuilder()
                   .collector()
                   .collector()
                   .collector()
                   .build()
            );*/
    }
}
