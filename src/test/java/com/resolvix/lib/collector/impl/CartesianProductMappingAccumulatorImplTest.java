package com.resolvix.lib.collector.impl;

import com.resolvix.lib.collector.impl.base.BaseCartesianProductMappingTest;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class CartesianProductMappingAccumulatorImplTest
    extends BaseCartesianProductMappingTest {

    private CartesianProductMappingAccumulatorImpl<
                CartesianProduct<String, String>, String, OneToMany<String, String>,
                OneToMany<String, String>> mappingAccumulator
            = new CartesianProductMappingAccumulatorImpl<>(
                CartesianProduct::getL,
                BaseCartesianProductMappingTest::toOneToMany,
                BaseCartesianProductMappingTest::toPartialOneToMany,
                OneToMany::foldPartial,
                OneToMany::fold);

    @Before
    public void before() {
        //
    }

    @Test
    public void aNewMappingAccumulatorWillHaveNoElements() {
        Assert.assertThat(mappingAccumulator.toList(), Matchers.empty());
    }

    @Test
    public void aMappingAccumulatorThatHasConsumedOneSourceItemWillContainOneItem() {
        mappingAccumulator.accept(
            of("A", "B"));

        Assert.assertThat(
            mappingAccumulator.toList(),
            hasItem(
               Matchers.both(isA(OneToMany.class))
                    .and(hasProperty("l", equalTo("A")))
                    .and(hasProperty("listR",
                        hasItem(equalTo("B"))))));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void aMappingAccumulatorThatHasConsumedTwoSourceItemsWithSameClassifierWillContainOneItem() {
        mappingAccumulator.accept(
            of("A", "B"));
        mappingAccumulator.accept(
            of("A", "C"));

        Assert.assertThat(
            mappingAccumulator.toList(),
            hasItem(
                Matchers.both(isA(OneToMany.class))
                    .and(hasProperty("l", equalTo("A")))
                    .and(hasProperty("listR",
                        hasItems(
                            equalTo("B"),
                            equalTo("C"))))));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void aMappingAccumulatorThatHasConsumedTwoSourceItemsWithDifferentClassifiersWillContainTwoItems() {
        mappingAccumulator.accept(
            of("A", "B"));
        mappingAccumulator.accept(
            of("B", "C"));

        Assert.assertThat(
            mappingAccumulator.toList(),
            hasItems(
                both(isA(OneToMany.class))
                    .and(hasProperty("l", equalTo("A")))
                    .and(hasProperty("listR",
                        hasItem(
                            equalTo("B")))),
                both(isA(OneToMany.class))
                    .and(hasProperty("l", equalTo("B")))
                    .and(hasProperty( "listR",
                        hasItem(
                            equalTo("C"))))));
    }
}
