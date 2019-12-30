package com.resolvix.lib.reflect.exception;

public class ClassRetrievalException
    extends RuntimeException
{

    public ClassRetrievalException() {
        super();
    }

    public ClassRetrievalException(String s) {
        super(s);
    }

    public ClassRetrievalException(String s, Throwable cause) {
        super(s, cause);
    }

    public ClassRetrievalException(Throwable cause) {
        super(cause);
    }
}
