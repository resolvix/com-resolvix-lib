package com.resolvix.lib.utils;

import org.junit.Test;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class StringUtilsUT {

    //
    //  fill
    //

    @Test
    public void fillZeroLength() {
        assertThat(
            StringUtils.fill(0, 'X'),
            emptyString());
    }

    @Test
    public void fillNonZeroLength() {
        assertThat(
            StringUtils.fill(10, 'X'),
            equalTo("XXXXXXXXXX"));
    }

    //
    //  padLeft
    //

    @Test
    public void padLeftEmptyString() {
        assertThat(
            StringUtils.padLeft("", 10, 'X'),
            equalTo("XXXXXXXXXX"));
    }

    @Test
    public void padLeftUnderLengthString() {
        assertThat(
            StringUtils.padLeft("ABC", 10, 'X'),
            equalTo("XXXXXXXABC"));
    }

    @Test
    public void padLeftExactLengthString() {
        assertThat(
            StringUtils.padLeft("ABCDEFGHIJ", 10, 'X'),
            equalTo("ABCDEFGHIJ"));
    }

    @Test
    public void padLeftOverLengthString() {
        assertThat(
            StringUtils.padLeft("ABCDEFGHIJK", 10, 'X'),
            equalTo("ABCDEFGHIJK"));
    }

    //
    //  padRight
    //

    @Test
    public void padRightEmptyString() {
        assertThat(
            StringUtils.padRight("", 10, 'X'),
            equalTo("XXXXXXXXXX"));
    }

    @Test
    public void padRightUnderLengthString() {
        assertThat(
            StringUtils.padRight("ABC", 10, 'X'),
            equalTo("ABCXXXXXXX"));
    }

    @Test
    public void padRightExactLengthString() {
        assertThat(
            StringUtils.padRight("ABCDEFGHIJ", 10, 'X'),
            equalTo("ABCDEFGHIJ"));
    }

    @Test
    public void padRightOverLengthString() {
        assertThat(
            StringUtils.padRight("ABCDEFGHIJK", 10, 'X'),
            equalTo("ABCDEFGHIJK"));
    }
}
