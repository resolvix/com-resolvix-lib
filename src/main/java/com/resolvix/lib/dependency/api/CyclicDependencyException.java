package com.resolvix.lib.dependency.api;

public class CyclicDependencyException
    extends Exception
{
    private static final String CYCLIC_DEPENDENCY_EXISTS_TEMPLATE
        = "A cyclic dependency exists between %s and %s";

    public <K> CyclicDependencyException(
        K k, K dk
    ) {
        super(String.format(CYCLIC_DEPENDENCY_EXISTS_TEMPLATE, k, dk));
    }
}
