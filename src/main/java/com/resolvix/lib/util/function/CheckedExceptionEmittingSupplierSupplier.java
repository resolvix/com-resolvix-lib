package com.resolvix.lib.util.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CheckedExceptionEmittingSupplierSupplier<
    O, E extends Exception>
    implements Supplier<Supplier<Optional<O>>>
{

    private CheckedSupplier<O, E> checkedSupplier;

    private Consumer<E> emissionConsumer;

    public CheckedExceptionEmittingSupplierSupplier(
        CheckedSupplier<O, E> checkedSupplier,
        Consumer<E> emissionConsumer) {
        super();
        this.checkedSupplier = checkedSupplier;
        this.emissionConsumer = emissionConsumer;
    }

    @Override
    public Supplier<Optional<O>> get() {
        return () -> {
            try {
                return Optional.of(checkedSupplier.get());
            } catch (Exception e) {
                emissionConsumer.accept((E) e);
                return Optional.empty();
            }
        };
    }
}
