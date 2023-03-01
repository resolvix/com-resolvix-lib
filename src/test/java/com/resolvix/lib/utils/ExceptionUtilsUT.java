package com.resolvix.lib.utils;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static com.resolvix.lib.utils.ExceptionUtils.toRuntimeException;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

public class ExceptionUtilsUT {

    @Test
    public void toRuntimeExceptionWhenCheckedExceptionIsUsed() {
        Exception exception = new Exception();
        assertThat(
            toRuntimeException(exception),
            allOf(
                isA(RuntimeException.class),
                hasProperty("cause", sameInstance(exception))));
    }

    @Test
    public void toRuntimeExceptionWhenRuntimeExceptionIsUsed() {
        RuntimeException runtimeException = new RuntimeException();
        assertThat(
            toRuntimeException(runtimeException),
            sameInstance(runtimeException));
    }
}
