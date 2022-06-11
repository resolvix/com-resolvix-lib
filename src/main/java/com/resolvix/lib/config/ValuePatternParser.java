package com.resolvix.lib.config;

import java.util.function.Function;
import java.util.regex.Pattern;

public class ValuePatternParser {

    private Pattern pattern;

    private Function<String, Object> parser;

    public ValuePatternParser(
            Pattern pattern, Function<String, Object> parser) {
        this.pattern = pattern;
        this.parser = parser;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Function<String, Object> getParser() {
        return parser;
    }
}
