package com.resolvix.lib.utils;

import org.junit.Test;

import java.util.Optional;

import static com.resolvix.lib.utils.FunctionUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThrows;

public class FunctionUtilsUT {

    private static class LocalNullPointerException
        extends Exception
    {
        LocalNullPointerException() {
            super();
        }
    }

    private static class LocalOutOfBoundsException
        extends Exception
    {
        LocalOutOfBoundsException() {
            super();
        }
    }

    private static Optional<Boolean> isNonZero(Integer input)
        throws LocalNullPointerException
    {
        if (input == null)
            throw new LocalNullPointerException();

        if (input.intValue() == 0)
            return Optional.of(false);

        return Optional.of(true);
    }

    private static Integer betweenZeroAndTen(Integer input)
        throws LocalNullPointerException, LocalOutOfBoundsException
    {
        if (input == null)
            throw new LocalNullPointerException();

        if (input.intValue() < 0 || input.intValue() > 10)
            throw new LocalOutOfBoundsException();

        return input;
    }

    private static <E extends Exception> Integer mod11(Integer input, E exception)
        throws E
    {
        if (input == null)
            throw exception;

        return input % 11;
    }

    private static <E extends Exception> Integer mod11excludingZero(Integer input, E exception)
        throws E
    {
        if (input == null)
            throw (E) exception;

        Integer output = input % 11;

        if (output == 0)
            throw (E) exception;

        return output;
    }

    private static <E extends Exception> Integer uncheckedMod11(Integer input, E exception)
    {
        if (input == null)
            return 0;

        return input % 11;
    }

    //
    //  FunctionUtils::onException
    //

    @Test
    public void onExceptionGivenCheckedFunctionThatThrowsTwoExceptionTypesWhenValidOperandsAreUsed()
        throws LocalOutOfBoundsException, LocalNullPointerException, Exception
    {
        assertThat(onExceptionTry(FunctionUtilsUT::betweenZeroAndTen, 0, FunctionUtilsUT::mod11), equalTo(0));
        assertThat(onExceptionTry(FunctionUtilsUT::betweenZeroAndTen, 10, FunctionUtilsUT::mod11), equalTo(10));
    }

    @Test
    public void onExceptionGivenCheckedFunctionThatThrowsTwoExceptionTypesWhenOutOfBoundsOperandsAreUsed()
        throws Exception
    {
        assertThat(onExceptionTry(FunctionUtilsUT::betweenZeroAndTen, 99, FunctionUtilsUT::mod11), equalTo(0));
        assertThat(onExceptionTry(FunctionUtilsUT::betweenZeroAndTen, 120, FunctionUtilsUT::mod11), equalTo(10));
    }

    @Test
    public void onExceptionGivenCheckedFunctionThatThrowsTwoExceptionTypesWhenOutOfBoundsOperandsAndUncheckedOnExceptionMethodAreUsed()
    {
        assertThat(onExceptionDo(FunctionUtilsUT::betweenZeroAndTen, 99, FunctionUtilsUT::uncheckedMod11), equalTo(0));
        assertThat(onExceptionDo(FunctionUtilsUT::betweenZeroAndTen, 120, FunctionUtilsUT::uncheckedMod11), equalTo(10));
    }

    @Test
    public void onExceptionGivenCheckedFunctionThatThrowsTwoExceptionTypesWhenExceptionIsRethrownOperandsAreUsed()
    {
        assertThrows(
            LocalOutOfBoundsException.class,
            () -> onExceptionTry(FunctionUtilsUT::betweenZeroAndTen, 99, FunctionUtilsUT::mod11excludingZero));
    }

    //
    //  FunctionUtils::toUnchecked
    //

    @Test
    public void toUncheckedGivenCheckedFunctionThatThrowsOneExceptionTypeWhenValidOperandIsUsed() {
        assertThat(toUnchecked(FunctionUtilsUT::isNonZero, 0, ExceptionUtils::toRuntimeException).get(), equalTo(Boolean.FALSE));
        assertThat(toUnchecked(FunctionUtilsUT::isNonZero, 1, ExceptionUtils::toRuntimeException).get(), equalTo(Boolean.TRUE));
    }

    @Test
    public void toUncheckedGivenCheckedFunctionThatThrowsOneExceptionTypeWhenInvalidOperandIsUsed() {
        RuntimeException runtimeException = assertThrows(
            RuntimeException.class,
            () -> toUnchecked(FunctionUtilsUT::isNonZero, null, ExceptionUtils::toRuntimeException));

        assertThat(
            runtimeException.getCause(),
            isA(LocalNullPointerException.class));
    }

    @Test
    public void toUncheckedGivenCheckedFunctionThatThrowsTwoExceptionTypesWhenValidOperandIsUsed() {
        toUnchecked(FunctionUtilsUT::betweenZeroAndTen, 0, ExceptionUtils::toRuntimeException);
        toUnchecked(FunctionUtilsUT::betweenZeroAndTen, 10, ExceptionUtils::toRuntimeException);
    }

    @Test
    public void toUncheckedGivenCheckedFunctionThatThrowsTwoExceptionTypesWhenInvalidOperandIsUsed() {
        RuntimeException runtimeException = assertThrows(
            RuntimeException.class,
            () -> toUnchecked(FunctionUtilsUT::betweenZeroAndTen, null, ExceptionUtils::toRuntimeException));

        assertThat(
            runtimeException.getCause(),
            isA(LocalNullPointerException.class));
    }

    @Test
    public void toUncheckedGivenCheckedFunctionThatThrowsTwoExceptionTypesWhenOutOfLowerBoundOperandIsUsed() {
        RuntimeException runtimeException = assertThrows(
            RuntimeException.class,
            () -> toUnchecked(FunctionUtilsUT::betweenZeroAndTen, -1, ExceptionUtils::toRuntimeException));

        assertThat(
            runtimeException.getCause(),
            isA(LocalOutOfBoundsException.class));
    }

    @Test
    public void toUncheckedGivenCheckedFunctionThatThrowsTwoExceptionTypesWhenOutOfUpperBoundOperandIsUsed() {
        RuntimeException runtimeException = assertThrows(
            RuntimeException.class,
            () -> toUnchecked(FunctionUtilsUT::betweenZeroAndTen, 11, ExceptionUtils::toRuntimeException));

        assertThat(
            runtimeException.getCause(),
            isA(LocalOutOfBoundsException.class));
    }
}
