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
        ObjRef<K, T> objRef = new ObjRef<K, T>(
            identifier.apply(t),
            t
        );
        objRef.addDependencies(
                dependencies.apply(t), 0);

        return objRef;
    }

    public static <K, T> Set<K> calculateDependencies(
            Map<K, ObjRef<K, T>> objRefMap,
            K k
    ) {
        ObjRef<K, T> objRef = objRefMap.get(k);
        Set<K> allDependencies = new HashSet<>();
        Set<K> directDependencies = objRef.getDependencies();
        directDependencies.forEach(
                (K kx) -> {
                    allDependencies.add(kx);
                    Set<K> indirectDependencies
                            = calculateDependencies(objRefMap, kx);
                    allDependencies.addAll(indirectDependencies);
                });
        return allDependencies;
    }

    public static <T, K> T[] calculateDependencies(
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
                    Set<K> indirectDependencies = calculateDependencies(
                            map, k);
                    objRef.addDependencies(indirectDependencies, 0);
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
