package com.resolvix.lib.dependency.impl;

import com.resolvix.lib.dependency.api.CyclicDependencyException;
import com.resolvix.lib.dependency.api.DependencyNotFoundException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

import static org.hamcrest.Matchers.sameInstance;

public class AnnotationDependencyResolverShould {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    private @interface DependsOn {

        Class<? extends BaseClass>[] value() default {};

    }

    private abstract class BaseClass {

    }

    @DependsOn({F.class, E.class})
    private class A extends BaseClass {

    }

    @DependsOn(A.class)
    private class B extends BaseClass {

    }

    @DependsOn({D.class, B.class})
    private class C extends BaseClass {

    }

    private class D extends BaseClass {

    }

    @DependsOn(F.class)
    private class E extends BaseClass {

    }

    @DependsOn(D.class)
    private class F extends BaseClass {

    }

    @Test
    public void resolveDependenciesShouldCorrectlyResolveWellFormedDependencies()
        throws CyclicDependencyException, DependencyNotFoundException
    {
        Class<?>[] dependencies = AnnotationDependencyResolver.resolveDependencies(
            DependsOn.class,
            Object.class,
            DependsOn::value,
            A.class, B.class, C.class, D.class, E.class, F.class);

        Assert.assertThat(dependencies, Matchers.arrayContaining(
            sameInstance(D.class), sameInstance(F.class), sameInstance(E.class),
            sameInstance(A.class), sameInstance(B.class), sameInstance(C.class)));
    }

    @Test
    public void resolveDependenciesShouldTypeAwarelyCorreclyResolveWellFormedDependencies()
        throws CyclicDependencyException, DependencyNotFoundException
    {
        Class<? extends BaseClass>[] dependencies = AnnotationDependencyResolver.resolveDependencies(
            DependsOn.class,
            BaseClass.class,
            DependsOn::value,
            A.class, B.class, C.class, D.class, E.class, F.class);

        Assert.assertThat(dependencies, Matchers.arrayContaining(
            sameInstance(D.class), sameInstance(F.class), sameInstance(E.class),
            sameInstance(A.class), sameInstance(B.class), sameInstance(C.class)));
    }
}
