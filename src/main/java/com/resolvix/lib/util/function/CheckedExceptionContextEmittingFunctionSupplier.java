package com.resolvix.lib.util.function;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CheckedExceptionContextEmittingFunctionSupplier<
    C, D, I, O, E extends Exception>
    implements Supplier<ContextFunction<D, I, Optional<O>>>
{

    private C outerContext;

    private CheckedFunction<I, O, E> checkedFunction;

    private QuadConsumer<C, D, I, E> emissionConsumer;

    public CheckedExceptionContextEmittingFunctionSupplier(
        C outerContext,
        CheckedFunction<I, O, E> checkedFunction,
        QuadConsumer<C, D, I, E> emissionConsumer)
    {
        super();
        this.outerContext = outerContext;
        this.checkedFunction = checkedFunction;
        this.emissionConsumer = emissionConsumer;
    }

    @Override
    public ContextFunction<D, I, Optional<O>> get() {
        return (D innerContext, I i) -> {
            try {
                return Optional.of(checkedFunction.apply(i));
            } catch (Exception e) {
                emissionConsumer.accept(outerContext, innerContext, i, (E) e);
                return Optional.empty();
            }
        };
    }
}
