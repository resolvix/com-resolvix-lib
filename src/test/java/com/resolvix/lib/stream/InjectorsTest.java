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

import static org.hamcrest.Matchers.*;
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
    public void injectIntoSingleCollector() {
    }

    @Ignore @Test
    public void injectIntoMultipleCollectors() {

        Collector<X, ?, List<X>> collectorListX = Collectors.toList();

        Collector<X, ?, Set<X>> collectorSetX = Collectors.toSet();

        Arrays.stream(xs)
                .collect(
                        Injectors.of(
                                Injectors.of(collectorListX),
                                Injectors.of(collectorSetX)));

//        assertThat(,
//            contains(a, b, c, d, e));
    }

    @Test
    public void injectIntoSingleCollection() {

        Collection<X> collectionX = new ArrayList<>();

        Arrays.stream(xs)
                .collect(
                        Injectors.of(collectionX));

        assertThat(collectionX, contains(a, b, c, d, e));
    }

    @Test
    public void injectIntoSingleSet() {

        Set<X> setX = new HashSet<>();

        Arrays.stream(xs)
                .collect(
                        Injectors.of(setX));

        assertThat(setX, containsInAnyOrder(a, b, c, d, e));
    }

    @Test
    public void injectKeyObjectPairIntoSingleMap() {

        Map<String, X> map = new HashMap<>();

        Arrays.stream(xs)
                .collect(
                    Injectors.of(map, X::getKey));

        assertThat(map, allOf(
            hasEntry("a", a),
            hasEntry("b", b),
            hasEntry("c", c),
            hasEntry("d", d),
            hasEntry("e", e)));
    }

    @Test
    public void injectKeyValuePairIntoSingleMap() {

        Map<String, String[]> map = new HashMap<>();

        Arrays.stream(xs)
            .collect(
                Injectors.of(map, X::getKey, X::getRefs));

        assertThat(map, allOf(
            hasEntry("a", new String[] { "a", "b", "c", "d" }),
            hasEntry("b", new String[] { "e", "f", "g", "h" }),
            hasEntry("c", new String[] { "i", "j", "k", "l" }),
            hasEntry("d", new String[] { "m", "n", "o", "p" }),
            hasEntry("e", new String[] { "q", "r", "s", "t" })));
    }

    @Test
    public void injectIntoMultipleCollections() {

        Collection<X> collectionX1 = new ArrayList<>();

        Collection<X> collectionX2 = new LinkedList<>();

        Set<X> setX = new HashSet<>();

        Arrays.stream(xs)
                .collect(
                        Injectors.of(
                                Injectors.of(collectionX1),
                                Injectors.of(collectionX2),
                                Injectors.of(setX)));

        assertThat(collectionX1, contains(a, b, c, d, e));

        assertThat(collectionX2, contains(a, b, c, d, e));

        assertThat(setX, containsInAnyOrder(a, b, c, d, e));
    }
}
