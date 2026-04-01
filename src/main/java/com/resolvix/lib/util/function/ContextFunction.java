package com.resolvix.lib.util.function;

@FunctionalInterface
public interface ContextFunction<C, I, O> {

    O apply(C context, I i);
}
