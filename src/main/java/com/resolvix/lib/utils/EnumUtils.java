package com.resolvix.lib.utils;

public class EnumUtils {

    public EnumUtils() {
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
}
