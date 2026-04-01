package com.resolvix.lib.util.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CheckedExceptionEmittingFunctionSupplier<
    I, O, E extends Exception>
    implements Supplier<Function<I, Optional<O>>>
{

    private CheckedFunction<I, O, E> checkedFunction;

    private BiConsumer<I, E> emissionConsumer;

    public CheckedExceptionEmittingFunctionSupplier(
        CheckedFunction<I, O, E> checkedFunction,
        BiConsumer<I, E> emissionConsumer)
    {
        super();
        this.checkedFunction = checkedFunction;
        this.emissionConsumer = emissionConsumer;
    }

    @Override
    public Function<I, Optional<O>> get() {
        return (I i) -> {
            try {
                return Optional.of(checkedFunction.apply(i));
            } catch (Exception e) {
                emissionConsumer.accept(i, (E) e);
                return Optional.empty();
            }
        };
    }
}
