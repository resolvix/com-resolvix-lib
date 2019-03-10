package com.resolvix.lib.stream;

import com.resolvix.lib.stream.api.Injector;
import com.resolvix.lib.stream.impl.StreamInjectorImpl;
import com.resolvix.lib.stream.impl.base.BaseMappingTest;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class InjectorsTest
    extends BaseMappingTest {

    @Before
    public void before() {
        //
    }

    public static class Acc
        implements BinaryOperator<X> {

        @Override
        public X apply(X x, X x2) {
            return null;
        }

        @Override
        public <V> BiFunction<X, X, V> andThen(Function<? super X, ? extends V> after) {
            return null;
        }
    }

    @Ignore
    @Test
    public void injectIntoSingleCollector() {
    }

    @Test
    public void injectIntoMultipleCollectors() {

        AtomicReference<List<X>> refListX = new AtomicReference<>();

        AtomicReference<Set<X>> refSetX = new AtomicReference<>();

        Arrays.stream(xs)
            .collect(
                Injectors.of(
                    Collection.class,
                    Injectors.<X, List<X>>of(Collectors.toList(), refListX::set),
                    Injectors.<X, Set<X>>of(Collectors.toSet(), refSetX::set)));

        assertThat(refListX.get(),
            contains(a, b, c, d, e));

        assertThat(refSetX.get(),
            containsInAnyOrder(a, b, c, d, e));
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
            hasEntry("a", new String[]{"a", "b", "c", "d"}),
            hasEntry("b", new String[]{"e", "f", "g", "h"}),
            hasEntry("c", new String[]{"i", "j", "k", "l"}),
            hasEntry("d", new String[]{"m", "n", "o", "p"}),
            hasEntry("e", new String[]{"q", "r", "s", "t"})));
    }

    @Test
    public void injectIntoMultipleCollections() {

        Collection<X> collectionX1 = new ArrayList<>();

        Collection<X> collectionX2 = new LinkedList<>();

        Set<X> setX = new HashSet<>();

        Arrays.stream(xs)
            .collect(
                Injectors.of(
                    Collection.class,
                    Injectors.of(collectionX1),
                    Injectors.of(collectionX2),
                    Injectors.of(setX)));

        assertThat(collectionX1, contains(a, b, c, d, e));

        assertThat(collectionX2, contains(a, b, c, d, e));

        assertThat(setX, containsInAnyOrder(a, b, c, d, e));
    }

    @Test
    public void injectAsStream() {

        Function<Stream<X>, List<X>> streamProcessor = (Stream<X> streamX) -> {
            return streamX
                    .filter((X xx) -> true)
                    .collect(Collectors.toList());
        };

        AtomicReference<List<X>> refListX = new AtomicReference<>();

        Arrays.stream(xs)
                .collect(
                        Injectors.of(
                                streamProcessor,
                                refListX::set));

        assertThat(refListX.get(), contains(a, b, c, d, e));
    }
}
