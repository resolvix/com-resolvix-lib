package com.resolvix.lib.config;

import java.text.DecimalFormatSymbols;

class OrdinaryValueParserImpl
    extends BaseValueParserImpl<String>
    implements ValueParser<String>
{

    private static final String DELIMITERS = "\"'";

    private static final String ESCAPE = "\\";

    private static final char DECIMAL_SEPARATOR = DecimalFormatSymbols.getInstance().getDecimalSeparator();

    private boolean isStringDelimiter(char ch) {
        return (DELIMITERS.indexOf(ch) != -1);
    }

    private boolean isEscape(char ch) {
        return (ESCAPE.indexOf(ch) != -1);
    }

    private void parseRealNumber(StringBuilder sb, CharSequence cs, int i, int i_max) {
        char ch;
        do {
            ch = cs.charAt(i);
            if (Character.isDigit(ch))
                sb.append(ch);
            else {
                sb.append(ch);
                parseUndelimitedString(sb, cs, ++i, i_max);
                break;
            }
        } while (++i < i_max);
    }

    private void parseNumber(StringBuilder sb, CharSequence cs, int i, int i_max) {
        char ch;
        do {
            ch = cs.charAt(i);
            if (Character.isDigit(ch))
                sb.append(ch);
            else if (ch == DECIMAL_SEPARATOR) {
                sb.append(ch);
                parseRealNumber(sb, cs, ++i, i_max);
                break;
            } else {
                sb.append(ch);
                parseUndelimitedString(sb, cs, ++i, i_max);
                break;
            }
        } while (++i < i_max);
    }

    private void parseUndelimitedString(StringBuilder sb, CharSequence cs, int i, int i_max) {
        char ch;
        do {
            ch = cs.charAt(i);
            sb.append(ch);
        } while (++i < i_max);
    }

    private void parseDelimitedString(StringBuilder sb, CharSequence cs, int i, int i_max, char delimiter) {
        if (++i >= i_max)
            return;

        char ch;
        do {
            ch = cs.charAt(i);
            if (ch == delimiter)
                break;

            if (isEscape(ch)) {
                if (++i < i_max) {
                    ch = cs.charAt(i);
                    sb.append(ch);
                } else break;
            } else {
                sb.append(ch);
            }
        } while (++i < i_max);
    }

    @Override
    public String parse(String s) {
        int i = 0;
        int i_max = s.length();

        //
        //  Purge leading whitespace
        //
        char lookahead;
        do {
            lookahead = s.charAt(i);
            if (!isWhitespace(lookahead))
                break;
        } while (++i < i_max);

        // begin substantive parse
        StringBuilder sb = new StringBuilder();
        if (Character.isDigit(lookahead))
            parseNumber(sb, s, i, i_max);
        else if (isStringDelimiter(lookahead))
            parseDelimitedString(sb, s, i, i_max, lookahead);
        else
            parseUndelimitedString(sb, s, i, i_max);

        do {
            lookahead = s.charAt(i);
            if (!isWhitespace(lookahead))
                break;
        } while (++i < i_max);

        return sb.toString().trim();
    }
}
