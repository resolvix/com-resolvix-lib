package com.resolvix.lib.dependency.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ObjectReferenceShould
{
    private ObjectReference<String, String> objRefA
        = toObjectReference("A", "ALPHA", new String[] {""});

    private ObjectReference<String, String> objRefB
        = toObjectReference("B", "BRAVO", new String[] {""});

    private ObjectReference<String, String> objRefC
        = toObjectReference("C", "CHARLIE", new String[] {"A"});

    private ObjectReference<String, String> objRefD
        = toObjectReference("D", "DELTA", new String[] {"B", "C"});

    private ObjectReference<String, String> objRefE
        = toObjectReference("E", "ECHO", new String[] {"C", "D"});

    private ObjectReference<String, String> objRefF
        = toObjectReference("F", "FOXTROT", new String[] {"E", "G"});

    private ObjectReference<String, String> objRefG
        = toObjectReference("G", "GOLF", new String[] {"F", "E"});

    private static <K, T> ObjectReference<K, T> toObjectReference(K k, T t, K[] dependencies) {
        ObjectReference<K, T> objRef = new ObjectReference<>(k, t);

        Arrays.stream(dependencies)
            .forEach((K dk) -> objRef.addDependency(dk, 0));
        return objRef;
    }

    @Before
    public void before() {
        //
    }

    @Test
    public void getKEqualsOriginalKey() {
        assertThat(objRefA.getK(), equalTo("A"));
        assertThat(objRefB.getK(), equalTo("B"));
    }

    @Test
    public void getTEqualsOriginalValue() {
        assertThat(objRefA.getT(), equalTo("ALPHA"));
        assertThat(objRefB.getT(), equalTo("BRAVO"));
    }

    @Test
    public void compareToNeitherDependentUponEachOther() {
        assertThat(
            objRefA.compareTo(objRefB),
            equalTo(0));

        assertThat(
            objRefB.compareTo(objRefA),
            equalTo(0));
    }

    @Test
    public void compareToDirectlyDependentUponEachOther() {
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
    public void compareToIsDependedUponByTheOther() {
        assertThat(
            objRefA.compareTo(objRefC),
            lessThanOrEqualTo(-1));

        assertThat(
            objRefB.compareTo(objRefD),
            lessThanOrEqualTo(-1));
    }

    @Test
    public void compareToIsIndirectlyDependentOnTheOther() {
        assertThat(
            objRefA.compareTo(objRefE),
            equalTo(0));

        assertThat(
            objRefB.compareTo(objRefE),
            equalTo(0));

        objRefE.addDependency("A", 1);
        objRefE.addDependency("B", 1);

        assertThat(
            objRefA.compareTo(objRefE),
            lessThanOrEqualTo(-1));

        assertThat(
            objRefB.compareTo(objRefE),
            lessThanOrEqualTo(-1));
    }

    @Test(expected = IllegalStateException.class)
    public void compareToAreCodependentOne() {
        assertThat(
            objRefF.compareTo(objRefG),
            greaterThanOrEqualTo(0));
    }

    @Test(expected = IllegalStateException.class)
    public void compareToAreCodependentTwo() {
        assertThat(
            objRefG.compareTo(objRefF),
            greaterThanOrEqualTo(0));
    }
}
