package com.resolvix.lib.dependency;

import com.google.common.base.Function;
import javafx.collections.transformation.SortedList;

import java.util.*;
import java.util.stream.Stream;

class ObjRef<K, T>
    implements Comparable<ObjRef<K, T>>
{
    private static class DependencyMaplet<K> {
        K k;

        int level;

        DependencyMaplet(K k, int level) {
            this.k = k;
            this.level = level;
        }
    }

    K k;

    T t;

    Map<K, DependencyMaplet<K>> dependencies;

    public ObjRef(
        K k,
        T t
    ) {
        this.k = k;
        this.t = t;
        this.dependencies = new HashMap<>();
    }

    public K getK() { return k; }

    public T getT() { return t; }

    public void addDependencies(
            Stream<K> streamK,
            int level) {
        streamK.map((K k) -> new DependencyMaplet<>(k, level))
                .forEach((DependencyMaplet<K> dependencyMaplet) ->
                        dependencies.putIfAbsent(dependencyMaplet.k, dependencyMaplet));
    }

    public void addDependencies(
            K[] ks,
            int level) {
        addDependencies(
                Arrays.stream(ks),
                level);
    }

    public void addDependencies(
            Collection<K> collectionK,
            int level) {
        addDependencies(
                collectionK.stream(),
                level);
    }

    public Set<K> getDependencies() {
        return dependencies.keySet();
    }

    private boolean isDependentUpon(K k) {
        return dependencies.containsKey(k);
    }

    @Override
    public int compareTo(ObjRef<K, T> o) {

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
}
