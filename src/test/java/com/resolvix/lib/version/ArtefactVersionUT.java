package com.resolvix.lib.version;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.internal.matchers.Null;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArtefactVersionUT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before() {
        //
    }

    //
    //  parse/1
    //

    @Test
    public void parseGivenNullValue() {
        thrown.expect(NullPointerException.class);
        ArtefactVersion.parse(null);
    }

    @Test
    public void parseGivenNonCompliantVersion() {
        thrown.expect(IllegalArgumentException.class);
        ArtefactVersion.parse("A.B.C-1-D");
    }

    @Test
    public void parseGivenMajorVersion() {
        assertThat(
            ArtefactVersion.parse("1"),
            equalTo(ArtefactVersion.of(1)));
    }

    @Test
    public void parseGivenMajorMinorVersion() {
        assertThat(
            ArtefactVersion.parse("1.2"),
            equalTo(ArtefactVersion.of(1, 2)));
    }

    @Test
    public void parseGivenMajorMinorIncrementalVersion() {
        assertThat(
            ArtefactVersion.parse("1.2.3"),
            equalTo(ArtefactVersion.of(1, 2, 3)));
    }

    @Test
    public void parseGivenMajorMinorIncrementalQualifierVersion() {
        assertThat(
            ArtefactVersion.parse("1.2.3-SNAPSHOT"),
            equalTo(ArtefactVersion.of(1, 2, 3, "SNAPSHOT")));
    }

    @Test
    public void parseGivenMajorMinorIncrementalQualifierBuildNumberVersion() {
        assertThat(
            ArtefactVersion.parse("1.2.3-SNAPSHOT-12"),
            equalTo(ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 12)));
    }

    //
    //  compareTo/1
    //

    @Test
    public void compareToGivenNullValue() {
        thrown.expect(NullPointerException.class);
        int result = ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)
            .compareTo(null);
    }

    @Test
    public void compareToGivenEqualVersion() {
        assertThat(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)
                .compareTo(ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)),
            equalTo(0));
    }

    @Test
    public void compareToGivenGreaterMajorVersion() {
        assertThat(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)
                .compareTo(ArtefactVersion.of(0, 2, 3, "SNAPSHOT", 4)),
            equalTo(1));
    }

    @Test
    public void compareToGivenLesserMajorVersion() {
        assertThat(
            ArtefactVersion.of(0, 2, 3, "SNAPSHOT", 4)
                .compareTo(ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)),
            equalTo(-1));
    }

    @Test
    public void compareToGivenGreaterMinorVersion() {
        assertThat(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)
                .compareTo(ArtefactVersion.of(1, 1, 3, "SNAPSHOT", 4)),
            equalTo(1));
    }

    @Test
    public void compareToGivenLesserMinorVersion() {
        assertThat(
            ArtefactVersion.of(1, 1, 3, "SNAPSHOT", 4)
                .compareTo(ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)),
            equalTo(-1));
    }

    @Test
    public void compareToGivenGreaterIncrementalVersion() {
        assertThat(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)
                .compareTo(ArtefactVersion.of(1, 2, 2, "SNAPSHOT", 4)),
            equalTo(1));
    }

    @Test
    public void compareToGivenLesserIncrementalVersion() {
        assertThat(
            ArtefactVersion.of(1, 2, 2, "SNAPSHOT", 4)
                .compareTo(ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)),
            equalTo(-1));
    }

    @Test
    public void compareToGivenGreaterNullQualifierVersion() {
        assertThat(
            ArtefactVersion.of(1, 2, 3, null, 4)
                .compareTo(ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)),
            equalTo(1));
    }

    @Test
    public void compareToGivenLesserNullQualifierVersion() {
        assertThat(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)
                .compareTo(ArtefactVersion.of(1, 2, 3, null, 4)),
            equalTo(-1));
    }

    @Test
    public void compareToGivenGreaterNonNullQualifierVersion() {
        assertThat(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOTT", 4)
                .compareTo(ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)),
            equalTo(1));
    }

    @Test
    public void compareToGivenLesserNonNullQualifierVersion() {
        assertThat(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)
                .compareTo(ArtefactVersion.of(1, 2, 3, "SNAPSHOTT", 4)),
            equalTo(-1));
    }

    @Test
    public void compareToGivenGreaterBuildNumber() {
        assertThat(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)
                .compareTo(ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 3)),
            equalTo(1));
    }

    @Test
    public void compareToGivenLesserBuildNumber() {
        assertThat(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 3)
                .compareTo(ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)),
            equalTo(-1));
    }

    //
    //  equals
    //

    @Test
    public void equalsGivenNullNonArtefactVersionValue() {
        assertFalse(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)
                .equals(null));
    }

    @Test
    public void equalsGivenNullValue() {
        assertFalse(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)
                .equals("1.2.3-SNAPSHOT-4"));
    }

    @Test
    public void equalsGivenSameVersionObjects() {
        ArtefactVersion version =  ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4);
        assertTrue(version.equals(version));
    }

    @Test
    public void equalsGivenEqualVersions() {
        assertTrue(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)
                .equals(ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)));
    }

    @Test
    public void equalsGivenUnEqualVersions() {
        assertFalse(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 3)
                .equals(ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4)));
    }

    //
    //  toString
    //

    @Test
    public void toStringGivenMajorVersion() {
        assertThat(
            ArtefactVersion.of(1).toString(),
            equalTo("1"));
    }

    @Test
    public void toStringGivenMajorMinorVersion() {
        assertThat(
            ArtefactVersion.of(1, 2).toString(),
            equalTo("1.2"));
    }

    @Test
    public void toStringGivenMajorIncrementalVersion() {
        assertThat(
            ArtefactVersion.of(1,2, 3).toString(),
            equalTo("1.2.3"));
    }

    @Test
    public void toStringGivenMajorMinorIncrementalVersionQualifier() {
        assertThat(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOT").toString(),
            equalTo("1.2.3-SNAPSHOT"));
    }

    @Test
    public void toStringGivenMajorMinorIncrementalVersionQualifierBuildNumber() {
        assertThat(
            ArtefactVersion.of(1, 2, 3, "SNAPSHOT", 4).toString(),
            equalTo("1.2.3-SNAPSHOT-4"));
    }
}