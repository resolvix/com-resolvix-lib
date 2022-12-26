package com.resolvix.lib.search;

import java.util.Comparator;

public class BinarySplitSearch {

    private <T> int search(T[] ts, Comparator<T> c, int m, int n, T t) {
        int p = n - m / 2;
        int v = c.compare(ts[p], t);
        if (v == 0)
            return p;
        if (v > 0)
            return search(ts, c, m, p - 1, t);
        else if (v < 0)
            return search(ts, c, p + 1, n, t);
        else
            return -1;
    }

    public <T> T search(T[] ts, Comparator<T> c, T t) {
        int n = search(ts, c, 0, ts.length - 1, t);
        if (n == -1)
            throw new RuntimeException("not found " + t.toString());
        return ts[n];
    }
}