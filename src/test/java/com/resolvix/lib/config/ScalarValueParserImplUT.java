package com.resolvix.lib.config;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ScalarValueParserImplUT {

    ScalarValueParserImpl parser;

    @Before
    public void before() {
        this.parser = new ScalarValueParserImpl();
    }

    //
    //  parseNumber -related tests
    //

    @Test
    public void testIntegerNumber() {
        assertThat(
                parser.parse("12345"),
                equalTo("12345"));
    }

    @Test
    public void testRealNumber() {
        assertThat(
                parser.parse("12345.54321"),
                equalTo("12345.54321"));
    }

    //
    //  parseUndelimitedString -related tests
    //

    @Test
    public void testUndelimitedString() {
        assertThat(
                parser.parse("Undelimited String containing \" character"),
                equalTo("Undelimited String containing \" character"));
    }

    @Test
    public void testUndelimitedStringBeginningWithIntegerNumber() {
        assertThat(
                parser.parse("12345 IntegerNumber"),
                equalTo("12345 IntegerNumber"));
    }

    @Test
    public void testUndelimitedStringBeginningWithRealNumber() {
        assertThat(
                parser.parse("12345.54321 RealNumber"),
                equalTo("12345.54321 RealNumber"));
    }

    //
    //  parseDelimitedString -related tests
    //

    @Test
    public void testSingleQuoteDelimitedString() {
        assertThat(
                parser.parse("'Delimited String with \\\\\\' delimiter '"),
                equalTo("Delimited String with \\' delimiter"));
    }

    @Test
    public void testDoubleQuoteDelimitedString() {
        assertThat(
                parser.parse("\"Delimited String with \\\\\\\" delimiter \""),
                equalTo("Delimited String with \\\" delimiter"));
    }
}
