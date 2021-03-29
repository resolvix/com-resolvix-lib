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
    public void safeGivenNullValue() {
        assertThat(
            ObjectUtils.safe(null, ObjectUtilsUT::addOne),
            nullValue());
    }

    @Test
    public void safeGivenNonNullValue() {
        assertThat(
            ObjectUtils.safe(Integer.valueOf(1), ObjectUtilsUT::addOne),
            equalTo(Integer.valueOf(2)));
    }
}
