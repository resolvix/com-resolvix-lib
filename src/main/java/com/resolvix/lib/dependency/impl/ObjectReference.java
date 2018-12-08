package com.resolvix.lib.dependency.impl;

import java.util.*;
import java.util.stream.Collectors;

class ObjectReference<K, T>
    implements Comparable<ObjectReference<K, T>>
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

    @Override
    public int compareTo(ObjectReference<K, T> o) {

        boolean isDependedUpon = o.isDependentUpon(k);
        boolean isDependentOn = isDependentUpon(o.k);

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

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))
            return true;

        if (obj instanceof ObjectReference)
            return (compareTo((ObjectReference<K, T>) obj) == 0);

        return false;
    }
}
