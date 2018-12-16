package com.resolvix.lib.collector;

import com.resolvix.lib.collector.impl.base.BaseCartesianProductMappingTest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;

public class CartesianProductMappingTest
    extends BaseCartesianProductMappingTest
{

    @Before
    public void before() {

    }

    @Test
    public void cartesianProductMapping_success_no_downstream_collector() {
        List<OneToMany<String, String>> oneToManies = cartesianProducts.stream()
            .collect(
                CartesianProductMapping.cartesianProductMapping(
                    CartesianProduct::getL,
                    BaseCartesianProductMappingTest::toOneToMany,
                    BaseCartesianProductMappingTest::toPartialOneToMany,
                    OneToMany::foldPartial,
                    OneToMany::fold));

        assertThat(oneToManies, hasItems(
            oneToManyMatcher("a", "b", "c", "d", "e", "f"),
            oneToManyMatcher("b", "g", "h", "i", "j"),
            oneToManyMatcher("c", "k", "l", "m")
        ));
    }

    @Test
    public void cartesianProductMapping_success_with_downstream_collector() {
        Map<String, List<String>> oneToManies = cartesianProducts.stream()
            .collect(
                CartesianProductMapping.cartesianProductMapping(
                    CartesianProduct::getL,
                    BaseCartesianProductMappingTest::toOneToMany,
                    BaseCartesianProductMappingTest::toPartialOneToMany,
                    OneToMany::foldPartial,
                    OneToMany::fold,
                    Collectors.toMap(
                        OneToMany::getL,
                        OneToMany::getListR
                    )
                )
            );

        assertThat(oneToManies, allOf(
            hasEntry(equalTo("a"), contains("b", "c", "d", "e", "f")),
            hasEntry(equalTo("b"), contains("g", "h", "i", "j")),
            hasEntry(equalTo("c"), contains("k", "l", "m"))
        ));
    }
}
