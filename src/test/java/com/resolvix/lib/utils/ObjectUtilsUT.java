package com.resolvix.lib.utils;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ObjectUtilsUT {

    private static Integer addOne(Integer in) {
        return Integer.valueOf(in.intValue() + 1);
    }

    @Test
    public void safe1GivenNullValue() {
        assertThat(
            ObjectUtils.safe(null, ObjectUtilsUT::addOne),
            nullValue());
    }

    @Test
    public void safe1GivenNonNullValue() {
        assertThat(
            ObjectUtils.safe(Integer.valueOf(1), ObjectUtilsUT::addOne),
            equalTo(Integer.valueOf(2)));
    }

    @Test
    public void safe2GivenNullValue() {
        assertThat(
            ObjectUtils.safe(null, ObjectUtilsUT::addOne, ObjectUtilsUT::addOne),
            nullValue());
    }

    @Test
    public void safe2GivenNonNullValueAndFunctionProducingNullValue() {
        assertThat(
            ObjectUtils.safe(Integer.valueOf(1), (Integer i) -> null, ObjectUtilsUT::addOne),
            nullValue());
    }

    @Test
    public void safe2GivenNonNullValueAndFunctionProducingNonNullValueAndFunctionProducingNullValue() {
        assertThat(
            ObjectUtils.safe(Integer.valueOf(1), ObjectUtilsUT::addOne, (Integer i) -> null),
            nullValue());
    }

    @Test
    public void safe2GivenNonNullValueAndFunctionProducingNonNullValueAndFunctionProductingNonNullValue() {
        assertThat(
            ObjectUtils.safe(Integer.valueOf(1), ObjectUtilsUT::addOne, ObjectUtilsUT::addOne),
            equalTo(Integer.valueOf(3)));
    }
}
