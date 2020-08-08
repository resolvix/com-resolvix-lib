package com.resolvix.lib.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class DynamicProxyUtils {

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(
        InvocationHandler invocationHandler, Class<?>... interfaces) {
        return (T) Proxy.newProxyInstance(DynamicProxyUtils.class.getClassLoader(), interfaces, invocationHandler);
    }
}
