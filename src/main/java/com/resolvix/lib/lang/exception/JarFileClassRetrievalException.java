package com.resolvix.lib.lang.exception;

public class JarFileClassRetrievalException
    extends RuntimeException
{

    public JarFileClassRetrievalException() {
        super();
    }

    public JarFileClassRetrievalException(String s) {
        super(s);
    }

    public JarFileClassRetrievalException(String s, Throwable cause) {
        super(s, cause);
    }

    public JarFileClassRetrievalException(Throwable cause) {
        super(cause);
    }
}
