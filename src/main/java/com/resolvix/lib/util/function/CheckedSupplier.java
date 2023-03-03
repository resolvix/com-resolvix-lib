package com.resolvix.lib.util.function;

import java.util.Optional;
import java.util.function.Supplier;

@FunctionalInterface
public interface CheckedSupplier<O, E extends Exception>
{

    O get() throws E;
}
