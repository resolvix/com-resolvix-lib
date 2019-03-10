package com.resolvix.lib.stream.impl;

import com.resolvix.lib.stream.api.StreamInjector;

abstract class BaseStreamInjectorImpl<T, U, R>
    implements StreamInjector<T, BaseStreamInjectorImpl.BaseStreamBuffer<U>, R>
{
    protected static class BaseStreamBuffer<X> { }


}
