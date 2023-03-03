package com.resolvix.lib.util.function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CheckedExceptionEmittingBiFunctionSupplierUT {

    private CheckedExceptionEmittingBiFunctionSupplier<
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

    @Mock
    private TriConsumer<Integer, Integer, IllegalArgumentException>
        checkedExceptionTriConsumer;

    private BiFunction<Integer, Integer, Optional<Boolean>> bifunction;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        bifunctionSupplier = new CheckedExceptionEmittingBiFunctionSupplier<>(
            CheckedExceptionEmittingBiFunctionSupplierUT::isSumGreaterThanZeroAndLessThanTen,
            checkedExceptionTriConsumer);
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

        verifyZeroInteractions(checkedExceptionTriConsumer);
    }

    @Test
    public void suppliedFunctionWhenGeneratesOneException() {
        Optional<Boolean> result = bifunction.apply(null, null);
        assertFalse(result.isPresent());

        verify(checkedExceptionTriConsumer).accept(isNull(), isNull(), any(IllegalArgumentException.class));
        verifyNoMoreInteractions(checkedExceptionTriConsumer);
    }
}
