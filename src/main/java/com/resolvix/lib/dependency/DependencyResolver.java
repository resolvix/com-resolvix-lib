package com.resolvix.lib.dependency;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class DependencyResolver {

    protected static final String INVALID_DEPENDENCY_REFERENCE = "Invalid dependency reference";

    //Set<ObjRef<K, T>> dependencies;

    //Set<ObjRef<K, T>> dependents;

    //this.dependencies = new HashSet<>();
    //this.dependents = new HashSet<>();

    static <K, T> ObjRef<K, T> toObjRef(
        T t,
        Function<T, K> identifier,
        Function<T, K[]> dependencies) {
        ObjRef<K, T> objRef = new ObjRef<>(
            identifier.apply(t),
            dependencies.apply(t),
            t
        );
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
                        //objRef.dependencies.add(depObjRef);
                        //depObjRef.dependents.add(objRef);
                    });
                });

        return (T[]) Array.newInstance(classT, 10);
    }
}
