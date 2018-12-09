package com.resolvix.lib.collector;

import com.resolvix.lib.collector.impl.base.BaseCartesianProductMappingTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

@Ignore
@Deprecated
public class CartesianProductMappingListAccumulatorTest
    extends BaseCartesianProductMappingTest {

    @Before
    public void before() {

    }

    @Test
    public void acceptanceTest() {
        CartesianProductMappingListAccumulator<
                CartesianProduct<String, String>,
                OneToMany<String, String>,
                String> accumulator = new CartesianProductMappingListAccumulator<>(
                    CartesianProductMappingListAccumulatorTest::toOneToMany,
                    CartesianProductMappingListAccumulatorTest::toPartialOneToMany,
                    CartesianProduct::getL,
                    OneToMany::fold,
                    OneToMany::foldPartial);

        cartesianProducts.stream()
                .forEach((CartesianProduct<String, String> cartesianProduct) ->
                            accumulator.transformAndAppend(cartesianProduct));

        List<OneToMany<String, String>> oneToManies = accumulator.toList();

        assertThat(oneToManies, hasItems(
                oneToManyMatcher("a", "b", "c", "d", "e", "f"),
                oneToManyMatcher("b", "g", "h", "i", "j"),
                oneToManyMatcher("c", "k", "l", "m")
        ));
    }
}
