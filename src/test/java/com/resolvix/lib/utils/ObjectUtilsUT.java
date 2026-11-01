package com.resolvix.lib.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThrows;

public class ObjectUtilsUT {

    private static Integer addOne(Integer in) {
        return Integer.valueOf(in.intValue() + 1);
    }

    @Test
    public void coalesce() {
        assertThat(
                ObjectUtils.coalesce(null), nullValue());
        assertThat(
                ObjectUtils.coalesce(null, null), nullValue());
        assertThat(
                ObjectUtils.coalesce(null, null, 1), equalTo(1));
        assertThat(
                ObjectUtils.coalesce(null, null, null, "One"), equalTo("One"));
        assertThat(
                ObjectUtils.coalesce(1, 2), equalTo(1));
        assertThat(
                ObjectUtils.coalesce("One", "Two"), equalTo("One"));
    }

    @Test
    public void nullIf() {
        assertThat(
                ObjectUtils.nullIf(null, (operand) -> { return operand == null; }), nullValue());
        assertThat(
                ObjectUtils.nullIf(null, String::isBlank), nullValue());
        assertThat(
                ObjectUtils.nullIf("", String::isEmpty), nullValue());
        assertThat(
                ObjectUtils.nullIf(" ", String::isBlank), nullValue());
        assertThat(
                ObjectUtils.nullIf("1", String::isBlank), equalTo("1"));
        assertThrows(
                IllegalArgumentException.class, () -> ObjectUtils.nullIf(null, null));
        assertThrows(
                IllegalArgumentException.class, () -> ObjectUtils.nullIf("1", null));
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
