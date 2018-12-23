package com.resolvix.lib.collector;

import com.resolvix.lib.collector.impl.MappingMultiWayAccumulatorImpl;
import com.resolvix.lib.collector.impl.base.BaseMappingMultiWayTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MappingMultiWayTest
    extends BaseMappingMultiWayTest
{

    @Before
    public void before() {
        //
    }

    @Test
    public void createMultiWayAccumulatorWithMultipleCollectorSpecifications() {

        Function<X, String> toStr = X::getKey;

        Arrays.stream(xs)
            .collect(
                MappingMultiWay.getBuilder(X.class)
                    .mapperCollector(toStr, Collectors.toList())
                    .mapperCollector(X::getRefs, Collectors.toList())
                    .mapperCollector(Function.identity(), Collectors.toList())
                    .build()
            );
    }
}
