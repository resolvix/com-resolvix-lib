package com.resolvix.lib.dependency.api;

public class DependencyNotFoundException
    extends Exception
{
    private static final String DEPENDENCY_NOT_FOUND_TEMPLATE
        = "Dependency %s refernced by %s not found";

    public <K> DependencyNotFoundException(
        K k, K dk
    ) {
        super(String.format(DEPENDENCY_NOT_FOUND_TEMPLATE, dk, k));
    }
}
