package com.resolvix.lib.dependency;

import com.google.common.base.Function;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class ObjRef<K, T>
    implements Comparable<ObjRef<K, T>>
{

    K k;

    K[] deps;

    T t;

    public ObjRef(
        K k,
        K[] deps,
        T t
    ) {
        this.k = k;
        this.deps = deps;
        Arrays.sort(this.deps);
        this.t = t;
    }



    public K getK() {
                  return k;
                           }

    @Override
    public int compareTo(ObjRef<K, T> o) {

        boolean isDependedUpon = (Arrays.binarySearch(o.deps, k) >= 0);
        boolean isDependentOn = (Arrays.binarySearch(deps, o.k) >= 0);

        //
        //  Throw {@link IllegalStateException}, if both
        //  {@code this} and {@code a} are directly dependent upon
        //  each other.
        //
        if (isDependedUpon && isDependentOn)
            throw new IllegalStateException();

        //
        //  Return <1 if {@code o} is directly dependent on
        //  {@code this}.
        //
        if (isDependedUpon)
            return -1;

        //
        //  Return >1 if {@code this} is directly dependent on
        //  {@code o}.
        //
        if (isDependentOn)
            return 1;

        //
        //  Return 0 if neither {@code this} or {@code o}
        //  are directly dependent on each other.
        //
        return 0;
    }
}
