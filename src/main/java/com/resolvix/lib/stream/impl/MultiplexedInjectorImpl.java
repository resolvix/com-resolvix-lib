package com.resolvix.lib.stream.impl;

import com.resolvix.lib.stream.api.Injector;
import com.resolvix.lib.stream.api.MultiplexedInjector;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.*;

/**
 *
 * @param <T>
 * @param <R>
 */
public class MultiplexedInjectorImpl<T, U, R>
    extends BaseInjectorImpl<T, U[], R[]>
    implements MultiplexedInjector<T, U, R>
{
    private final Class<R> classR;

    //private final Class<U> classU;

    private final Injector<T, U, R>[] injectors;

    private final BiConsumer<U, T>[] accumulators;

    @SuppressWarnings("unchecked")
    private static <T, U, R> U[] map(
            Injector<T, ?, R>[] injectors,
            BiFunction<Injector<T, ?, R>, Class<U>, U> getter,
            Class<U> classU) {
        int length = injectors.length;
        U[] us = (U[]) Array.newInstance(classU, length);
        for (int i = 0; i < length; i++) {
            us[i] = classU.cast(getter.apply(injectors[i], classU));
        }
        return us;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected U[] supply() {
        return (U[]) Arrays.stream(injectors)
                .map((Injector<T, ?, ? extends R> injector) -> (U) injector.supplier().get())
                .toArray();
    }

    @Override
    protected void accumulate(U[] us, T t) {
        //
        //  Refinement opportunity/: this operation could probably be done in
        //  parallel.
        //
        int length = injectors.length;
        for (int i = 0; i < length; i++)
            accumulators[i].accept(us[i], t);
    }

    @Override
    protected R[] finish(U[] us) {
        int length = us.length;
      //  @SuppressWarnings("unchecked")
        R[] rs = (R[]) Array.newInstance(classR, length);
        for (int i = 0; i < length; i++) {
          //  @SuppressWarnings("unchecked")
            Function<U, R> finisher = (Function<U, R>) injectors[i].finisher();
            rs[i] = finisher.apply(us[i]);
        }
        return rs;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public <RR extends R> MultiplexedInjectorImpl(
            Class<R> classR,
         //   Class<U> classU,
            Injector<T, ?, ? extends R>... injectors
    ) {
        super();
        this.classR = classR;
      //  this.classU = classU;

        int length = injectors.length;

        //
        //  Initialise the injector accumulator lookup table
        //
        this.injectors = (Injector<T, U, R>[]) Array.newInstance(Injector.class, length);
        this.accumulators = (BiConsumer<U, T>[]) Array.newInstance(BiConsumer.class, length);
        for (int i = 0; i < length; i++) {
            this.injectors[i] = (Injector<T, U, R>) injectors[i];
            this.accumulators[i] = this.injectors[i].accumulator();
        }
    }
}