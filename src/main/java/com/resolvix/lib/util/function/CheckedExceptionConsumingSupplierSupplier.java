package com.resolvix.lib.util.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class CheckedExceptionConsumingSupplierSupplier<O, E extends Exception, S extends CheckedSupplier<O, E>>
    implements Supplier<Supplier<Optional<O>>>
{

    private List<E> es;

    private S checkedSupplier;

    public CheckedExceptionConsumingSupplierSupplier(S checkedSupplier) {
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
