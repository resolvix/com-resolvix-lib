package com.resolvix.lib.dependency;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class DependencyResolver {

    protected static final String INVALID_DEPENDENCY_REFERENCE = "Invalid dependency reference";

    static <K, T> ObjRef<K, T> toObjRef(
        T t,
        Function<T, K> identifier,
        Function<T, K[]> dependencies) {
        ObjRef<K, T> objRef = new ObjRef<>(
            identifier.apply(t), t);

        K[] ks = dependencies.apply(t);
        Arrays.stream(ks)
            .forEach((K k) -> objRef.addDependency(k, 0));

        return objRef;
    }

    static <K, T> Map<K, Integer> traceDependencies(
            Map<K, ObjRef<K, T>> objRefMap, K k, Integer level) {
        ObjRef<K, T> objRef = objRefMap.get(k);
        Map<K, Integer> dependencies = new HashMap<>();
        Set<K> directDependencies = objRef.getDependencies(0);
        directDependencies.forEach(
                (K dk) -> {
                    dependencies.put(dk, level);
                    dependencies.putAll(
                        traceDependencies(objRefMap, dk, level + 1));
                });
        return dependencies;
    }

    public static <T, K> T[] resolveDependencies(
            Class<T> classT,
            Function<T, K> identifier,
            Function<T, K[]> directDependencies,
            T... ts) {

        List<ObjRef<K, T>> lts = Arrays.stream(ts)
                .map((T t) -> toObjRef(t, identifier, directDependencies))
                .collect(Collectors.toList());

        Map<K, ObjRef<K, T>> map = Maps.uniqueIndex(lts, ObjRef::getK);

        map.forEach(
                (K k, ObjRef<K, T> objRef) -> {
                    traceDependencies(map, k, 0)
                        .forEach((K dk, Integer l) -> {
                            objRef.addDependency(dk, l);
                        });
                });

        lts.sort(
                new Comparator<ObjRef<K, T>>() {

                    @Override
                    public int compare(ObjRef<K, T> o1, ObjRef<K, T> o2) {
                        return o1.compareTo(o2);
                    }
                });

        T[] tsout = (T[]) Array.newInstance(classT, lts.size());

        List<T> ltsOut = lts.stream()
                .map(ObjRef::getT)
                .collect(Collectors.toList());

        return ltsOut.toArray(tsout);
    }
}
