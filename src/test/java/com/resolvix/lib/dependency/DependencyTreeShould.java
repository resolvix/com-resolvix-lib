package com.resolvix.lib.dependency;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.resolvix.lib.dependency.DependencyResolver.resolveDependencies;
import static com.resolvix.lib.dependency.DependencyResolver.traceDependencies;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class DependencyTreeShould {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    private SampleObject objectA = toObjDep("A", "F", "E");
    private SampleObject objectB = toObjDep("B", "A");
    private SampleObject objectC = toObjDep( "C", "D", "B");
    private SampleObject objectD = toObjDep("D");
    private SampleObject objectE = toObjDep("E", "F");
    private SampleObject objectF = toObjDep("F", "D");

    private SampleObject objectG = toObjDep("G", "H");
    private SampleObject objectH = toObjDep("H", "F", "G");

    @Before
    public void before() {

    }

    @Test
    public void resolveDependencies_should_correctly_resolve_well_formed_dependencies() {

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
    public void resolveDependencies_cannot_resolve_interdependencies() {
        thrown.expect(IllegalStateException.class);
        SampleObject[] dependencies = resolveDependencies(
            SampleObject.class,
            SampleObject::getName,
            SampleObject::getDependencies,
            objectG, objectH, objectF, objectD);
    }

    @Test
    public void resolveDependencies_cannot_resolve_unprovided_dependencies() {
        thrown.expect(IllegalStateException.class);
        SampleObject[] dependencies = resolveDependencies(
            SampleObject.class,
            SampleObject::getName,
            SampleObject::getDependencies,
            objectG, objectH, objectD);
    }
}
