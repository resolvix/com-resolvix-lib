package com.resolvix.lib.map;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class MapBuilder<M extends Map<K, V>, K, V> {

    private M m;

    public static <N extends Map<L, W>, L, W> MapBuilder<N, L, W> getBuilder(Class<N> classN, Class<L> classL, Class<W> classW) {
        try {
            N sampleN = classN.newInstance();
            Constructor<N> constructorN = classN.getConstructor(Map.class);
            N n = constructorN.newInstance(sampleN);
            return new MapBuilder<>(n);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <L, W> MapBuilder<HashMap<L, W>, L, W> getBuilder(Class<L> classL, Class<W> classW) {
        HashMap<L, W> m = new HashMap<>();
        return new MapBuilder(m);
    }

    private MapBuilder(M m) {
        this.m = m;
    }

    protected MapBuilder<M, K, V> self() {
        return this;
    }

    public MapBuilder<M, K, V> put(K k, V v) {
        m.put(k, v);
        return self();
    }

    public M build() {
        return m;
    }
}
