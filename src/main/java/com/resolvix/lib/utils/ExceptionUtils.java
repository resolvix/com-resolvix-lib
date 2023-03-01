package com.resolvix.lib.utils;

public class ExceptionUtils {

    private ExceptionUtils() {
        //
    }

    /**
     * Wraps an exception within a {@link RuntimeException} object if
     * required to enable a checked exception to be consumed in a context
     * where checked exceptions may not be supported such as the
     * {@link java.util.stream.Stream#map} method.
     *
     * @param exception the checked or unchecked exception
     * @return the exception wrapped in a {@link RuntimeException} object
     *  if it is a checked exception; the exception if it is an unchecked
     *  exception
     * @param <E> the exception type
     */
    public static <E extends Exception> RuntimeException toRuntimeException(E exception) {
        if (exception instanceof RuntimeException)
            return (RuntimeException) exception;

        return new RuntimeException(exception);
    }
}
