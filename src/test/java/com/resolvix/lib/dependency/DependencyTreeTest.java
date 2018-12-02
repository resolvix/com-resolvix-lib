package com.resolvix.lib.dependency;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.resolvix.lib.dependency.DependencyResolver.calculateDependencies;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class DependencyTreeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public static class ObjectDependency {

        private String name;

        private String[] dependencies;

        public ObjectDependency(String name, String[] dependencies) {
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

    private static ObjectDependency toObjDep(String name, String... dependencies) {
        return new ObjectDependency(name, dependencies);
    }

    private ObjectDependency objectA = toObjDep("A", "F", "E");
    private ObjectDependency objectB = toObjDep("B", "A");
    private ObjectDependency objectC = toObjDep( "C", "D", "B");
    private ObjectDependency objectD = toObjDep("D");
    private ObjectDependency objectE = toObjDep("E", "F");
    private ObjectDependency objectF = toObjDep("F", "D");

    @Before
    public void before() {

    }

    @Test
    public void acceptanceTest() {

        ObjectDependency[] dependencies = calculateDependencies(
                ObjectDependency.class,
                ObjectDependency::getName,
                ObjectDependency::getDependencies,
                objectA, objectB, objectC, objectD, objectE, objectF);

        assertThat(dependencies, arrayContaining(
                        sameInstance(objectD), sameInstance(objectF), sameInstance(objectE),
                        sameInstance(objectA), sameInstance(objectB), sameInstance(objectC)));
    }
}
