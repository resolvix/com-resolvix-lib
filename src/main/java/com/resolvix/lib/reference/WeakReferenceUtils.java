package com.resolvix.lib.reference;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WeakReferenceUtils {

    private WeakReferenceUtils() {
        //
    }

    public static <T> List<WeakReference<T>> compact(Iterable<WeakReference<T>> iterable) {
        List<WeakReference<T>> ls = new ArrayList<>();
        for (WeakReference<T> weakReference : iterable) {
            T t = weakReference.get();
            if (t != null)
                ls.add(weakReference);
        }
        return ls;
    }

    public static <T> WeakReference<T> find(Iterable<WeakReference<T>> iterable, T t) {
        for (WeakReference<T> weakReference : iterable) {
            T tt = weakReference.get();
            if (t.equals(tt))
                return weakReference;
        }
        return null;
    }

    public static <T> void foreach(Iterable<WeakReference<T>> iterable, Consumer<T> consumerT) {
        for (WeakReference<T> weakReference : iterable) {
            T t = weakReference.get();
            if (t != null)
                consumerT.accept(t);
        }
    }
}
