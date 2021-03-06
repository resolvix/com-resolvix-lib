package com.resolvix.lib.stream;

import com.resolvix.lib.stream.impl.base.BaseCartesianProductMappingTest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;

public class CollectorsCartesianProductMappingTest
    extends BaseCartesianProductMappingTest
{

    @Before
    public void before() {
        //
    }

    @Test
    public void cartesianProductMappingNoDownstreamCollector() {
        List<OneToMany<String, String>> oneToManies = cartesianProducts.stream()
            .collect(
                Collectors.cartesianProductMapping(
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
    public void cartesianProductMappingWithDownstreamCollector() {
        Map<String, List<String>> oneToManies = cartesianProducts.stream()
            .collect(
                Collectors.cartesianProductMapping(
                    CartesianProduct::getL,
                    BaseCartesianProductMappingTest::toOneToMany,
                    BaseCartesianProductMappingTest::toPartialOneToMany,
                    OneToMany::foldPartial,
                    OneToMany::fold,
                    java.util.stream.Collectors.toMap(
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
