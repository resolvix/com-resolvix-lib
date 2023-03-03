package com.resolvix.lib.util.function;

@FunctionalInterface
public interface CheckedContextFunction<C, I, O, E extends Exception> {

    O apply(C context, I i) throws E;
}
