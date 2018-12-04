package com.resolvix.lib.dependency;

import com.resolvix.lib.dependency.api.CyclicDependencyException;
import com.resolvix.lib.dependency.api.DependencyNotFoundException;
import com.resolvix.lib.dependency.impl.GenericDependencyResolver;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DependencyResolver {

    /**
     * Given an array containing a list of objects, and operations on those
     * objects to identify the object, and to identify the list of objects
     * within that list upon which that object depends, this method will
     * sort return an array of the same size containing the same list of
     * objects sorted such that the objects appearing earlier in the list
     * are not dependent upon any objects appearing later in the list.
     *
     * @param classT reference to the class of objects subject to
     *  dependency resolution
     *
     * @param getIdentifier reference to a method that returns, for any
     *  given object, a unique identifier for the object
     *
     * @param getDependencies reference to a method that returns, for
     *  any given object, a list of dependencies for that object expressed
     *  in terms of the unique identifiers
     *
     * @param ts an array containing the list of objects subject to dependency
     *  resolution
     *
     * @param <T> the class of objects subject to dependency resolution
     *
     * @param <K> the class of identifiers relating to the objects subject
     *  to dependency resolution
     *
     * @return an array of objects, in a dependency-resolved order.
     *
     * @throws CyclicDependencyException
     *
     * @throws DependencyNotFoundException
     */
    public static <T, K> T[] resolveDependencies(
        Class<T> classT,
        Function<T, K> getIdentifier,
        Function<T, K[]> getDependencies,
        T... ts
    ) throws CyclicDependencyException,
        DependencyNotFoundException {
        return GenericDependencyResolver.resolveDependencies(
            classT, getIdentifier, getDependencies, ts);
    }

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
