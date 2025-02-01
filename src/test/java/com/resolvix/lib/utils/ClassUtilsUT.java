package com.resolvix.lib.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ClassUtilsUT {

    private interface A {

        Boolean getBoolean();

        void setBoolean(Boolean value);
    }

    private static class B
        implements A
    {

        private Boolean value;

        B() {
            this.value = Boolean.FALSE;
        }

        @Override
        public Boolean getBoolean() {
            return value;
        }

        @Override
        public void setBoolean(Boolean value) {
            this.value = value;
        }
    }

    private static class C
        implements A
    {

        private Boolean value;

        C() {
            this.value = Boolean.TRUE;
        }

        @Override
        public Boolean getBoolean() {
            return value;
        }

        @Override
        public void setBoolean(Boolean value) {
            this.value = value;
        }
    }

    @Test
    public void testInstantiateClass()
        throws Exception
    {
        A a = ClassUtils.instantiateClass(B.class);
        assertThat(a, isA(B.class));
    }

    @Test
    public void testInstantiateClasses()
        throws Exception
    {
        @SuppressWarnings("unchecked")
        A[] as = ClassUtils.instantiateClasses(
            new Class[] { B.class, C.class}, A.class);
        assertThat(as[0], isA(B.class));
        assertThat(as[1], isA(C.class));
        assertThat(as[0], not(sameInstance(as[1])));
    }
}
