package com.resolvix.lib.dependency.impl;

import com.google.common.collect.Maps;
import com.resolvix.lib.dependency.api.CyclicDependencyException;
import com.resolvix.lib.dependency.api.DependencyNotFoundException;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenericDependencyResolver {

    private GenericDependencyResolver() {
        //
    }

    private static class LocalDependencyResolver<K, T> {

        private Map<K, ObjectReference<K, T>> map;

        private Deque<K> stack;

        private Map<K, Integer> traceDependencies(
            ObjectReference<K, T> objRef,
            Integer level
        ) throws CyclicDependencyException,
            DependencyNotFoundException
        {
            stack.push(objRef.getK());
            Map<K, Integer> dependencies = new HashMap<>();
            Set<K> directDependencies = objRef.getDependencies(0);
            for (K dk : directDependencies) {
                if (stack.contains(dk))
                    throw new CyclicDependencyException(objRef.getK(), dk);

                dependencies.put(dk, level);
                stack.push(dk);
                ObjectReference<K, T> depObjRef
                    = map.get(dk);
                if (depObjRef == null)
                    throw new DependencyNotFoundException(objRef.getK(), dk);

                dependencies.putAll(
                    traceDependencies(depObjRef, level + 1));
                stack.pop();
            }
            stack.pop();
            return dependencies;
        }

        private void traceDependencies(
            ObjectReference<K, T> objRef
        ) throws CyclicDependencyException,
            DependencyNotFoundException
        {
            traceDependencies(objRef, 0)
                .forEach(objRef::addDependency);
        }

        private LocalDependencyResolver(
            Map<K, ObjectReference<K, T>> map
        ) {
            this.map = map;
            this.stack = new ArrayDeque<>();
        }
    }

    private static <K, T> ObjectReference<K, T> toObjRef (
        T t,
        Function< T, K > getIdentifier,
        Function< T, K[]> getDependencies
    ) {
        ObjectReference<K, T> objRef = new ObjectReference<>(
            getIdentifier.apply(t), t);
        K[] ks = getDependencies.apply(t);
        Arrays.stream(ks)
            .forEach((K k) -> objRef.addDependency(k, 0));
        return objRef;
    }

    @SafeVarargs
    public static <T, K> T[] resolveDependencies(
        Class<T> classT,
        Function<T, K> identifier,
        Function<T, K[]> directDependencies,
        T... ts
    ) throws CyclicDependencyException,
        DependencyNotFoundException
    {
        List<ObjectReference<K, T>> objectReferences = Arrays.stream(ts)
            .map((T t) -> toObjRef(t, identifier, directDependencies))
            .collect(Collectors.toList());

        Map<K, ObjectReference<K, T>> map
            = Maps.uniqueIndex(objectReferences, ObjectReference::getK);

        LocalDependencyResolver<K, T> localDependencyResolver
            = new LocalDependencyResolver<>(map);

        for(Map.Entry<K, ObjectReference<K, T>> maplet : map.entrySet())
            localDependencyResolver.traceDependencies(maplet.getValue());

        objectReferences.sort(
            ObjectReference::compareDependencies);

        @SuppressWarnings("unchecked")
        T[] tsResult = (T[]) Array.newInstance(classT, ts.length);
        List<T> listResult = objectReferences.stream()
            .map(ObjectReference::getT)
            .collect(Collectors.toList());

        return listResult.toArray(tsResult);
    }
}
