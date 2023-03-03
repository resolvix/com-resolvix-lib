package com.resolvix.lib.util.function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CheckedExceptionContextEmittingSupplierSupplierUT {

    private static class LocalDivideByThreeException
        extends Exception
    {
        LocalDivideByThreeException() {
            super();
        }
    }

    @Mock
    private TriConsumer<Integer, Integer, LocalDivideByThreeException>
        contextCheckedExceptionConsumer;

    private int i = 0;

    private Integer outerContext = -1;

    private Integer innerContext = 1;

    private CheckedExceptionContextEmittingSupplierSupplier<
        Integer, Integer, Boolean, LocalDivideByThreeException>
        contextSupplierSupplier;

    public Boolean getI() throws LocalDivideByThreeException {
        if (i != 0 && i % 3 == 0)
            throw new LocalDivideByThreeException();

        return Boolean.valueOf(i++ % 3 == 2);
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        this.contextSupplierSupplier
            = new CheckedExceptionContextEmittingSupplierSupplier<>(
                outerContext, this::getI, contextCheckedExceptionConsumer);
    }

    @Test
    public void suppliedSupplierGeneratesNoExceptions() {
        ContextSupplier<Integer, Optional<Boolean>> contextSupplier
            = contextSupplierSupplier.get();
        Optional<Boolean> firstOptionalValue
            = contextSupplier.get(innerContext++);
        assertTrue(firstOptionalValue.isPresent());
        assertThat(firstOptionalValue.get(), equalTo(false));

        Optional<Boolean> secondOptionalValue
            = contextSupplier.get(innerContext++);
        assertTrue(secondOptionalValue.isPresent());
        assertThat(secondOptionalValue.get(), equalTo(false));

        Optional<Boolean> thirdOptionalValue
            = contextSupplier.get(innerContext++);
        assertTrue(thirdOptionalValue.isPresent());
        assertThat(thirdOptionalValue.get(), equalTo(true));

        verifyZeroInteractions(contextCheckedExceptionConsumer);
    }

    @Test
    public void suppliedSupplierGeneratesOneException() {
        ContextSupplier<Integer, Optional<Boolean>> supplier
            = contextSupplierSupplier.get();

        Optional<Boolean> firstOptionalValue = supplier.get(innerContext++);
        assertTrue(firstOptionalValue.isPresent());
        assertThat(firstOptionalValue.get(), equalTo(false));

        Optional<Boolean> secondOptionalValue = supplier.get(innerContext++);
        assertTrue(secondOptionalValue.isPresent());
        assertThat(secondOptionalValue.get(), equalTo(false));

        Optional<Boolean> thirdOptionalValue = supplier.get(innerContext++);
        assertTrue(thirdOptionalValue.isPresent());
        assertThat(thirdOptionalValue.get(), equalTo(true));

        verifyZeroInteractions(contextCheckedExceptionConsumer);

        Optional<Boolean> fourthOptionalValue = supplier.get(innerContext);

        verify(contextCheckedExceptionConsumer).accept(
            eq(outerContext), eq(innerContext), any(LocalDivideByThreeException.class));
        verifyNoMoreInteractions(contextCheckedExceptionConsumer);
    }
}
