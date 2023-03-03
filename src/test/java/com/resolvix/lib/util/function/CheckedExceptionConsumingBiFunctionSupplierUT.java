package com.resolvix.lib.util.function;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckedExceptionConsumingBiFunctionSupplierUT {

    private CheckedExceptionConsumingBiFunctionSupplier<
        Integer, Integer, Boolean, IllegalArgumentException>
        bifunctionSupplier;

    private static Boolean isSumGreaterThanZeroAndLessThanTen(Integer left, Integer right)
        throws IllegalArgumentException
    {
        if (left == null || right == null)
            throw new IllegalArgumentException();

        Integer sum = left + right;

        return Boolean.valueOf(sum > 0 && sum < 10);
    }

    private BiFunction<Integer, Integer, Optional<Boolean>> bifunction;

    @Before
    public void before() {
        bifunctionSupplier = new CheckedExceptionConsumingBiFunctionSupplier<>(
            CheckedExceptionConsumingBiFunctionSupplierUT::isSumGreaterThanZeroAndLessThanTen);
        bifunction = bifunctionSupplier.get();
    }

    @Test
    public void suppliedFunctionWhenGeneratesNoExceptions() {
        Optional<Boolean> firstOptionalValue = bifunction.apply(0, 0);
        assertTrue(firstOptionalValue.isPresent());
        assertThat(firstOptionalValue.get(), equalTo(false));

        Optional<Boolean> secondOptionalValue = bifunction.apply(0, 1);
        assertTrue(secondOptionalValue.isPresent());
        assertThat(secondOptionalValue.get(), equalTo(true));

        Optional<Boolean> thirdOptionalValue = bifunction.apply(0, 9);
        assertTrue(thirdOptionalValue.isPresent());
        assertThat(thirdOptionalValue.get(), equalTo(true));

        Optional<Boolean> fourthOptionalValue = bifunction.apply(1, 9);
        assertTrue(fourthOptionalValue.isPresent());
        assertThat(fourthOptionalValue.get(), equalTo(false));

        assertThat(bifunctionSupplier.getExceptions().size(), equalTo(0));
    }

    @Test
    public void suppliedFunctionWhenGeneratesOneException() {
        Optional<Boolean> result = bifunction.apply(null, null);
        assertFalse(result.isPresent());
        assertThat(bifunctionSupplier.getExceptions().size(), equalTo(1));
    }
}
