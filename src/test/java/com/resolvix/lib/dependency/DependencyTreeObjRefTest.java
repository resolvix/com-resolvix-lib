package com.resolvix.lib.dependency;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DependencyTreeObjRefTest
    extends DependencyResolver
{
    private static <K, T> ObjRef<K, T> toObjRef(K k, T t, K[] dependencies) {
        ObjRef<K, T> objRef = new ObjRef<>(k, t);
        objRef.addDependencies(dependencies, 0);
        return objRef;
    }

    private ObjRef<String, String> objRefA = toObjRef("A", "ALPHA", new String[] {""});

    private ObjRef<String, String> objRefB = toObjRef("B", "BRAVO", new String[] {""});

    private ObjRef<String, String> objRefC = toObjRef("C", "CHARLIE", new String[] {"A"});

    private ObjRef<String, String> objRefD = toObjRef("D", "DELTA", new String[] {"B", "C"});

    private ObjRef<String, String> objRefE = toObjRef("E", "ECHO", new String[] {"C", "D"});

    private ObjRef<String, String> objRefF = toObjRef("F", "FOXTROT", new String[] {"E", "G"});

    private ObjRef<String, String> objRefG = toObjRef("G", "GOLF", new String[] {"F", "E"});

    @Before
    public void before() {
        //
    }

    @Test
    public void ObjRef_compareTo_neither_dependent_upon_each() {

        assertThat(
            objRefA.compareTo(objRefB),
            equalTo(0));

        assertThat(
            objRefB.compareTo(objRefA),
            equalTo(0));

    }

    @Test
    public void ObjRef_compareTo_directly_dependent() {
        assertThat(
            objRefC.compareTo(objRefA),
            greaterThanOrEqualTo(1));

        assertThat(
            objRefD.compareTo(objRefC),
            greaterThanOrEqualTo(1));

        assertThat(
            objRefD.compareTo(objRefB),
            greaterThanOrEqualTo(1));

        assertThat(
            objRefE.compareTo(objRefD),
            greaterThanOrEqualTo(1));
    }

    @Test
    public void ObjRef_compareTo_is_depended_upon() {
        assertThat(
            objRefA.compareTo(objRefC),
            lessThanOrEqualTo(-1));

        assertThat(
            objRefB.compareTo(objRefD),
            lessThanOrEqualTo(-1));
    }

    @Ignore
    @Test
    public void ObjRef_compareTo_indirectly_dependent() {
        assertThat(
            objRefA.compareTo(objRefE),
            greaterThanOrEqualTo(1));

        assertThat(
            objRefB.compareTo(objRefE),
            greaterThanOrEqualTo(1));
    }

    @Test(expected = IllegalStateException.class)
    public void ObjRef_compareTo_codependent() {
        assertThat(
            objRefF.compareTo(objRefG),
            greaterThanOrEqualTo(0));
    }

    @Test(expected = IllegalStateException.class)
    public void ObjRef_compareTo_codependent_2() {
        assertThat(
            objRefG.compareTo(objRefF),
            greaterThanOrEqualTo(0));
    }
}
