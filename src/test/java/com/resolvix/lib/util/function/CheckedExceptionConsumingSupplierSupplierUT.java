package com.resolvix.lib.util.function;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CheckedExceptionConsumingSupplierSupplierUT {

    private static class LocalDivideByThreeException
        extends Exception
    {
        LocalDivideByThreeException() {
            super();
        }
    }

    private static class LocalCheckedSupplier
        implements CheckedSupplier<Boolean, LocalDivideByThreeException>
    {

        private int i = 0;

        @Override
        public Boolean get() throws LocalDivideByThreeException {
            if (i != 0 && i % 3 == 0)
                throw new LocalDivideByThreeException();

            return Boolean.valueOf(i++ % 3 == 2);
        }
    }

    private CheckedExceptionConsumingSupplierSupplier<
        Boolean, LocalDivideByThreeException, LocalCheckedSupplier>
        supplierSupplier;

    @Before
    public void before() {
        supplierSupplier = new CheckedExceptionConsumingSupplierSupplier<>(
            new LocalCheckedSupplier());
    }

    @Test
    public void suppliedSupplierGeneratesNoExceptions() {
        Supplier<Optional<Boolean>> supplier
            = supplierSupplier.get();
        Optional<Boolean> firstOptionalValue = supplier.get();
        assertThat(firstOptionalValue.isPresent(), equalTo(true));
        assertThat(firstOptionalValue.get(), equalTo(false));

        Optional<Boolean> secondOptionalValue = supplier.get();
        assertThat(secondOptionalValue.isPresent(), equalTo(true));
        assertThat(secondOptionalValue.get(), equalTo(false));

        Optional<Boolean> thirdOptionalValue = supplier.get();
        assertThat(thirdOptionalValue.isPresent(), equalTo(true));
        assertThat(thirdOptionalValue.get(), equalTo(true));

        assertThat(supplierSupplier.getExceptions().size(), equalTo(0));
    }

    @Test
    public void suppliedSupplierGeneratesOneException() {
        Supplier<Optional<Boolean>> supplier
            = supplierSupplier.get();
        Optional<Boolean> firstOptionalValue = supplier.get();
        assertThat(firstOptionalValue.isPresent(), equalTo(true));
        assertThat(firstOptionalValue.get(), equalTo(false));

        Optional<Boolean> secondOptionalValue = supplier.get();
        assertThat(secondOptionalValue.isPresent(), equalTo(true));
        assertThat(secondOptionalValue.get(), equalTo(false));

        Optional<Boolean> thirdOptionalValue = supplier.get();
        assertThat(thirdOptionalValue.isPresent(), equalTo(true));
        assertThat(thirdOptionalValue.get(), equalTo(true));

        assertThat(supplierSupplier.getExceptions().size(), equalTo(0));

        Optional<Boolean> fourthOptionalValue = supplier.get();
        assertThat(fourthOptionalValue.isPresent(), equalTo(false));
        assertThat(supplierSupplier.getExceptions().size(), equalTo(1));
    }
}
