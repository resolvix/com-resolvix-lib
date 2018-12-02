package com.resolvix.lib.dependency;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class DependencyResolver {

    protected static final String INVALID_DEPENDENCY_REFERENCE = "Invalid dependency reference";

    static <K, T> ObjectReference<K, T> toObjRef(
        T t,
        Function<T, K> identifier,
        Function<T, K[]> dependencies) {
        ObjectReference<K, T> objRef = new ObjectReference<>(
            identifier.apply(t), t);

        K[] ks = dependencies.apply(t);
        Arrays.stream(ks)
            .forEach((K k) -> objRef.addDependency(k, 0));

        return objRef;
    }

    static <K, T> Map<K, Integer> traceDependencies(
        Deque<K> stack, Map<K, ObjectReference<K, T>> map, K k, Integer level) {
        ObjectReference<K, T> objRef = map.get(k);
        if (objRef == null)
            throw new IllegalStateException("dependency not found");

        Map<K, Integer> dependencies = new HashMap<>();
        Set<K> directDependencies = objRef.getDependencies(0);
        directDependencies.forEach(
                (K dk) -> {
                    if (stack.contains(dk))
                        throw new IllegalStateException("cyclic dependency identified");

                    dependencies.put(dk, level);
                    stack.push(dk);
                    dependencies.putAll(
                        traceDependencies(stack, map, dk, level + 1));
                    stack.pop();
                });
        return dependencies;
    }

    public static <T, K> T[] resolveDependencies(
            Class<T> classT,
            Function<T, K> identifier,
            Function<T, K[]> directDependencies,
            T... ts) {

        List<ObjectReference<K, T>> lts = Arrays.stream(ts)
                .map((T t) -> toObjRef(t, identifier, directDependencies))
                .collect(Collectors.toList());

        Map<K, ObjectReference<K, T>> map = Maps.uniqueIndex(lts, ObjectReference::getK);
        Deque<K> stack = new ArrayDeque<K>();

        map.forEach(
                (K k, ObjectReference<K, T> objRef) -> {
                    stack.push(k);
                    traceDependencies(stack, map, k, 0)
                        .forEach((K dk, Integer l) -> {
                            objRef.addDependency(dk, l);
                        });
                    stack.pop();
                });

        lts.sort(
                new Comparator<ObjectReference<K, T>>() {

                    @Override
                    public int compare(ObjectReference<K, T> o1, ObjectReference<K, T> o2) {
                        return o1.compareTo(o2);
                    }
                });

        T[] tsout = (T[]) Array.newInstance(classT, lts.size());

        List<T> ltsOut = lts.stream()
                .map(ObjectReference::getT)
                .collect(Collectors.toList());

        return ltsOut.toArray(tsout);
    }
}
