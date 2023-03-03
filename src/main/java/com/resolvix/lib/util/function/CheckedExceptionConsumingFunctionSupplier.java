package com.resolvix.lib.util.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class CheckedExceptionConsumingFunctionSupplier<I, O, E extends Exception, F extends CheckedFunction<I, O, E>>
    implements Supplier<Function<I, Optional<O>>>
{

    private List<E> es;

    private F checkedFunction;

    public CheckedExceptionConsumingFunctionSupplier(F checkedFunction) {
        super();
        this.es = new ArrayList<>();
        this.checkedFunction = this.checkedFunction;
    }

    @Override
    public Function<I, Optional<O>> get() {
        return (I i) -> {
            try {
                return Optional.of(checkedFunction.apply(i));
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
