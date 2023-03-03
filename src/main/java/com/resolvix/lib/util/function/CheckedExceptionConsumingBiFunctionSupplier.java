package com.resolvix.lib.util.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class CheckedExceptionConsumingBiFunctionSupplier<I, J, O, E extends Exception, F extends CheckedBiFunction<I, J, O, E>>
    implements Supplier<BiFunction<I, J, Optional<O>>>
{

    private List<E> es;

    private F checkedBiFunction;

    public CheckedExceptionConsumingBiFunctionSupplier(F checkedBiFunction) {
        super();
        this.es = new ArrayList<>();
        this.checkedBiFunction = checkedBiFunction;
    }

    @Override
    public BiFunction<I, J, Optional<O>> get() {
        return (I i, J j) -> {
            try {
                return Optional.of(checkedBiFunction.apply(i, j));
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
