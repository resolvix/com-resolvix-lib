package com.resolvix.lib.dependency;

import com.resolvix.lib.dependency.api.CyclicDependencyException;
import com.resolvix.lib.dependency.api.DependencyNotFoundException;
import com.resolvix.lib.dependency.impl.AnnotationDependencyResolver;
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
     * perform a topological sort of the array, and return it.
     *
     * The array of objects returned will be sorted such that the objects
     * appearing earlier in the list are not dependent upon any objects
     * appearing later in the list.
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
     * @return an array of objects, in a dependency-resolved order
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

    /**
     * Given an array containing a list of classes, the identity of an
     * annotation class used to identify dependencies between classes,
     * and a method to identify the dependencies for a given class
     * annotation, this method will perform a topological sort of the
     * array, and return it.
     *
     * The array of classes returned will be sorted such that the classes
     * appearing earlier in the list are not dependent upon any objects
     * appearing later in the list.
     *
     * @param classA reference to the {@link Annotation} class used
     *  within the source code to identify a dependency between the
     *  annotated class, and a target class
     *
     * @param classT reference to the class of classes subject to
     *  dependency resolution
     *
     * @param getDependencies reference to a method that returns, for a
     *  given annotation, a list of dependencies for the class it is
     *  associated with, expressed in terms of the canonical names of
     *  each dependent classes
     *
     * @param ts an array containing the list of classes subject to
     *  dependency resolution
     *
     * @param <A> the class of {@link Annotation} representing the source
     *  of dependency information for the relevant classes
     *
     * @param <T> the class of classes subject to dependency resolution
     *
     * @return an array of classes, in a dependency-resolved order
     *
     * @throws CyclicDependencyException
     *
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
        return AnnotationDependencyResolver.resolveDependencies(
            classA, classT, getDependencies, ts);
    }
}
