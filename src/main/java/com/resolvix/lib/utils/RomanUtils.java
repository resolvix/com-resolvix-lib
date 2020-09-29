package com.resolvix.lib.utils;

import com.resolvix.lib.map.MapBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class RomanUtils {

    private static enum RomanNumbers {
        I("I", "i", 1),
        IV("IV", "iv", 4),
        V("V", "v", 5),
        IX("IX", "ix", 9),
        X("X", "x", 10),
        XL("XL", "xl", 40),
        L("L", "l", 50),
        XC("XC", "xc", 90),
        C("C", "c", 100),
        CD("CD", "cd", 400),
        D("D", "d", 500),
        CM("CM", "cm", 900),
        M("M", "m", 1000);

        private String uppercase;

        private String lowercase;

        private int value;

        private RomanNumbers(String uppercase, String lowercase, int value) {
            this.uppercase = uppercase;
            this.lowercase = lowercase;
            this.value = value;
        }

        public String getUppercase() {
            return uppercase;
        }

        public String getLowercase() {
            return lowercase;
        }

        public int getValue() {
            return value;
        }
    }

    private static enum RomanNumeral {

        I('I', 'i', 1),
        V('V', 'v', 5),
        X('X', 'x', 10),
        L('L', 'l', 50),
        C('C', 'c', 100),
        D('D', 'd', 500),
        M('M', 'm', 1000);

        private char uppercase;

        private char lowercase;

        private int value;

        private RomanNumeral(char uppercase, char lowercase, int value) {
            this.uppercase = uppercase;
            this.lowercase = lowercase;
            this.value = value;
        }

        public char getUppercase() {
            return uppercase;
        }

        public char getLowercase() {
            return lowercase;
        }

        public int getValue() {
            return value;
        }
    }

    private static final Map<Character, Integer> numerals
        = MapBuilder.getBuilder(Character.class, Integer.class)
            .put('I', 1)
            .put('V', 5)
            .put('X', 10)
            .put('L', 50)
            .put('C', 100)
            .put('D', 500)
            .put('M', 1000)
            .build();

    private static final Comparator<RomanNumeral> ROMAN_NUMERAL_COMPARATOR_ASC
        = (RomanNumeral l, RomanNumeral r) -> Integer.compare(l.getValue(), r.getValue());

    private static final Comparator<RomanNumbers> ROMAN_NUMBERS_COMPARATOR_ASC
        = (RomanNumbers l, RomanNumbers r) -> Integer.compare(l.getValue(), r.getValue());

    private static final Comparator<RomanNumeral> ROMAN_NUMERAL_COMPARATOR_DESC
        = ROMAN_NUMERAL_COMPARATOR_ASC.reversed();

    private static final Comparator<RomanNumbers> ROMAN_NUMBERS_COMPARATOR_DESC
        = ROMAN_NUMBERS_COMPARATOR_ASC.reversed();

    private static final List<RomanNumeral> ROMAN_NUMERALS_DESCENDING
        = Arrays.asList(RomanNumeral.values())
            .stream()
            .sorted(ROMAN_NUMERAL_COMPARATOR_DESC)
            .collect(Collectors.toList());

    private static final List<RomanNumbers> ROMAN_NUMBERS_DESCENDING
        = Arrays.asList(RomanNumbers.values())
        .stream()
        .sorted(ROMAN_NUMBERS_COMPARATOR_DESC)
        .collect(Collectors.toList());

    public static String toRomanNumerals(int value, boolean uppercase) {
        int scratch = value;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ROMAN_NUMBERS_DESCENDING.size(); i++) {
            RomanNumbers romanNumbers = ROMAN_NUMBERS_DESCENDING.get(i);
            int n = scratch / romanNumbers.getValue();
            if (n > 0) {
                for (int j = 0; j < n; j++)
                    sb.append(uppercase ? romanNumbers.getUppercase() : romanNumbers.getLowercase());
                scratch -= n * romanNumbers.getValue();
            }
        }

        return sb.toString();
    }

    public static int fromRomanNumerals(String s) {
        int t = 0;
        for (int i = 0; i < s.length(); i++) {
            int n = numerals.getOrDefault(s.charAt(i), 0);
            if (i + 1 < s.length()) {
                int n2 = numerals.getOrDefault(s.charAt(i + 1), 0);
                if (n < n2) {
                    t -= n;
                } else {
                    t += n;
                }
            } else {
                t += n;
            }
        }

        return t;
    }
}
