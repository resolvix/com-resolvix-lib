package com.resolvix.lib.config;

abstract class BaseValueParserImpl<T>
    implements ValueParser<T>
{

    protected boolean isWhitespace(char ch) {
        return Character.isWhitespace(ch);
    }
}
