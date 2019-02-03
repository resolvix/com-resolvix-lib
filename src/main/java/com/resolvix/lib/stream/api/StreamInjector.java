package com.resolvix.lib.stream.api;

import java.util.stream.Stream;

public interface StreamInjector<T, R>
    extends Injector<T, Stream<T>, R> {

}
