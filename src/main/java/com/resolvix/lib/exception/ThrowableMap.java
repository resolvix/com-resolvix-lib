package com.resolvix.lib.exception;

import com.resolvix.lib.exception.api.ThrowableMaplet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ThrowableMap {

    protected Map<Class<? extends Throwable>, Function<? extends Throwable, ?>> map;

    private ThrowableMap(
        Map<Class<? extends Throwable>, Function<? extends Throwable, ?>> map
    ) {
        this.map = map;
    }

    public static ThrowableMap of(
        List<ThrowableMaplet<? extends Throwable, ?>> serviceExceptionMaplets
    ) {
        Map<Class<? extends Throwable>, Function<? extends Throwable, ?>> map
            = new HashMap<>();
        serviceExceptionMaplets
            .forEach((ThrowableMaplet<? extends Throwable, ?> m) ->
                map.put(
                    m.getThrowableClass(),
                    m.getThrowableTransform()));
        return new ThrowableMap(map);
    }

    public <E extends Throwable, R> R map(E serviceException) {
        @SuppressWarnings("unchecked")
        Function<E, R> transformer
            = (Function<E, R>) map.get(serviceException.getClass());
        if (transformer == null)
            throw new IllegalStateException(serviceException);
        return transformer.apply(serviceException);
    }
}
