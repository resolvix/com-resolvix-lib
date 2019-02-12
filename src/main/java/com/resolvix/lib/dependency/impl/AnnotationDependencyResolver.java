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

    private static class GetDependenciesForClassFromAnnotation<A extends Annotation, T>
        implements Function<Class<? extends T>, String[]>
    {
        private Class<A> classA;

        private Function<A, Class<? extends T>[]> getDependenciesFromAnnotation;

        GetDependenciesForClassFromAnnotation(
            Class<A> classA,
            Function<A, Class<? extends T>[]> getDependenciesFromAnnotation
        ) {
            this.classA = classA;
            this.getDependenciesFromAnnotation = getDependenciesFromAnnotation;
        }

        @Override
        public String[] apply(Class<? extends T> t) {
            A a = t.getDeclaredAnnotation(classA);
            if (a == null)
                return new String[] {};

            Class<? extends T>[] ts = getDependenciesFromAnnotation.apply(a);
            List<String> lss = Arrays.stream(ts)
                .map(Class::getCanonicalName)
                .collect(Collectors.toList());
            String[] ss = new String[lss.size()];
            return lss.toArray(ss);
        }
    }

    @SafeVarargs
    public static <A extends Annotation, T> Class<? extends T>[] resolveDependencies(
        Class<A> classA,
        Class<T> classT,
        Function<A, Class<? extends T>[]> getDependencies,
        Class<? extends T>... classes
    ) throws CyclicDependencyException,
        DependencyNotFoundException
    {
        Function<Class<? extends T>, String> getCanonicalName
            = Class::getCanonicalName;

        Function<Class<? extends T>, String[]> getDependenciesForClassFromAnnotation
            = new GetDependenciesForClassFromAnnotation<>(classA, getDependencies);

        @SuppressWarnings("unchecked")
        Class<Class<? extends T>> classClassT
            = (Class<Class<? extends T>>) classT.getClass();

        return GenericDependencyResolver.resolveDependencies(
            classClassT,
            getCanonicalName,
            getDependenciesForClassFromAnnotation,
            classes);
    }
}
