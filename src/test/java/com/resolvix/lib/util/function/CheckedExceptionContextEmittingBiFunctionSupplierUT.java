package com.resolvix.lib.util.function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CheckedExceptionContextEmittingBiFunctionSupplierUT {

    private CheckedExceptionContextEmittingBiFunctionSupplier<
        Integer, Integer, Integer, Integer, Boolean, IllegalArgumentException>
        contextBiFunctionSupplier;

    private static Boolean isSumGreaterThanZeroAndLessThanTen(Integer left, Integer right)
        throws IllegalArgumentException
    {
        if (left == null || right == null)
            throw new IllegalArgumentException();

        Integer sum = left + right;

        return java.lang.Boolean.valueOf(sum > 0 && sum < 10);
    }

    @Mock
    private PentConsumer<Integer, Integer, Integer, Integer, IllegalArgumentException>
        contextCheckedExceptionBiConsumer;

    private ContextBiFunction<Integer, Integer, Integer, Optional<Boolean>> contextBiFunction;

    private Integer outerContext = -1;

    private Integer innerContext = 1;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        contextBiFunctionSupplier = new CheckedExceptionContextEmittingBiFunctionSupplier<>(
            outerContext,
            CheckedExceptionContextEmittingBiFunctionSupplierUT::isSumGreaterThanZeroAndLessThanTen,
            contextCheckedExceptionBiConsumer);
        contextBiFunction = contextBiFunctionSupplier.get();
    }

    @Test
    public void suppliedFunctionWhenGeneratesNoExceptions() {

        Optional<Boolean> firstOptionalValue = contextBiFunction.apply(innerContext++, 0, 0);
        assertTrue(firstOptionalValue.isPresent());
        assertThat(firstOptionalValue.get(), equalTo(false));

        Optional<Boolean> secondOptionalValue = contextBiFunction.apply(innerContext++, 1, 0);
        assertTrue(secondOptionalValue.isPresent());
        assertThat(secondOptionalValue.get(), equalTo(true));

        Optional<Boolean> thirdOptionalValue = contextBiFunction.apply(innerContext++, 9, 0);
        assertTrue(thirdOptionalValue.isPresent());
        assertThat(thirdOptionalValue.get(), equalTo(true));

        Optional<Boolean> fourthOptionalValue = contextBiFunction.apply(innerContext++, 9, 1);
        assertTrue(fourthOptionalValue.isPresent());
        assertThat(fourthOptionalValue.get(), equalTo(false));

        verifyZeroInteractions(contextCheckedExceptionBiConsumer);
    }

    @Test
    public void suppliedFunctionWhenGeneratesOneException() {
        Optional<Boolean> result
            = contextBiFunction.apply(innerContext, null, null);
        assertFalse(result.isPresent());
        verify(contextCheckedExceptionBiConsumer).accept(
            eq(outerContext), eq(innerContext), isNull(), isNull(),
            any(IllegalArgumentException.class));
        verifyNoMoreInteractions(contextCheckedExceptionBiConsumer);
    }

    @Test
    public void suppliedFunctionSampleUseCase() {
        List<Boolean> booleans = Arrays.asList(100, 200, 300)
            .stream()
            .map(number -> contextBiFunction.apply(number, null, null))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());

        assertTrue(booleans.isEmpty());
        verify(contextCheckedExceptionBiConsumer)
            .accept(eq(outerContext), eq(100), isNull(), isNull(), any(IllegalArgumentException.class));
        verify(contextCheckedExceptionBiConsumer)
            .accept(eq(outerContext), eq(200), isNull(), isNull(), any(IllegalArgumentException.class));
        verify(contextCheckedExceptionBiConsumer)
            .accept(eq(outerContext), eq(300), isNull(), isNull(), any(IllegalArgumentException.class));
        verifyNoMoreInteractions(contextCheckedExceptionBiConsumer);
    }
}
