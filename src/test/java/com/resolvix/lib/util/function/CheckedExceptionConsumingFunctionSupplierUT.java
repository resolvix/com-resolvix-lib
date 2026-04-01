package com.resolvix.lib.util.function;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckedExceptionConsumingFunctionSupplierUT {

    private CheckedExceptionConsumingFunctionSupplier<
        Integer, Boolean, IllegalArgumentException>
        functionSupplier;

    private static Boolean isGreaterThanZeroAndLessThanTen(Integer input)
        throws IllegalArgumentException
    {
        if (input == null)
            throw new IllegalArgumentException();

        return Boolean.valueOf(input > 0 && input < 10);
    }

    Function<Integer, Optional<Boolean>> function;

    @Before
    public void before() {
        functionSupplier = new CheckedExceptionConsumingFunctionSupplier<>(
            CheckedExceptionConsumingFunctionSupplierUT::isGreaterThanZeroAndLessThanTen);
        function = functionSupplier.get();
    }

    @Test
    public void suppliedFunctionWhenGeneratesNoExceptions() {

        Optional<Boolean> firstOptionalValue = function.apply(0);
        assertTrue(firstOptionalValue.isPresent());
        assertThat(firstOptionalValue.get(), equalTo(false));

        Optional<Boolean> secondOptionalValue = function.apply(1);
        assertTrue(secondOptionalValue.isPresent());
        assertThat(secondOptionalValue.get(), equalTo(true));

        Optional<Boolean> thirdOptionalValue = function.apply(9);
        assertTrue(thirdOptionalValue.isPresent());
        assertThat(thirdOptionalValue.get(), equalTo(true));

        Optional<Boolean> fourthOptionalValue = function.apply(10);
        assertTrue(fourthOptionalValue.isPresent());
        assertThat(fourthOptionalValue.get(), equalTo(false));

        assertThat(functionSupplier.getExceptions().size(), equalTo(0));
    }

    @Test
    public void suppliedFunctionWhenGeneratesOneException() {
        Optional<Boolean> result = function.apply(null);
        assertFalse(result.isPresent());
        assertThat(functionSupplier.getExceptions().size(), equalTo(1));
    }
}
