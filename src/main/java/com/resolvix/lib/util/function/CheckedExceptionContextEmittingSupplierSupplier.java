package com.resolvix.lib.util.function;

import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 *
 * @param <C> the type of the outer context, if any
 * @param <D> the type of the inner context, if any
 * @param <O> the type of results supplied
 * @param <E> the type of exceptions thrown
 */
public class CheckedExceptionContextEmittingSupplierSupplier<C, D, O, E extends Exception>
    implements Supplier<ContextSupplier<D, Optional<O>>>
{
    private C outerContext;

    private CheckedSupplier<O, E> checkedSupplier;

    private TriConsumer<C, D, E> emissionConsumer;

    public CheckedExceptionContextEmittingSupplierSupplier(
        C outerContext,
        CheckedSupplier<O, E> checkedContextSupplier,
        TriConsumer<C, D, E> emissionConsumer)
    {
        super();
        this.outerContext = outerContext;
        this.checkedSupplier = checkedContextSupplier;
        this.emissionConsumer = emissionConsumer;
    }

    @Override
    public ContextSupplier<D, Optional<O>> get() {
        return (D innerContext) -> {
            try {
                return Optional.of(checkedSupplier.get());
            } catch (Exception e) {
                emissionConsumer.accept(outerContext, innerContext, (E) e);
                return Optional.empty();
            }
        };
    }
}
