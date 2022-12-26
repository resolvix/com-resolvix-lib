package com.resolvix.lib.search;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BinarySplitSearchUT {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private BinarySplitSearch algorithm = new BinarySplitSearch();

    @Test
    public void searchArrayUsingArraysBinarySearchMethod() {
        String[] ss = new String[] {
            "one", "two", "three", "four", "five",
            "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen",
            "sixteen", "seventen", "eighteen", "nineteen", "twenty"};
        Arrays.sort(ss);
        int index = Arrays.binarySearch(ss, "SEVEN", String::compareToIgnoreCase);
        assertThat(ss[index], equalTo("seven"));
    }

    @Test
    public void searchListUsingCollectionsBinarySearchMethod() {
        List<String> list = Arrays.asList(
            "one", "two", "three", "four", "five",
            "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen",
            "sixteen", "seventen", "eighteen", "nineteen", "twenty");
        list.sort(String::compareTo);
        int index = Collections.binarySearch(list, "SEVEN", String::compareToIgnoreCase);
        assertThat(list.get(index), equalTo("seven"));
    }

    @Test
    public void searchArrayUsingBinarySearchMethod() {
        String[] ss = new String[] {
            "one", "two", "three", "four", "five",
            "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen",
            "sixteen", "seventen", "eighteen", "nineteen", "twenty"};
        Arrays.sort(ss);
        assertThat(
            algorithm.search(ss, String::compareToIgnoreCase, "EIGHT"),
            equalTo("eight"));
        assertThat(
            algorithm.search(ss, String::compareToIgnoreCase, "TWO"),
            equalTo("two"));
        assertThat(
            algorithm.search(ss, String::compareToIgnoreCase, "THIRTEEN"),
            equalTo("thirteen"));
        assertThat(
            algorithm.search(ss, String::compareToIgnoreCase, "NINE"),
            equalTo("nine"));
    }
}