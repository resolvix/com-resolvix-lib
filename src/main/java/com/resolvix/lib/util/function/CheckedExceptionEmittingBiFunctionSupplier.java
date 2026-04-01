package com.resolvix.lib.util.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CheckedExceptionEmittingBiFunctionSupplier<
    I, J, O, E extends Exception>
    implements Supplier<BiFunction<I, J, Optional<O>>>
{

    private CheckedBiFunction<I, J, O, E> checkedBiFunction;

    private TriConsumer<I, J, E> emissionConsumer;

    public CheckedExceptionEmittingBiFunctionSupplier(
        CheckedBiFunction<I, J, O, E> checkedBiFunction,
        TriConsumer<I, J, E> emissionConsumer) {
        super();
        this.checkedBiFunction = checkedBiFunction;
        this.emissionConsumer = emissionConsumer;
    }

    @Override
    public BiFunction<I, J, Optional<O>> get() {
        return (I i, J j) -> {
            try {
                return Optional.of(checkedBiFunction.apply(i, j));
            } catch (Exception e) {
                emissionConsumer.accept(i, j, (E) e);
                return Optional.empty();
            }
        };
    }
}
