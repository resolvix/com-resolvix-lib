package com.resolvix.lib.utils;

import org.junit.Test;

import static com.resolvix.lib.utils.RomanUtils.fromRomanNumerals;
import static com.resolvix.lib.utils.RomanUtils.toRomanNumerals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RomanUtilsUT {

    @Test
    public void singleRomanNumerals() {
        assertThat(fromRomanNumerals("I"), equalTo(1));
        assertThat(fromRomanNumerals("V"), equalTo(5));
        assertThat(fromRomanNumerals("X"), equalTo(10));
        assertThat(fromRomanNumerals("L"), equalTo(50));
        assertThat(fromRomanNumerals("C"), equalTo(100));
        assertThat(fromRomanNumerals("D"), equalTo(500));
        assertThat(fromRomanNumerals("M"), equalTo(1000));
    }

    @Test
    public void multipleRomanNumerals() {
        assertThat(fromRomanNumerals("II"), equalTo(2));
        assertThat(fromRomanNumerals("III"), equalTo(3));
        assertThat(fromRomanNumerals("XX"), equalTo(20));
        assertThat(fromRomanNumerals("XXX"), equalTo(30));
        assertThat(fromRomanNumerals("CC"), equalTo(200));
        assertThat(fromRomanNumerals("CCC"), equalTo(300));
        assertThat(fromRomanNumerals("MM"), equalTo(2000));
        assertThat(fromRomanNumerals("MMM"), equalTo(3000));
    }

    @Test
    public void additiveRomanNumerals() {
        assertThat(fromRomanNumerals("VI"), equalTo(6));
        assertThat(fromRomanNumerals("VII"), equalTo(7));
        assertThat(fromRomanNumerals("VIII"), equalTo(8));
        assertThat(fromRomanNumerals("XI"), equalTo(11));
        assertThat(fromRomanNumerals("XII"), equalTo(12));
        assertThat(fromRomanNumerals("XIII"), equalTo(13));
        assertThat(fromRomanNumerals("XV"), equalTo(15));
        assertThat(fromRomanNumerals("LI"), equalTo(51));
        assertThat(fromRomanNumerals("LII"), equalTo(52));
        assertThat(fromRomanNumerals("LIII"), equalTo(53));
        assertThat(fromRomanNumerals("LV"), equalTo(55));
        assertThat(fromRomanNumerals("LX"), equalTo(60));
        assertThat(fromRomanNumerals("LXX"), equalTo(70));
        assertThat(fromRomanNumerals("LXXX"), equalTo(80));
        assertThat(fromRomanNumerals("CI"), equalTo(101));
        assertThat(fromRomanNumerals("CII"), equalTo(102));
        assertThat(fromRomanNumerals("CIII"), equalTo(103));
        assertThat(fromRomanNumerals("CV"), equalTo(105));
        assertThat(fromRomanNumerals("CX"), equalTo(110));
        assertThat(fromRomanNumerals("CL"), equalTo(150));
        assertThat(fromRomanNumerals("MI"), equalTo(1001));
        assertThat(fromRomanNumerals("MII"), equalTo(1002));
        assertThat(fromRomanNumerals("MIII"), equalTo(1003));
        assertThat(fromRomanNumerals("MV"), equalTo(1005));
        assertThat(fromRomanNumerals("MX"), equalTo(1010));
        assertThat(fromRomanNumerals("ML"), equalTo(1050));
        assertThat(fromRomanNumerals("MC"), equalTo(1100));
        assertThat(fromRomanNumerals("MM"), equalTo(2000));
    }

    @Test
    public void subtractiveRomanNumerals() {
        assertThat(fromRomanNumerals("IV"), equalTo(4));
        assertThat(fromRomanNumerals("IX"), equalTo(9));
        assertThat(fromRomanNumerals("XL"), equalTo(40));
        assertThat(fromRomanNumerals("XC"), equalTo(90));
        assertThat(fromRomanNumerals("CD"), equalTo(400));
        assertThat(fromRomanNumerals("CM"), equalTo(900));
    }

    @Test
    public void compoundRomanNumerals() {
        assertThat(fromRomanNumerals("XIX"), equalTo(19));
    }

    @Test
    public void toRomanNumeralsTest() {
        assertThat(toRomanNumerals(1974, true), equalTo("MCMLXXIV"));
        assertThat(toRomanNumerals(2000, true), equalTo("MM"));
        assertThat(toRomanNumerals(2018, true), equalTo("MMXVIII"));
        assertThat(toRomanNumerals(2019, true), equalTo("MMXIX"));
        assertThat(toRomanNumerals(2020, true), equalTo("MMXX"));
    }
}
