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
 * @param <E> the type of checked or unchecked exceptions thrown
 */
@FunctionalInterface
public interface CheckedContextSupplier<C, O, E extends Exception> {

    /**
     * Gets a result in a given processing context.
     *
     * @param context the processing context
     * @return a result
     * @throws E an exception
     */
    O get(C context) throws E;
}