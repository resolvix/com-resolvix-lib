package com.resolvix.lib.log;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class EncoderUT {

    private Encoder encoder = Encoder.getEncoder();

    @Before
    public void before() {
        //
    }

    @Test
    public void encode() {
        String s = "string";
        assertThat(encoder.encode(s), equalTo(s));
    }
}
