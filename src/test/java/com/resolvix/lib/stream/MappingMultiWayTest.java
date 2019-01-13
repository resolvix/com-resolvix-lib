package com.resolvix.lib.stream;

import com.resolvix.lib.stream.impl.base.BaseMappingTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@Ignore
@Deprecated
public class MappingMultiWayTest
    extends BaseMappingTest
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

    @Test
    public void alternativeApproachToMultiWayCollecting() {


        /*Arrays.stream(xs)
                .
        */
    }
}
