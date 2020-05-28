package com.resolvix.lib.dependency;

import com.resolvix.lib.dependency.api.CyclicDependencyException;
import com.resolvix.lib.dependency.api.DependencyNotFoundException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.hamcrest.Matchers.sameInstance;

public class DependencyResolverUT {

    private static final Logger LOGGER = LoggerFactory.getLogger(DependencyResolverUT.class);

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    private @interface DependsOn {

        Class<?>[] value() default {};
    }

    private class A { }

    @DependsOn({A.class})
    private class B { }

    @DependsOn({B.class})
    private class C { }

    @DependsOn({A.class, B.class, C.class})
    private class D { }

    @DependsOn({D.class})
    private class E { }

    @DependsOn({D.class, E.class})
    private class F { }

    @DependsOn({E.class})
    private class G { }

    @DependsOn({E.class})
    private class H { }

    @DependsOn({E.class})
    private class I { }

    @DependsOn({F.class, E.class})
    private class J { }

    @DependsOn({D.class, F.class, E.class})
    private class K { }

    @DependsOn({D.class, E.class})
    private class L { }

    @DependsOn({L.class, E.class})
    private class M { }

    private class N { }

    @DependsOn({E.class})
    private class O { }

    @DependsOn({D.class, E.class})
    private class P { }

    @DependsOn({F.class, L.class, E.class})
    private class Q { }

    @DependsOn({E.class, N.class, Q.class})
    private class R { }

    @DependsOn({Q.class})
    private class S { }

    @DependsOn({E.class, S.class, N.class, F.class, L.class})
    private class T { }

    @DependsOn({P.class, T.class, O.class})
    private class U { }

    private void dump(String separator, Class<?>[] cs) {
        LOGGER.debug(separator);
        for (Class<?> c : cs)
            LOGGER.debug(c.getCanonicalName());
    }

    @Test
    public void resolveClassDependenciesGivenDependencyOrderedClasses()
        throws CyclicDependencyException, DependencyNotFoundException
    {
        Class<?>[] dependencies = DependencyResolver.resolveDependencies(
            DependsOn.class,
            Object.class,
            DependsOn::value,
            A.class, B.class, C.class, D.class, E.class, F.class,
            G.class, H.class, I.class, J.class, K.class, L.class,
            M.class, N.class, O.class, P.class, Q.class, R.class,
            S.class, T.class, U.class);

        Assert.assertThat(dependencies, Matchers.arrayContaining(
            sameInstance(A.class), sameInstance(B.class), sameInstance(C.class),
            sameInstance(D.class), sameInstance(E.class), sameInstance(F.class),
            sameInstance(G.class), sameInstance(H.class), sameInstance(I.class),
            sameInstance(J.class), sameInstance(K.class), sameInstance(L.class),
            sameInstance(M.class), sameInstance(N.class), sameInstance(O.class),
            sameInstance(P.class), sameInstance(Q.class), sameInstance(R.class),
            sameInstance(S.class), sameInstance(T.class), sameInstance(U.class)));
    }

    @Test
    public void resolveClassDependenciesGivenNearDependencyOrderedClasses()
        throws CyclicDependencyException, DependencyNotFoundException {
        Class<?>[] dependencies = DependencyResolver.resolveDependencies(
            DependsOn.class,
            Object.class,
            DependsOn::value,
            A.class, B.class, C.class, D.class, E.class, Q.class,
            R.class, N.class, S.class, F.class, G.class, H.class,
            I.class, J.class, K.class, L.class, M.class, T.class,
            O.class, P.class, U.class);

        dump("resolveClassDependenciesGivenNearDependencyOrderedClasses", dependencies);

//        Assert.assertThat(dependencies, Matchers.arrayContaining(
//            sameInstance(A.class), sameInstance(B.class), sameInstance(C.class),
//            sameInstance(D.class), sameInstance(E.class), sameInstance(F.class),
//            sameInstance(G.class), sameInstance(H.class), sameInstance(I.class),
//            sameInstance(J.class), sameInstance(K.class), sameInstance(L.class),
//            sameInstance(M.class), sameInstance(N.class), sameInstance(O.class),
//            sameInstance(P.class), sameInstance(Q.class), sameInstance(R.class),
//            sameInstance(S.class), sameInstance(T.class), sameInstance(U.class)));
    }

    @Test @Ignore
    public void resolveClassDependenciesGivenArbitrarilyOrderedClasses()
        throws CyclicDependencyException, DependencyNotFoundException {
        Class<?>[] dependencies = DependencyResolver.resolveDependencies(
            DependsOn.class,
            Object.class,
            DependsOn::value,
            U.class, T.class, S.class, R.class, Q.class, P.class,
            O.class, N.class, M.class, L.class, K.class, J.class,
            I.class, H.class, G.class, F.class, E.class, D.class,
            C.class, B.class, A.class);

        Assert.assertThat(dependencies, Matchers.arrayContaining(
            sameInstance(A.class), sameInstance(B.class), sameInstance(C.class),
            sameInstance(D.class), sameInstance(E.class), sameInstance(F.class),
            sameInstance(G.class), sameInstance(H.class), sameInstance(I.class),
            sameInstance(J.class), sameInstance(K.class), sameInstance(L.class),
            sameInstance(M.class), sameInstance(N.class), sameInstance(O.class),
            sameInstance(P.class), sameInstance(Q.class), sameInstance(R.class),
            sameInstance(S.class), sameInstance(T.class), sameInstance(U.class)));
    }

    @Test @Ignore
    public void resolveClassDependenciesGivenInverseDependencyOrderedClasses()
        throws CyclicDependencyException, DependencyNotFoundException {
        Class<?>[] dependencies = DependencyResolver.resolveDependencies(
            DependsOn.class,
            Object.class,
            DependsOn::value,
            U.class, T.class, S.class, R.class, Q.class, P.class,
            O.class, N.class, M.class, L.class, K.class, J.class,
            I.class, H.class, G.class, F.class, E.class, D.class,
            C.class, B.class, A.class);

        Assert.assertThat(dependencies, Matchers.arrayContaining(
            sameInstance(A.class), sameInstance(B.class), sameInstance(C.class),
            sameInstance(D.class), sameInstance(E.class), sameInstance(F.class),
            sameInstance(G.class), sameInstance(H.class), sameInstance(I.class),
            sameInstance(J.class), sameInstance(K.class), sameInstance(L.class),
            sameInstance(M.class), sameInstance(N.class), sameInstance(O.class),
            sameInstance(P.class), sameInstance(Q.class), sameInstance(R.class),
            sameInstance(S.class), sameInstance(T.class), sameInstance(U.class)));
    }
}
