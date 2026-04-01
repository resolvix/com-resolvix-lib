package com.resolvix.lib.util.function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CheckedExceptionContextEmittingFunctionSupplierUT {

    private CheckedExceptionContextEmittingFunctionSupplier<
        Integer, Integer, Integer, Boolean, IllegalArgumentException>
        contextFunctionSupplier;

    private static Boolean isGreaterThanZeroAndLessThanTen(Integer input)
        throws IllegalArgumentException
    {
        if (input == null)
            throw new IllegalArgumentException();

        return Boolean.valueOf(input > 0 && input < 10);
    }

    @Mock
    private QuadConsumer<Integer, Integer, Integer, IllegalArgumentException>
        contextCheckedExceptionBiConsumer;

    private ContextFunction<Integer, Integer, Optional<Boolean>> contextFunction;

    private Integer outerContext = -1;

    private Integer innerContext = 1;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        contextFunctionSupplier = new CheckedExceptionContextEmittingFunctionSupplier<>(
            outerContext,
            CheckedExceptionContextEmittingFunctionSupplierUT::isGreaterThanZeroAndLessThanTen,
            contextCheckedExceptionBiConsumer);
        contextFunction = contextFunctionSupplier.get();
    }

    @Test
    public void suppliedFunctionWhenGeneratesNoExceptions() {

        Optional<Boolean> firstOptionalValue = contextFunction.apply(innerContext++, 0);
        assertTrue(firstOptionalValue.isPresent());
        assertThat(firstOptionalValue.get(), equalTo(false));

        Optional<Boolean> secondOptionalValue = contextFunction.apply(innerContext++, 1);
        assertTrue(secondOptionalValue.isPresent());
        assertThat(secondOptionalValue.get(), equalTo(true));

        Optional<Boolean> thirdOptionalValue = contextFunction.apply(innerContext++, 9);
        assertTrue(thirdOptionalValue.isPresent());
        assertThat(thirdOptionalValue.get(), equalTo(true));

        Optional<Boolean> fourthOptionalValue = contextFunction.apply(innerContext++, 10);
        assertTrue(fourthOptionalValue.isPresent());
        assertThat(fourthOptionalValue.get(), equalTo(false));

        verifyZeroInteractions(contextCheckedExceptionBiConsumer);
    }

    @Test
    public void suppliedFunctionWhenGeneratesOneException() {
        Optional<Boolean> result = contextFunction.apply(innerContext, null);
        assertFalse(result.isPresent());
        verify(contextCheckedExceptionBiConsumer).accept(
            eq(outerContext), eq(innerContext), isNull(), any(IllegalArgumentException.class));
        verifyNoMoreInteractions(contextCheckedExceptionBiConsumer);
    }
}
