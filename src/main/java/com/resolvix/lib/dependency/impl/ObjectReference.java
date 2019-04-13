package com.resolvix.lib.dependency.impl;

import java.util.*;
import java.util.stream.Collectors;

class ObjectReference<K, T>
//    implements Comparable<ObjectReference<K, T>>
{
    private K k;

    private T t;

    private Map<K, Integer> dependencies;

    public ObjectReference(
        K k,
        T t
    ) {
        this.k = k;
        this.t = t;
        this.dependencies = new HashMap<>();
    }

    public static <K, T> int compareDependencies(
        ObjectReference<K, T> left,
        ObjectReference<K, T> right)
    {
        boolean isDependedUpon = right.isDependentUpon(left.k);
        boolean isDependentOn = left.isDependentUpon(right.k);

        //
        //  Throw {@link IllegalStateException}, if both {@code left}
        //  and {@code right} are directly dependent upon each other.
        //
        if (isDependedUpon && isDependentOn)
            throw new IllegalStateException();

        //
        //  Return <1 if {@code right} is directly dependent on
        //  {@code left}.
        //
        if (isDependedUpon)
            return -1;

        //
        //  Return >1 if {@code left} is directly dependent on
        //  {@code right}.
        //
        if (isDependentOn)
            return 1;

        //
        //  Return 0 if neither {@code left} or {@code right} are
        //  directly dependent on each other.
        //
        return 0;
    }

    public K getK() { return k; }

    public T getT() { return t; }

    public void addDependency(
        K k, int level) {
        Integer l = dependencies.get(k);
        if (l == null || l > level)
            dependencies.put(k, level);
    }

    public Set<K> getDependencies(int level) {
        return dependencies.entrySet()
            .stream()
            .filter((Map.Entry<K, Integer> e) -> (e.getValue() <= level))
            .map((Map.Entry<K, Integer> e) -> e.getKey())
            .collect(Collectors.toSet());
    }

    private boolean isDependentUpon(K k) {
        return dependencies.containsKey(k);
    }
}
