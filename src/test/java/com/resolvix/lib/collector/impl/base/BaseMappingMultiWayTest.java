package com.resolvix.lib.collector.impl.base;

public class BaseMappingMultiWayTest {

    protected static class X {

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

    protected X[] xs = new X[] {
        new X("a", new String[] { "a", "b", "c", "d" }),
        new X("b", new String[] { "e", "f", "g", "h" }),
        new X("c", new String[] { "i", "j", "k", "l" }),
        new X("d", new String[] { "m", "n", "o", "p" }),
        new X("e", new String[] { "q", "r", "s", "t" })
    };
}
