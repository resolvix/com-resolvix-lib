package com.resolvix.lib.utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class InputStreamUtilsUT {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String SAMPLE_STRING = "<sample string>";

    @Before
    public void before() {

    }

    @Test
    public void toString_given_null_input_stream() throws IOException {
        expectedException.expect(NullPointerException.class);
        InputStreamUtils.toString((InputStream) null, StandardCharsets.UTF_8);
    }

    @Test
    public void toString_given_valid_input_stream() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(SAMPLE_STRING.getBytes(StandardCharsets.UTF_8));
        assertThat(
            InputStreamUtils.toString(inputStream, StandardCharsets.UTF_8),
            equalTo(SAMPLE_STRING));
    }
}
