package com.resolvix.lib.stream.impl;

import com.resolvix.lib.stream.impl.base.BaseMappingTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Deprecated
public class MultiWayAccumulatorImplTest
    extends BaseMappingTest
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
