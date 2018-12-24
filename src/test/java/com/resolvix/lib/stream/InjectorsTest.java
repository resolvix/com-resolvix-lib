package com.resolvix.lib.stream;

import com.resolvix.lib.stream.impl.base.BaseMappingTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;

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

    @Test
    public void preferredMappingCollectorStyle() {

        Collector<X, ?, List<X>> collectorA;

        Collector<X, ?, Set<X>> collectorB;

        Arrays.stream(xs)
                .collect(
                        Injectors.injector()
                )
                .collect(java.util.stream.Collectors.toList())
                .stream()
                .peek(injector());

        //    .p(collectorA, collectorB);


    }
}
