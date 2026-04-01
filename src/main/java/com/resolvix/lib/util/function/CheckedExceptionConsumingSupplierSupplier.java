package com.resolvix.lib.util.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class CheckedExceptionConsumingSupplierSupplier<O, E extends Exception>
    implements Supplier<Supplier<Optional<O>>>
{

    private List<E> es;

    private CheckedSupplier<O, E> checkedSupplier;

    public CheckedExceptionConsumingSupplierSupplier(CheckedSupplier<O, E> checkedSupplier) {
        super();
        this.es = new ArrayList<>();
        this.checkedSupplier = checkedSupplier;
    }

    @Override
    public Supplier<Optional<O>> get() {
        return () -> {
            try {
                return Optional.of(checkedSupplier.get());
            } catch (Exception e) {
                es.add((E) e);
                return Optional.empty();
            }
        };
    }

    public List<E> getExceptions() {
        return es;
    }
}
