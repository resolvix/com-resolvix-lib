package com.resolvix.lib.utils;

import org.junit.Test;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ArrayUtilsUT {

    private Integer[] intArray = new Integer[] { 0, 1, 2, 3, 4, 5 };

    private Character[][] charArray = new Character[][] {
        {'a', 'b'},             //  0
        {'c', 'd', 'e'},        //  1
        {'f'},                  //  2
        { },                    //  3
        {'g', 'h', 'i', 'j'},   //  4
        {'k', 'l', 'm', 'n'}    //  5
    };

    @Test
    public void contains() {
        assertThat(
            ArrayUtils.contains(intArray, 5),
            equalTo(true));

        assertThat(
            ArrayUtils.contains(intArray, 6),
            equalTo(false));
    }

    private Character[] get(int index) {
        return charArray[index];
    }

    @Test
    public void flatMap() {
        Character[] flatMap = ArrayUtils.flatMap(intArray, Character.class, this::get);
        assertThat(
            flatMap,
            arrayContaining('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n'));
    }
}
