package com.resolvix.lib.dependency;

import javafx.beans.DefaultProperty;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ObjRef<K, T>
    implements Comparable<ObjRef<K, T>>
{
    K k;

    T t;

    Map<K, Integer> dependencies;

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

    public void addDependency(
        K k, int level) {
        dependencies.putIfAbsent(k, level);
    }

    @Deprecated
    public void addDependencies(
            Stream<K> streamK,
            int level) {
        streamK.forEach((K k) -> addDependency(k, level));
    }

    @Deprecated
    public void addDependencies(
            K[] ks,
            int level) {
        addDependencies(
                Arrays.stream(ks),
                level);
    }

    @Deprecated
    public void addDependencies(
            Collection<K> collectionK,
            int level) {
        addDependencies(
                collectionK.stream(),
                level);
    }

    @Deprecated
    public Set<K> getDependencies() {
        return dependencies.keySet();
    }

    Set<K> getDependencies(int level) {
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
