package com.resolvix.lib.stream;

import com.resolvix.lib.stream.api.Injector;
import com.resolvix.lib.stream.impl.base.BaseMappingTest;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class InjectorsTest
    extends BaseMappingTest
{

    @Before
    public void before() {
        //
    }

    public static class Acc
        implements BinaryOperator<X>
    {

        @Override
        public X apply(X x, X x2) {
            return null;
        }

        @Override
        public <V> BiFunction<X, X, V> andThen(Function<? super X, ? extends V> after) {
            return null;
        }
    }

    @Ignore @Test
    public void shouldInjectIntoSingleCollector() {
    }

    @Ignore @Test
    public void shouldInjectIntoMultipleCollectors() {

        Collector<X, ?, List<X>> collectorListX = Collectors.toList();

        Collector<X, ?, Set<X>> collectorSetX = Collectors.toSet();

        Arrays.stream(xs)
                .collect(
                        Injectors.of(
                                Injectors.of(collectorListX),
                                Injectors.of(collectorSetX)));

    }

    @Test
    public void shouldInjectIntoSingleCollection() {

        Collection<X> collectionX = new ArrayList<>();

        Arrays.stream(xs)
                .collect(
                        Injectors.of(collectionX));

        assertThat(collectionX, not(empty()));
    }

    @Test
    public void shouldInjectIntoSingleSet() {

        Set<X> setX = new HashSet<>();

        Arrays.stream(xs)
                .collect(
                        Injectors.of(setX));

        assertThat(setX, not(empty()));
    }

    @Ignore @Test
    public void shouldInjectIntoMultipleCollections() {

        Collection<X> collectionX1 = new ArrayList<>();

        Collection<X> collectionX2 = new LinkedList<>();

        Arrays.stream(xs)
                .collect(
                        Injectors.of(
                                Injectors.of(collectionX1),
                                Injectors.of(collectionX2)));
    }
}
