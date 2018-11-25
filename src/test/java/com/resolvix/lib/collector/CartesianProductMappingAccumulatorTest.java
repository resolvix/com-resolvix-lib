package com.resolvix.lib.collector;

import com.resolvix.lib.collector.base.BaseCartesianProductMappingTest;
import junit.framework.TestCase;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class CartesianProductMappingAccumulatorTest
    extends BaseCartesianProductMappingTest {

    private CartesianProductMappingAccumulator<
        CartesianProduct<String, String>, String, OneToMany<String, String>,
        OneToMany<String, String>> mappingAccumulator
        = new CartesianProductMappingAccumulator<>(
        CartesianProduct::getL,
        BaseCartesianProductMappingTest::toOneToMany,
        BaseCartesianProductMappingTest::toPartialOneToMany,
        OneToMany::foldPartial,
        OneToMany::fold);


    @Before
    public void before() {

    }

    @Test
    public void anNewMappingAccumulatorWillHaveNoElements() {
        Assert.assertThat(mappingAccumulator.toList(), Matchers.empty());
    }

    @Test
    public void aMappingAccumulatorThatHasConsumedOneSourceItemWillContainOneItem() {
        mappingAccumulator.accumulate(
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
    public void aMappingAccumulatorThatHasConsumedTwoSourceItemsWithSameClassifierWillContainOneItem() {
        mappingAccumulator.accumulate(
            of("A", "B"));
        mappingAccumulator.accumulate(
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
    public void aMappingAccumulatorThatHasConsumedTwoSourceItemsWithDifferentClassifiersWillContainTwoItems() {
        mappingAccumulator.accumulate(
            of("A", "B"));
        mappingAccumulator.accumulate(
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
