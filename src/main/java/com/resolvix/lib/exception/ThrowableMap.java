package com.resolvix.lib.exception;

import com.resolvix.lib.exception.api.ThrowableMaplet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ThrowableMap {

    protected Map<Class<? extends Throwable>, Function<? extends Throwable, String>> map;

    private ThrowableMap(
        Map<Class<? extends Throwable>, Function<? extends Throwable, String>> map
    ) {
        this.map = map;
    }

    public static ThrowableMap of(
        List<ThrowableMaplet<?>> serviceExceptionMaplets
    ) {
        Map<Class<? extends Throwable>, Function<? extends Throwable, String>> map
            = new HashMap<>();
        serviceExceptionMaplets
            .forEach((ThrowableMaplet<?> m) ->
                map.put(
                    m.getThrowableClass(),
                    m.getThrowableTransform()));
        return new ThrowableMap(map);
    }

    public <E extends Throwable> String map(E serviceException) {
        @SuppressWarnings("unchecked")
        Function<E, String> transformer
            = (Function<E, String>) map.get(serviceException.getClass());
        if (transformer == null)
            throw new IllegalStateException(serviceException);
        return transformer.apply(serviceException);
    }
}
