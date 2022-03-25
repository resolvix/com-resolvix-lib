package com.resolvix.lib.utils;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ToStringBuilderUT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ToStringBuilder getToStringBuilder() {
        return new ToStringBuilder(this);
    }

    @Test
    public void appendBooleanTrueFalse() {
        assertThat(
            getToStringBuilder()
                .append("boolean_1", true)
                .append("boolean_2", false)
                .build(),
            equalTo("[boolean_1: true, boolean_2: false]"));
    }

    @Test
    public void appendByte() {
        assertThat(
            getToStringBuilder()
                .append("minByte", Byte.MIN_VALUE)
                .append("maxByte", Byte.MAX_VALUE)
                .build(),
            equalTo("[minByte: -128, maxByte: 127]"));
    }

    @Test
    public void appendChar() {
        assertThat(
            getToStringBuilder()
                .append("char_1", 'a')
                .append("char_2", 'b')
                .build(),
            equalTo("[char_1: 'a', char_2: 'b']"));
    }

    @Test
    public void appendDouble() {
        assertThat(
            getToStringBuilder()
                .append("double_1", 1.0d)
                .append("double_2", 2.0d)
                .build(),
            equalTo("[double_1: 1.0d, double_2: 2.0d]"));
    }

    @Test
    public void appendFloat() {
        assertThat(
            getToStringBuilder()
                .append("float_1", 1.0f)
                .append("float_2", 2.0f)
                .build(),
            equalTo("[float_1: 1.0f, float_2: 2.0f]"));
    }

    @Test
    public void appendInt() {
        assertThat(
            getToStringBuilder()
                .append("int_1", 1)
                .append("int_2", 2)
                .build(),
            equalTo("[int_1: 1, int_2: 2]"));
    }

    @Test
    public void appendLong() {
        assertThat(
            getToStringBuilder()
                .append("long_1", 1l)
                .append("long_2", 2l)
                .build(),
            equalTo("[long_1: 1l, long_2: 2l]"));
    }

    @Test
    public void appendStringGivenNullValue() {
        assertThat(
            getToStringBuilder()
                .append("String", (String) null)
                .build(),
            equalTo("[String: <null>]"));
    }

    @Test
    public void appendStringGivenNonNullValue() {
        assertThat(
            getToStringBuilder()
                .append("String_1", "<value1>")
                .append("String_2", "<value2>")
                .build(),
            equalTo("[String_1: \"<value1>\", String_2: \"<value2>\"]"));
    }
}
