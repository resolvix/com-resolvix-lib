package com.resolvix.lib.dependency.impl;

import com.resolvix.lib.dependency.DependencyResolver;
import com.resolvix.lib.dependency.api.CyclicDependencyException;
import com.resolvix.lib.dependency.api.DependencyNotFoundException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.hamcrest.Matchers.sameInstance;

public class AnnotationDependencyResolverShould {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    private @interface DependsOn {

        Class<?>[] value() default {};

    }

    @DependsOn({F.class, E.class})
    private class A {

    }

    @DependsOn(A.class)
    private class B {

    }

    @DependsOn({D.class, B.class})
    private class C {

    }

    private class D {

    }

    @DependsOn(F.class)
    private class E {

    }

    @DependsOn(D.class)
    private class F {

    }

    @Test
    public void resolveDependencies_should_correctly_resolve_well_formed_dependencies()
        throws CyclicDependencyException, DependencyNotFoundException
    {
        Class<?>[] dependencies = AnnotationDependencyResolver.resolveDependencies(
            DependsOn.class,
            Class.class,
            DependsOn::value,
            A.class, B.class, C.class, D.class, E.class, F.class);

        Assert.assertThat(dependencies, Matchers.arrayContaining(
            sameInstance(D.class), sameInstance(F.class), sameInstance(E.class),
            sameInstance(A.class), sameInstance(B.class), sameInstance(C.class)));
    }
}
