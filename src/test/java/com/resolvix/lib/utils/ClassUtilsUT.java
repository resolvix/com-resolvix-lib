package com.resolvix.lib.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ClassUtilsUT {

    private static class LocalClass {

        private Boolean value;

        LocalClass() {
            this.value = false;
        }

        boolean getBoolean() {
            return value;
        }

        void setBoolean(Boolean value) {
            this.value = value;
        }
    }

    @Test
    public void testInstantiateClass()
        throws Exception
    {
        LocalClass localClass = ClassUtils.instantiateClass(LocalClass.class);
        assertThat(localClass, isA(LocalClass.class));
    }

    @Test
    public void testInstantiateClasses()
        throws Exception
    {
        @SuppressWarnings("unchecked")
        LocalClass[] localClasses = ClassUtils.instantiateClasses(
            (Class<LocalClass>[]) new Class[] {LocalClass.class, LocalClass.class}, LocalClass.class);
        assertThat(localClasses[0], isA(LocalClass.class));
        assertThat(localClasses[1], isA(LocalClass.class));
        assertThat(localClasses[0], not(sameInstance(localClasses[1])));
    }
}
