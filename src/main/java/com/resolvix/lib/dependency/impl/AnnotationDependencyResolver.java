package com.resolvix.lib.dependency.impl;

import com.resolvix.lib.dependency.api.CyclicDependencyException;
import com.resolvix.lib.dependency.api.DependencyNotFoundException;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnnotationDependencyResolver {

    private AnnotationDependencyResolver() { }

    private static class GetDependenciesForClassFromAnnotation<A extends Annotation, T extends Class<?>>
        implements Function<T, String[]>
    {
        private Class<A> classA;

        private Function<A, T[]> getDependenciesFromAnnotation;

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

    @SafeVarargs
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
            = new GetDependenciesForClassFromAnnotation<>(classA, getDependencies);

        return GenericDependencyResolver.resolveDependencies(
            classT,
            getCanonicalName,
            getDependenciesForClassFromAnnotation,
            ts);
    }
}
