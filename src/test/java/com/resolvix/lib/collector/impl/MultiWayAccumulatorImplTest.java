package com.resolvix.lib.collector.impl;

import com.resolvix.lib.collector.impl.base.BaseMappingMultiWayTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

public class MultiWayAccumulatorImplTest
    extends BaseMappingMultiWayTest
{

    @Before
    public void before() {
        //
    }

    @Ignore
    @Test
    public void createMultiWayAccumulatorWithMultipleCollectorSpecifications() {

        /*Arrays.stream(xs)
            .collect(
               MappingMultiWayAccumulatorImpl.getBuilder()
                   .map()
                   .collector()
                   .collector()
                   .build()
            );*/
    }
}
