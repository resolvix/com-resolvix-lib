package com.resolvix.lib.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * A utility class containing a variety of static methods to support
 * {@link Class} related operations.
 */
public class ClassUtils
{

    /**
     * Returns a newly instantiated instance of a class S, being a subclass
     * of class T.
     *
     * @param s the class to be instantiated
     * @return the new instance of class, s
     * @param <T> the superclass of type S
     * @param <S> the subclass of type T
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T, S extends T> S instantiateClass(Class<S> s)
        throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, InstantiationException
    {
        Constructor<S> constructor = s.getDeclaredConstructor();
        return constructor.newInstance();
    }

    /**
     * Returns an array of newly instantiated instances of classes being
     * subclasses of class T, in an array
     *
     * @param cts the array of classes representing the type of instances
     *  to be instantiated
     * @param classT the {@link Class<T>} object relating to the common
     *  subclass, {@code T}, of all members of array {@code cts}
     * @return an array of type {@code T[]} containing the newly
     *  instantiated classes
     * @param <T> the type of the common subclass of all members of
     *  {@code cts}
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] instantiateClasses(Class<? extends T>[] cts, Class<T> classT)
        throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, InstantiationException
    {
        T[] ts = (T[]) Array.newInstance(classT, cts.length);
        for (int i = 0; i < ts.length; i++) {
            ts[i] = instantiateClass(cts[i]);
        }
        return ts;
    }
}
