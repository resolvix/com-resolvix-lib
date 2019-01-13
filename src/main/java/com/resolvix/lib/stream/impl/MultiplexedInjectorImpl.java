package com.resolvix.lib.stream.impl;

import com.resolvix.lib.stream.api.Injector;
import com.resolvix.lib.stream.api.MultiplexedInjector;

import java.lang.reflect.Array;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @param <T>
 * @param <R>
 */
public class MultiplexedInjectorImpl<T, R>
    extends BaseInjectorImpl<T, R[]>
{

    /*public static class MultiplexedInjectorXX<T>
    {
        private int count;

        private Object[] objects;

        private BiConsumer<Object, T>[] accumulators;

        private BinaryOperator<Object>[] combiners;

        private Function<Object, ?>[] finishers;

        public MultiplexedInjector(
            Injector<T, ?>... injectors) {
            this.count = injectors.length;
            this.objects = map(injectors, Injector::supplier, Object.class);
            this.accumulators = (BiConsumer<Object, T>[]) map(injectors, Injector::accumulator, BiConsumer.class);
            this.combiners = (BinaryOperator<Object>[]) map(injectors, Injector::combiner, BinaryOperator.class);
            this.finishers = (Function<Object, ?>[]) map(injectors, Injector::finisher, Function.class);
        }




    }*/

    private Class<R> classR;

    private Injector<T, R>[] injectors;

    private int count;

    @SuppressWarnings("unchecked")
    private static <T, R, U> U[] map(Injector<T, R>[] injectors, Function<Injector<T, R>, U> getter, Class<U> classU) {
        int length = injectors.length;
        U[] us = (U[]) Array.newInstance(classU, length);
        for (int i = 0; i < length; i++) {
            us[i] = getter.apply(injectors[i]);
        }
        return us;
    }

    @Override
    protected R[] supply() {
        return map(injectors, (Injector<T, R> inj) -> inj.supplier().get(), classR);
    }

    @Override
    protected void accumulate(R[] rs, T t) {
        //
        //  Refinement opportunity/: this operation could probably be done in
        //  parallel.
        //
        for (int i = 0; i < count; i++)
            injectors[i].accumulator().accept(rs[i], t);
    }

    /*public R[] finish(R[] rs) {
        R[] finished = (R[]) Array.newInstance(classR, count);
        for (int i = 0; i < count; i++)
            finished[i] = injectors[i].finisher().apply(rs[i]);
        return finished;
    }*/

    public MultiplexedInjectorImpl(
        Class<R> classR,
        Injector<T, R>... injectors
    ) {
//        super(
//            (R[]) map(injectors, (Injector<T, R> inj) -> inj.supplier().get(), classR),
//            MultiplexedInjectorImpl::accumulate,
//            MultiplexedInjectorImpl::combine
//        );
        //super(MultiplexedInjectorImpl::accumulate, MultiplexedInjectorImpl::combine);
        super();
        this.classR = classR;
        this.injectors = injectors;
        this.count = injectors.length;
    }
}