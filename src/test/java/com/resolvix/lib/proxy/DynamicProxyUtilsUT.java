package com.resolvix.lib.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertThat;

public class DynamicProxyUtilsUT {

    private interface DynamicInterfaceA {

        String getA();
    }

    private interface DynamicInterfaceB {

        String getB();
    }

    private static class LocalInvocationHandler
        implements InvocationHandler
    {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return "[LocalInvocationHandler for " + proxy.getClass().getName() + " for method " + method.getName() + "]";
        }
    }

    //
    //  createProxy
    //

    @Test
    public void createProxy() {
        InvocationHandler invocationHandler = new LocalInvocationHandler();
        Object object = DynamicProxyUtils.createProxy(
            invocationHandler, DynamicInterfaceA.class, DynamicInterfaceB.class);
        assertThat(
            ((DynamicInterfaceA) object).getA(),
            matchesPattern("\\[LocalInvocationHandler for com.resolvix.lib.proxy.\\$Proxy[0-9]+ for method getA\\]"));
        assertThat(
            ((DynamicInterfaceB) object).getB(),
            matchesPattern("\\[LocalInvocationHandler for com.resolvix.lib.proxy.\\$Proxy[0-9]+ for method getB\\]"));
    }
}
