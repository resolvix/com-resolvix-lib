package com.resolvix.lib.utils;

import java.util.function.Function;

public class EnumUtils {

    private EnumUtils() {
        //
    }

    public static <E extends Enum<E>> String safeName(E e) {
        if (e == null)
            return null;
        return e.name();
    }

    public static <E extends Enum<E>> Integer safeOrdinal(E e) {
        if (e == null)
            return null;
        return e.ordinal();
    }

    public static <E extends Enum<E>, R> R safeMap(E e, Function<E, R> map) {
        if (e == null)
            return null;
        return map.apply(e);
    }
}
