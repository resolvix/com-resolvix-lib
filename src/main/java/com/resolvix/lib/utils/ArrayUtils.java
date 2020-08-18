package com.resolvix.lib.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ArrayUtils {

    private ArrayUtils() {
        //
    }

    public static <T> boolean contains(T[] ts, T t) {
        assert ts != null && t != null;
        for (T tt : ts)
            if (t.equals(tt))
                return true;
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <T, U> T[] flatMap(U[] us, Class<T> classT, Function<U, T[]> functionUTS) {
        assert us != null;
        List<T> listT = new ArrayList<>();
        for (U u : us) {
            T[] ts = functionUTS.apply(u);
            if (ts != null)
                for (T t : ts)
                    listT.add(t);
        }
        return listT.toArray(
            (T[]) Array.newInstance(classT, listT.size()));
    }
}
