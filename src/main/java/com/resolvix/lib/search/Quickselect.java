package com.resolvix.lib.search;

import java.util.Comparator;

public class Quickselect {

    private <T> int partition(T[] ts, Comparator<T> c, int m, int n) {
        int p = (m + n) / 2;
        T pivot = ts[p];
        int i = m;
        int j = n;
        while (true) {
            while (c.compare(ts[i], pivot) < 0) i++;
            while (c.compare(ts[j], pivot) > 0) j--;
            if (i >= j)
                break;

            T scratchT = ts[i];
            ts[i] = ts[j];
            ts[j] = scratchT;
        }

        return j;
    }

    private <T> void quickselect(T[] ts, Comparator<T> c, int m, int n, int k) {
        if (m < n) {
            int p = partition(ts, c, m, n);
            if (k <= p)
                quickselect(ts, c, m, p, k);
            else if (k >= p)
                quickselect(ts, c, p + 1, n, k);
        }
    }

    public <T> T find(T[] ts, Comparator<? super T> c, int k) {
        quickselect(ts, c, 0, ts.length - 1, k);
        return ts[k];
    }
}