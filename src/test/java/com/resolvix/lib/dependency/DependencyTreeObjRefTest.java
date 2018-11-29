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
    private ObjRef<String, String> objRefA = new ObjRef<String, String>("A", new String[] {""}, "ALPHA");

    private ObjRef<String, String> objRefB = new ObjRef<String, String>("B", new String[] {""}, "BRAVO");

    private ObjRef<String, String> objRefC = new ObjRef<String, String>("C", new String[] {"A"}, "CHARLIE");

    private ObjRef<String, String> objRefD = new ObjRef<String, String>("D", new String[] {"B", "C"}, "DELTA");

    private ObjRef<String, String> objRefE = new ObjRef<String, String>("E", new String[] {"C", "D"}, "ECHO");

    private ObjRef<String, String> objRefF = new ObjRef<String, String>("F", new String[] {"E", "G"}, "FOXTROT");

    private ObjRef<String, String> objRefG = new ObjRef<String, String>("G", new String[] {"F", "E"}, "GOLF");

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
