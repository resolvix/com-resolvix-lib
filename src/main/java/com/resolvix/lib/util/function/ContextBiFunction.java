package com.resolvix.lib.util.function;

@FunctionalInterface
public interface ContextBiFunction<C, I, J, O> {

    O apply(C context, I i, J j);
}
