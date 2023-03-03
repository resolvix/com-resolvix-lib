package com.resolvix.lib.util.function;

/**
 * Represents the supplier of results in a given processing context.
 *
 * To comply with the intended purpose for this interface an
 * implementation, the context provided must not have any influence
 * on the value supplied by the supplier; it is provided as an
 * argument solely to enable the supplier to perform context-aware
 * operations such as logging, throwing fully qualified exceptions.
 *
 * @param <C> the type of processing context
 * @param <O> the type of results supplied
 */
@FunctionalInterface
public interface ContextSupplier<C, O> {

    /**
     * Gets a result in a given processing context.
     *
     * @param context the processing context
     * @return a result
     */
    O get(C context);
}