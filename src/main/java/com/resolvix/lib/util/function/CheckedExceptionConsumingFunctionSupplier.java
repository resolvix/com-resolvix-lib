package com.resolvix.lib.util.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class CheckedExceptionConsumingFunctionSupplier<I, O, E extends Exception>
    implements Supplier<Function<I, Optional<O>>>
{

    private List<E> es;

    private CheckedFunction<I, O, E> checkedFunction;

    public CheckedExceptionConsumingFunctionSupplier(
        CheckedFunction<I, O, E> checkedFunction)
    {
        super();
        this.es = new ArrayList<>();
        this.checkedFunction = checkedFunction;
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
