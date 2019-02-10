package com.resolvix.lib.stream.impl.base;

public class BaseMappingTest {

    protected X a = new X("a", new String[] { "a", "b", "c", "d" });

    protected X b = new X("b", new String[] { "e", "f", "g", "h" });

    protected X c = new X("c", new String[] { "i", "j", "k", "l" });

    protected X d = new X("d", new String[] { "m", "n", "o", "p" });

    protected X e = new X("e", new String[] { "q", "r", "s", "t" });

    protected X[] xs = new X[] {
        a, b, c, d, e
    };

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
}
