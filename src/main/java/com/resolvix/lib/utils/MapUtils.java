package com.resolvix.lib.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MapUtils {

    private MapUtils() {
        //
    }

    public static <K, V extends Enum<V>> Map<K, V> toMap(V[] vs, Function<? super V, K> keyFunction) {
        Map<K, V> m = new HashMap<>();
        for (V v : vs)
            m.put(keyFunction.apply(v), v);
        return m;
    }
}
