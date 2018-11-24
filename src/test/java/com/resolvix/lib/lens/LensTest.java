package com.resolvix.lib.lens;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import static com.resolvix.lib.lens.Lens.toLens;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class LensTest {

    private static class A {

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

    private static class B {

        private A a;

        public A getA() {
            return a;
        }

        public void setA(A a) {
            this.a = a;
        }
    }

    private static class C {

        private B b;

        public B getB() {
            return b;
        }

        public void setB(B b) {
            this.b = b;
        }
    }

    private A objectA;

    private B objectB;

    private C objectC;

    private Integer expectedI;

    private String expectedS;

    @Before
    public void before() {

    }

    private <T> Matcher<T> ofTypeAndValue(Class<T> classT, T t) {
        return both(isA(classT)).and(equalTo(t));
    }

    @Test
    public void Lens_success() {
        Lens<C, A> cToA = toLens(B::new, C::getB, C::getB)
            .andThen(A::new, B::getA, B::setA);

        cToA.apply(objectC).setI(expectedI);
        cToA.apply(objectC).setS(expectedS);

        assertThat(
            objectC.getB().getA(), allOf(
                hasProperty("i", ofTypeAndValue(Integer.class, expectedI)),
                hasProperty("s", ofTypeAndValue(String.class, expectedS))));
    }
}
