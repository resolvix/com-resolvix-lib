package com.resolvix.lib.writer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StringBuilderWriterUT {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String SAMPLE_STRING = "<sample string>";

    private static final char[] SAMPLE_CHAR_ARRAY = SAMPLE_STRING.toCharArray();

    @Before
    public void before() {
        //
    }

    @Test
    public void test() throws IOException {
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        stringBuilderWriter.write(SAMPLE_CHAR_ARRAY, 0, SAMPLE_CHAR_ARRAY.length);
        stringBuilderWriter.flush();
        assertThat(
            stringBuilderWriter.toString(),
            equalTo(SAMPLE_STRING));
    }
}
