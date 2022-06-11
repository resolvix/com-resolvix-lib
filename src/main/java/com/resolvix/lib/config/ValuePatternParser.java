package com.resolvix.lib.config;

import java.util.function.Function;
import java.util.regex.Pattern;

public class ValuePatternParser {

    private Function<String, Boolean> test;

    private Function<String, Object> parser;

    public ValuePatternParser(
            Function<String, Boolean> test, Function<String, Object> parser) {
        this.test = test;
        this.parser = parser;
    }

    public Boolean applyTest(String s) {
        return test.apply(s);
    }

    public Function<String, Object> getParser() {
        return parser;
    }
}
