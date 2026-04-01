package com.resolvix.lib.util.function;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckedExceptionConsumingSupplierSupplierUT {

    private static class LocalDivideByThreeException
        extends Exception
    {
        LocalDivideByThreeException() {
            super();
        }
    }

    private CheckedExceptionConsumingSupplierSupplier<
        Boolean, LocalDivideByThreeException>
        supplierSupplier;

    private int i = 0;

    private Boolean getI() throws LocalDivideByThreeException {
        if (i != 0 && i % 3 == 0)
            throw new LocalDivideByThreeException();

        return Boolean.valueOf(i++ % 3 == 2);
    }

    @Before
    public void before() {
        supplierSupplier = new CheckedExceptionConsumingSupplierSupplier<>(
            this::getI);
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

        assertThat(supplierSupplier.getExceptions().size(), equalTo(0));
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

        assertThat(supplierSupplier.getExceptions().size(), equalTo(0));

        Optional<Boolean> fourthOptionalValue = supplier.get();
        assertFalse(fourthOptionalValue.isPresent());
        assertThat(supplierSupplier.getExceptions().size(), equalTo(1));
    }
}
