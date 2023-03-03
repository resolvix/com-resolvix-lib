package com.resolvix.lib.util.function;

import java.util.Optional;
import java.util.function.Supplier;

public class CheckedExceptionContextEmittingBiFunctionSupplier<
    C, D, I, J, O, E extends Exception>
    implements Supplier<ContextBiFunction<D, I, J, Optional<O>>>
{

    private C outerContext;

    private CheckedBiFunction<I, J, O, E> checkedBiFunction;

    private PentConsumer<C, D, I, J, E> emissionConsumer;

    public CheckedExceptionContextEmittingBiFunctionSupplier(
        C outerContext,
        CheckedBiFunction<I, J, O, E> checkedBiFunction,
        PentConsumer<C, D, I, J, E> emissionConsumer)
    {
        super();
        this.outerContext = outerContext;
        this.checkedBiFunction = checkedBiFunction;
        this.emissionConsumer = emissionConsumer;
    }

    @Override
    public ContextBiFunction<D, I, J, Optional<O>> get() {
        return (D innerContext, I i, J j) -> {
            try {
                return Optional.of(checkedBiFunction.apply(i, j));
            } catch (Exception e) {
                emissionConsumer.accept(outerContext, innerContext, i, j, (E) e);
                return Optional.empty();
            }
        };
    }
}
