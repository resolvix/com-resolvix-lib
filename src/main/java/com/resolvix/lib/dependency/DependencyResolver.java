package com.resolvix.lib.dependency;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class DependencyResolver {

    private static final String INVALID_DEPENDENCY_REFERENCE = "Invalid dependency reference";

    private static class ObjRef<K, T>
        implements Comparable<ObjRef<K, T>>
    {

        K k;

        K[] deps;

        T t;

        Set<ObjRef<K, T>> dependencies;

        Set<ObjRef<K, T>> dependents;

        public K getK() {
            return k;
        }

        @Override
        public int compareTo(ObjRef<K, T> o) {

            //
            //  Return <1 if {@code o} is dependent on
            //  {@code this}.
            //
            //  Return >1 if {@code this} is dependent on
            //  {@code o}.
            //
            //  Return 0 if neither {@code this} or {@code o}
            //  are dependent on each other.
            //
            //  Throw {@link IllegalStateException}, if both
            //  {@code this} and {@code a} are dependent upon
            //  each other.
            //

            return 0;
        }
    }

    private static <K, T> ObjRef<K, T> toObjRef(
            T t,
            Function<T, K> identifier,
            Function<T, K[]> dependencies) {
        ObjRef<K, T> objRef = new ObjRef<>();
        objRef.k = identifier.apply(t);
        objRef.deps = dependencies.apply(t);
        objRef.t = t;
        objRef.dependencies = new HashSet<>();
        objRef.dependents = new HashSet<>();
        return objRef;
    }

    public static <T, K> T[] calculateDependencies(
            Class<T> classT,
            Function<T, K> identifier,
            Function<T, K[]> dependencies,
            T... ts) {

        List<ObjRef<K, T>> lts = Arrays.stream(ts)
                .map((T t) -> toObjRef(t, identifier, dependencies))
                .collect(Collectors.toList());

        Map<K, ObjRef<K, T>> map = Maps.uniqueIndex(lts, ObjRef::getK);

        map.forEach(
                (K k, ObjRef<K, T> objRef) -> {
                    K[] deps = objRef.deps;
                    Arrays.stream(deps).forEach((K kx) -> {
                        ObjRef<K, T> depObjRef = map.get(kx);
                        if (depObjRef == null)
                            throw new IllegalStateException(INVALID_DEPENDENCY_REFERENCE);
                        objRef.dependencies.add(depObjRef);
                        depObjRef.dependents.add(objRef);
                    });
                });

        return (T[]) Array.newInstance(classT, 10);
    }
}
