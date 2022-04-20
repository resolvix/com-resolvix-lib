package com.resolvix.lib.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class MapUtils {

    private MapUtils() {
        //
    }

    public static <K, V extends Enum<V>> Map<K, V> toMap(
        Supplier<Map<K, V>> mapSupplier, V[] vs, Function<? super V, K> keyFunction) {
        Map<K, V> m = mapSupplier.get();
        for (V v : vs)
            m.put(keyFunction.apply(v), v);
        return m;
    }

    public static <K, V extends Enum<V>> Map<K, V> toMap(
        V[] vs, Function<? super V, K> keyFunction) {
        return toMap(HashMap::new, vs, keyFunction);
    }
}
