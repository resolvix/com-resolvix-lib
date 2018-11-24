package com.resolvix.lib.optic;

import com.resolvix.lib.optic.api.Lens;
import com.resolvix.lib.optic.impl.base.BaseLens;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.resolvix.lib.optic.Optic.toLens;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class LensTest {

    public static class A {

        private Integer i;

        private String s;

        public Integer getI() {
            return i;
        }

        public void setI(Integer i) {
            this.i = i;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }
    }

    public static class B {

        private A a;

        public A getA() {
            return a;
        }

        public void setA(A a) {
            this.a = a;
        }
    }

    public static class C {

        private B b;

        public B getB() {
            return b;
        }

        public void setB(B b) {
            this.b = b;
        }
    }

    public static class D {

        private C c;

        public C getC() { return c; }

        public void setC(C c) { this.c = c; }
    }

    private static final Lens<C, A> cToA = toLens(C::getB, C::setB, B::new)
        .andThen(B::getA, B::setA, A::new);

    private static final Lens<D, A> dToA = toLens(D::getC, D::setC, C::new)
        .andThen(cToA);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private A objectA;

    private B objectB;

    private C objectC;

    private D objectD;

    private Integer expectedI = 99;

    private String expectedS = "<expectedS>";

    @Before
    public void before() {

    }

    private <T> Matcher<T> ofTypeAndValue(Class<T> classT, T t) {
        return both(isA(classT)).and(equalTo(t));
    }

    @Test
    public void Lens_success_pre_initialised_intermediate_note() {
        objectB = new B();
        objectC = new C();
        objectC.setB(objectB);
        cToA.apply(objectC).setI(expectedI);
        cToA.apply(objectC).setS(expectedS);

        assertThat(
            objectC.getB().getA(), allOf(
                hasProperty("i", ofTypeAndValue(Integer.class, expectedI)),
                hasProperty("s", ofTypeAndValue(String.class, expectedS))));
    }

    @Test
    public void Lens_success_initialise_intermediate_node() {
        objectC = new C();
        cToA.apply(objectC).setI(expectedI);
        cToA.apply(objectC).setS(expectedS);

        assertThat(
            objectC.getB().getA(), allOf(
                hasProperty("i", ofTypeAndValue(Integer.class, expectedI)),
                hasProperty("s", ofTypeAndValue(String.class, expectedS))));
    }

    @Test
    public void ComposedImplLens_success_null_intermediate_node() {
        Lens<B, Object> cToObject = new BaseLens<B, Object>() {

            @Override
            public Object apply(B c) {
                return null;
            }

            @Override
            protected Lens<B, Object> self() {
                return this;
            }
        };

        Lens<Object, Object> objectToObject = new BaseLens<Object, Object>() {

            @Override
            public Object apply(Object o) {
                return null;
            }

            @Override
            protected Lens<Object, Object> self() {
                return this;
            }
        };

        Lens<D, C> dToC = toLens(D::getC, D::setC, C::new);
        Lens<C, B> cToB = toLens(C::getB, C::setB, B::new);
        Lens<D, Object> dToObject = dToC.andThen(cToB).andThen(cToObject).andThen(objectToObject);

        objectC = new C();
        objectD = new D();
        objectD.setC(objectC);

        Object object = dToObject.apply(objectD);
        assertThat(object, nullValue());
    }

    @Test
    public void Lens_failure_null_value() {
        thrown.expect(IllegalStateException.class);
        cToA.apply(null);
    }
}
