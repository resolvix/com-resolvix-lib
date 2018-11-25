package com.resolvix.lib.collector;

import com.resolvix.lib.collector.base.BaseCartesianProductMappingTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class CartesianProductMappingTest
    extends BaseCartesianProductMappingTest
{

    @Before
    public void before() {

    }

    /*
    @Ignore @Test
    public void acceptanceTest() {
        /*CartesianProductMappingListAccumulator<
            BaseCartesianProductMappingTest.CartesianProduct<String, String>,
            BaseCartesianProductMappingTest.OneToMany<String, String>,
            String> accumulator = new CartesianProductMappingListAccumulator<>(
            CartesianProductMappingListAccumulatorTest::toOneToMany,
            CartesianProductMappingListAccumulatorTest::toPartialOneToMany,
            BaseCartesianProductMappingTest.CartesianProduct::getL,
            BaseCartesianProductMappingTest.OneToMany::fold,
            BaseCartesianProductMappingTest.OneToMany::foldPartial);*

        CartesianProductMapping<
            Function<T, U> fullMapper,
            Function<T, V> partialMapper,
            Function<T, K> classifier,
            BiFunction<U, V, W> fold,
            Collector<T, A, D> downstream>

        cartesianProducts.stream()
            .forEach(
                (BaseCartesianProductMappingTest.CartesianProduct<String, String> cartesianProduct) ->
                    cartesianProductMapping(cartesianProduct)
            );

        cartesianProducts.stream()
            .forEach((BaseCartesianProductMappingTest.CartesianProduct<String, String> cartesianProduct) ->
                accumulator.transformAndAppend(cartesianProduct));

        List<BaseCartesianProductMappingTest.OneToMany<String, String>> oneToManies = accumulator.toList();

        assertThat(oneToManies, hasItems(
            oneToManyMatcher("a", "b", "c", "d", "e", "f"),
            oneToManyMatcher("b", "g", "h", "i", "j"),
            oneToManyMatcher("c", "k", "l", "m")
        ));
    }
    */
}
