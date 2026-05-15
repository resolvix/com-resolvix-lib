package com.resolvix.lib.utils;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogUtilsUT {

    private static class ObjectWithAccessor
    {
        private final Integer i;

        ObjectWithAccessor(Integer i) {
            this.i = i;
        }

        Integer getI() {
            return i;
        }
    }

    private static class NestedObjectWithAccessor
    {
        private final ObjectWithAccessor objectWithAccessor;

        NestedObjectWithAccessor() {
            this.objectWithAccessor = null;
        }

        NestedObjectWithAccessor(Integer i) {
            this.objectWithAccessor = new ObjectWithAccessor(i);
        }

        ObjectWithAccessor getObjectWithAccessor() {
            return objectWithAccessor;
        }
    }

    @Before
    public void before() {
        //
    }

    @Test
    public void sanitize_String_WhenNull() {
        assertThat(LogUtils.sanitize(null), nullValue());
    }

    @Test
    public void sanitize_String_WhenNonNull() {
        assertThat(LogUtils.sanitize(""), equalTo(""));
    }

    @Test
    public void sanitize_ObjectUsingString_WhenNull() {
        assertThat(LogUtils.sanitize((Integer) null), nullValue());
    }

    @Test
    public void sanitize_ObjectUsingToString_WhenNonNull() {
        assertThat(LogUtils.sanitize(Integer.MAX_VALUE), equalTo(Integer.toString(Integer.MAX_VALUE)));
    }

    @Test
    public void sanitize_ObjectUsingAccessor_WhenNull() {
        assertThat(LogUtils.sanitize((ObjectWithAccessor) null, ObjectWithAccessor::getI), nullValue());
    }

    @Test
    public void sanitize_ObjectUsingAccessor_WhenNonNullAccessReturnsNull() {
        ObjectWithAccessor objectWithAccessor = new ObjectWithAccessor(null);
        assertThat(LogUtils.sanitize(objectWithAccessor, ObjectWithAccessor::getI), nullValue());
    }

    @Test
    public void sanitize_ObjectUsingAccessor_WhenNonNullAccessorReturnsString() {
        ObjectWithAccessor objectWithAccessor = new ObjectWithAccessor(Integer.MAX_VALUE);
        assertThat(LogUtils.sanitize(objectWithAccessor, ObjectWithAccessor::getI), equalTo(Integer.toString(Integer.MAX_VALUE)));
    }

    @Test
    public void sanitize_NestedObjectUsingAccessor_WhenNonNull() {
        assertThat(
                LogUtils.sanitize(null, NestedObjectWithAccessor::getObjectWithAccessor, ObjectWithAccessor::getI),
                nullValue());
    }

    @Test
    public void sanitize_NestedObjectUsingAccessor_WhenNonNullFirstAccessorReturnsNull() {
        NestedObjectWithAccessor nestedObjectWithAccessor = new NestedObjectWithAccessor();
        assertThat(
                LogUtils.sanitize(nestedObjectWithAccessor, NestedObjectWithAccessor::getObjectWithAccessor, ObjectWithAccessor::getI),
                nullValue());
    }

    @Test
    public void sanitize_NestedObjectUsingAccessor_WhenNonNullFirstAccessorReturnsNonNullSecondAccessorReturnsNull() {
        NestedObjectWithAccessor nestedObjectWithAccessor = new NestedObjectWithAccessor(null);
        assertThat(
                LogUtils.sanitize(nestedObjectWithAccessor, NestedObjectWithAccessor::getObjectWithAccessor, ObjectWithAccessor::getI),
                nullValue());
    }

    @Test
    public void sanitize_NestedObjectUsingAccessor_WhenNonNullFirstAccessorReturnsNonNullSecondAccessorReturnsNonNull() {
        NestedObjectWithAccessor nestedObjectWithAccessor = new NestedObjectWithAccessor(Integer.MAX_VALUE);
        assertThat(
                LogUtils.sanitize(nestedObjectWithAccessor, NestedObjectWithAccessor::getObjectWithAccessor, ObjectWithAccessor::getI),
                equalTo(Integer.toString(Integer.MAX_VALUE)));
    }
}
