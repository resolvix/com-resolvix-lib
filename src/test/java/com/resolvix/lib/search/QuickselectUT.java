package com.resolvix.lib.search;

import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class QuickselectUT {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Quickselect algorithm = new Quickselect();

    @Test
    public void select() {
        String[] ss = new String[] {
            "one", "two", "three", "four", "five",
            "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen",
            "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
        assertThat(
            algorithm.find(ss, String::compareTo, 0),
            equalTo("eight"));
        assertThat(
            algorithm.find(ss, String::compareTo, 19),
            equalTo("two"));
    }
}
