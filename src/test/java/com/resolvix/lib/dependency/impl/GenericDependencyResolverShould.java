package com.resolvix.lib.dependency.impl;

import com.resolvix.lib.dependency.api.CyclicDependencyException;
import com.resolvix.lib.dependency.api.DependencyNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.resolvix.lib.dependency.impl.GenericDependencyResolver.resolveDependencies;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class GenericDependencyResolverShould {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SampleObject objectA = toObjDep("A", "F", "E");
    private SampleObject objectB = toObjDep("B", "A");
    private SampleObject objectC = toObjDep( "C", "D", "B");
    private SampleObject objectD = toObjDep("D");
    private SampleObject objectE = toObjDep("E", "F");
    private SampleObject objectF = toObjDep("F", "D");

    private SampleObject objectG = toObjDep("G", "H");
    private SampleObject objectH = toObjDep("H", "F", "G");

    public static class SampleObject {

        private String name;

        private String[] dependencies;

        public SampleObject(String name, String[] dependencies) {
            this.name = name;
            this.dependencies = dependencies;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String[] getDependencies() {
            return dependencies;
        }

        public void setDependencies(String[] dependencies) {
            this.dependencies = dependencies;
        }
    }

    private static SampleObject toObjDep(String name, String... dependencies) {
        return new SampleObject(name, dependencies);
    }

    @Before
    public void before() {
        //
    }

    @Test
    @SuppressWarnings("unchecked")
    public void resolveDependenciesShouldCorrectlyResolveWellFormedDependencies()
        throws CyclicDependencyException, DependencyNotFoundException
    {
        SampleObject[] dependencies = resolveDependencies(
            SampleObject.class,
            SampleObject::getName,
            SampleObject::getDependencies,
            objectA, objectB, objectC, objectD, objectE, objectF);

        assertThat(dependencies, arrayContaining(
            sameInstance(objectD), sameInstance(objectF), sameInstance(objectE),
            sameInstance(objectA), sameInstance(objectB), sameInstance(objectC)));
    }

    @Test
    public void resolveDependenciesCannotResolveInterdependencies()
        throws CyclicDependencyException, DependencyNotFoundException
    {
        thrown.expect(CyclicDependencyException.class);
        SampleObject[] dependencies = resolveDependencies(
            SampleObject.class,
            SampleObject::getName,
            SampleObject::getDependencies,
            objectG, objectH, objectF, objectD);
    }

    @Test
    public void resolveDependenciesCannotResolveUnprovidedDependencies()
        throws CyclicDependencyException, DependencyNotFoundException
    {
        thrown.expect(DependencyNotFoundException.class);
        SampleObject[] dependencies = resolveDependencies(
            SampleObject.class,
            SampleObject::getName,
            SampleObject::getDependencies,
            objectG, objectH, objectD);
    }
}
