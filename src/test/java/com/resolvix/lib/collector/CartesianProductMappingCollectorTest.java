package com.resolvix.lib.collector;

import com.resolvix.lib.collector.base.BaseCartesianProductMappingTest;
import org.hamcrest.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class CartesianProductMappingCollectorTest
    extends BaseCartesianProductMappingTest {

    @Before
    public void before() {

    }

    @Ignore @Test
    public void acceptanceTest() {
        List<OneToMany<String, String>> oneToManies = cartesianProducts.stream()
            .collect(CartesianProductMappingCollector.asList(
                CartesianProductMappingCollectorTest::toOneToMany,
                CartesianProductMappingCollectorTest::toPartialOneToMany,
                OneToMany::fold,
                OneToMany::foldPartial,
                CartesianProduct::getL,
                CartesianProduct::getR
            ));

        //List<OneToMany<String, String>> oneToManies2 = cartesianProducts.stream()
        //        .collect()

        assertThat(oneToManies, hasItems(
            oneToManyMatcher("a", "b", "c", "d", "e", "f"),
            oneToManyMatcher("b", "g", "h", "i", "j"),
            oneToManyMatcher("c", "k", "l", "m")
        ));
    }
}
