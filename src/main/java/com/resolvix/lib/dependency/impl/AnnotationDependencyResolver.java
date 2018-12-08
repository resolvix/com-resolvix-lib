package com.resolvix.lib.dependency.impl;

import com.resolvix.lib.dependency.api.CyclicDependencyException;
import com.resolvix.lib.dependency.api.DependencyNotFoundException;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnotationDependencyResolver {

    @Deprecated
    private static <T extends Class<?>> String[] toCanonicalNameList(T[] ts) {
        String[] ss = new String[ts.length];
        return Arrays.stream(ts)
            .map(Class::getCanonicalName)
            .collect(Collectors.toList())
            .toArray(ss);
    }

    private static <T extends Class<?>> Stream<T> toStream(T[] ts) {
        return Arrays.stream(ts);
    }

    private static class GetDependenciesForClassFromAnnotation<A extends Annotation, T extends Class<?>>
        implements Function<T, String[]>
    {
        Class<A> classA;

        Function<A, T[]> getDependenciesFromAnnotation;

        GetDependenciesForClassFromAnnotation(
            Class<A> classA,
            Function<A, T[]> getDependenciesFromAnnotation
        ) {
            this.classA = classA;
            this.getDependenciesFromAnnotation = getDependenciesFromAnnotation;
        }

        @Override
        public String[] apply(T t) {
            A a = t.getDeclaredAnnotation(classA);
            if (a == null)
                return new String[] {};

            T[] ts = getDependenciesFromAnnotation.apply(a);
            List<String> lss = Arrays.stream(ts)
                .map(Class::getCanonicalName)
                .collect(Collectors.toList());
            String[] ss = new String[lss.size()];
            return lss.toArray(ss);
        }
    }

    /**
     *
     * @param classA
     * @param ts
     * @param <A>
     * @param <T>
     * @return
     * @throws CyclicDependencyException
     * @throws DependencyNotFoundException
     */
    public static <A extends Annotation, T extends Class<?>> T[] resolveDependencies(
        Class<A> classA,
        Class<T> classT,
        Function<A, T[]> getDependencies,
        T... ts
    ) throws CyclicDependencyException,
        DependencyNotFoundException
    {
        Function<T, String> getCanonicalName
            = Class::getCanonicalName;

        Function<T, String[]> getDependenciesForClassFromAnnotation
            = new GetDependenciesForClassFromAnnotation<A, T>(classA, getDependencies);

        return GenericDependencyResolver.resolveDependencies(
            classT,
            getCanonicalName,
            getDependenciesForClassFromAnnotation,
            ts);
    }
}
