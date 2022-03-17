package com.resolvix.lib.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class ObjectUtilsUT {

    private static final Integer ONE = Integer.valueOf(1);

    private static final Integer TWO = Integer.valueOf(2);

    private static final AtomicReference NULL_REF = new AtomicReference();

    private static final AtomicReference ONE_REF = new AtomicReference(ONE);

    private static final AtomicReference TWO_REF = new AtomicReference(TWO);

    private static Integer addOne(Integer in) {
        return Integer.valueOf(in.intValue() + 1);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //
    //  safe/1
    //

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

    //
    //  safe/2
    //

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

    //
    //  safeEquals/2
    //

    @Test
    public void safeEquals2GivenNullOperands() {
        assertTrue(
            ObjectUtils.safeEquals(null, null));
    }

    @Test
    public void safeEquals2GivenNullLeftOperandNonNullRightOperand() {
        assertFalse(
            ObjectUtils.safeEquals(null, ONE));
    }

    @Test
    public void safeEquals2GivenNonNullLeftOperandNullRightOperand() {
        assertFalse(
            ObjectUtils.safeEquals(ONE, null));
    }

    @Test
    public void safeEquals2GivenEqualLeftOperandRightOperand() {
        assertTrue(
            ObjectUtils.safeEquals(ONE, ONE));
    }

    @Test
    public void safeEquals2GivenNonEqualLeftOperandRightOperand() {
        assertFalse(
            ObjectUtils.safeEquals(ONE, TWO));
    }

    //
    //  safeDereferenceEquals/3
    //
    @Test
    public void safeDereferenceEquals3GivenNullFunction() {
        thrown.expect(AssertionError.class);
        ObjectUtils.safeDereferenceEquals(null, null, null);
    }

    @Test
    public void safeDereferenceEquals3GivenNullOperands() {
        assertTrue(
            ObjectUtils.safeDereferenceEquals((AtomicReference) null, (AtomicReference) null, AtomicReference::get));
    }

    @Test
    public void safeDereferenceEquals3GivenNullLeftOperandNonNullRightOperand() {
        assertFalse(
            ObjectUtils.safeDereferenceEquals(null, ONE_REF, AtomicReference::get));
    }

    @Test
    public void safeDereferenceEquals3GivenNonNullLeftOperandNullRightOperand() {
        assertFalse(
            ObjectUtils.safeDereferenceEquals(ONE_REF, null, AtomicReference::get));
    }

    @Test
    public void safeDereferenceEquals3GivenEqualLeftOperandRightOperand() {
        assertTrue(
            ObjectUtils.safeDereferenceEquals(ONE_REF, ONE_REF, AtomicReference::get));
    }

    @Test
    public void safeDereferenceEquals3GivenNonEqualLeftOperandRightOperand() {
        assertFalse(
            ObjectUtils.safeDereferenceEquals(ONE_REF, TWO_REF, AtomicReference::get));
    }
}
