package com.resolvix.lib.util.function;

@FunctionalInterface
public interface CheckedContextBiFunction<C, I, J, O, E extends Exception> {

    O apply(C context, I i, J j) throws E;
}
