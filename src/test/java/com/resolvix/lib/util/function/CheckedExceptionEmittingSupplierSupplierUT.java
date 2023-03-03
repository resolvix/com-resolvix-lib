package com.resolvix.lib.util.function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CheckedExceptionEmittingSupplierSupplierUT {

    private static class LocalDivideByThreeException
        extends Exception
    {
        LocalDivideByThreeException() {
            super();
        }
    }

    @Mock
    private Consumer<LocalDivideByThreeException> checkedExceptionConsumer;

    private int i = 0;

    private CheckedExceptionEmittingSupplierSupplier<
        Boolean,
        LocalDivideByThreeException>
        supplierSupplier;

    public Boolean getI() throws LocalDivideByThreeException {
        if (i != 0 && i % 3 == 0)
            throw new LocalDivideByThreeException();

        return Boolean.valueOf(i++ % 3 == 2);
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        supplierSupplier = new CheckedExceptionEmittingSupplierSupplier<>(
            this::getI, checkedExceptionConsumer);
    }

    @Test
    public void suppliedSupplierGeneratesNoExceptions() {
        Supplier<Optional<Boolean>> supplier
            = supplierSupplier.get();
        Optional<Boolean> firstOptionalValue = supplier.get();
        assertTrue(firstOptionalValue.isPresent());
        assertThat(firstOptionalValue.get(), equalTo(false));

        Optional<Boolean> secondOptionalValue = supplier.get();
        assertTrue(secondOptionalValue.isPresent());
        assertThat(secondOptionalValue.get(), equalTo(false));

        Optional<Boolean> thirdOptionalValue = supplier.get();
        assertTrue(thirdOptionalValue.isPresent());
        assertThat(thirdOptionalValue.get(), equalTo(true));

        verifyZeroInteractions(checkedExceptionConsumer);
    }

    @Test
    public void suppliedSupplierGeneratesOneException() {
        Supplier<Optional<Boolean>> supplier
            = supplierSupplier.get();

        Optional<Boolean> firstOptionalValue = supplier.get();
        assertTrue(firstOptionalValue.isPresent());
        assertThat(firstOptionalValue.get(), equalTo(false));

        Optional<Boolean> secondOptionalValue = supplier.get();
        assertTrue(secondOptionalValue.isPresent());
        assertThat(secondOptionalValue.get(), equalTo(false));

        Optional<Boolean> thirdOptionalValue = supplier.get();
        assertTrue(thirdOptionalValue.isPresent());
        assertThat(thirdOptionalValue.get(), equalTo(true));

        verifyZeroInteractions(checkedExceptionConsumer);

        Optional<Boolean> fourthOptionalValue = supplier.get();

        verify(checkedExceptionConsumer).accept(any(LocalDivideByThreeException.class));
        verifyNoMoreInteractions(checkedExceptionConsumer);
    }
}
