package com.resolvix.lib.util.function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CheckedExceptionEmittingFunctionSupplierUT {

    private CheckedExceptionEmittingFunctionSupplier<
        Integer, Boolean, IllegalArgumentException>
        functionSupplier;

    private static Boolean isGreaterThanZeroAndLessThanTen(Integer input)
        throws IllegalArgumentException
    {
        if (input == null)
            throw new IllegalArgumentException();

        return Boolean.valueOf(input > 0 && input < 10);
    }

    @Mock
    private BiConsumer<Integer, IllegalArgumentException> checkedExceptionBiConsumer;

    private Function<Integer, Optional<Boolean>> function;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        functionSupplier = new CheckedExceptionEmittingFunctionSupplier<>(
            CheckedExceptionEmittingFunctionSupplierUT::isGreaterThanZeroAndLessThanTen,
            checkedExceptionBiConsumer);
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

        verifyZeroInteractions(checkedExceptionBiConsumer);
    }

    @Test
    public void suppliedFunctionWhenGeneratesOneException() {
        Optional<Boolean> result = function.apply(null);
        assertFalse(result.isPresent());
        verify(checkedExceptionBiConsumer).accept(ArgumentMatchers.isNull(), any(IllegalArgumentException.class));
        verifyNoMoreInteractions(checkedExceptionBiConsumer);
    }
}
