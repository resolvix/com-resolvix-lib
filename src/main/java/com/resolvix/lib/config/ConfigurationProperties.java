package com.resolvix.lib.config;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Functionality required:
 *
 * load properties, represent encrypted values, represent array values,
 * represent associate lists, support a hierarchy of configuration
 * properties.
 *
 */
public class ConfigurationProperties {

    private static String EMPTY_STRING = "";

    private static String STRING_REGEX = "^(.+)$";

    private static Pattern STRING_PATTERN = Pattern.compile(STRING_REGEX);

    private static String ENCRYPTED_STRING_REGEX = "^(ENC\\(.*\\))$";

    private static Pattern ENCRYPTED_STRING_PATTERN = Pattern.compile(ENCRYPTED_STRING_REGEX);

    private static String ARRAY_REGEX = "^\\{(\\s?(?:.+)?(?:\\s?|\\s?,\\s?))\\}$";

    private static Pattern ARRAY_PATTERN = Pattern.compile(ARRAY_REGEX);

    private static String ASSOCIATIVE_ARRAY_REGEX = "^\\{(\\s?(?:.+\\:.+)(?:\\s?|\\s?,\\s?))\\}$";

    private static Pattern ASSOCIATIVE_ARRAY_PATTERN = Pattern.compile(ASSOCIATIVE_ARRAY_REGEX);

    private static ValuePatternParser[] valuePatternParsers
            = new ValuePatternParser[] {
                    new ValuePatternParser((String s) -> ASSOCIATIVE_ARRAY_PATTERN.matcher(s).matches(), ConfigurationProperties::parseAssociativeArray),
                    new ValuePatternParser((String s) -> ARRAY_PATTERN.matcher(s).matches(), ConfigurationProperties::parseArray),
                    new ValuePatternParser((String s) -> ENCRYPTED_STRING_PATTERN.matcher(s).matches(), ConfigurationProperties::parseEncryptedString),
                    new ValuePatternParser((String s) -> STRING_PATTERN.matcher(s).matches(), ConfigurationProperties::parseString) };

    private Map<String, Object> map;

    private ConfigurationProperties(Map<String, Object> map) {
        this.map = map;
    }

    private static Object parseAssociativeArray(String string) {
        System.out.print("associativeArray: ");
        System.out.println(string);
        return null;
    }

    private static Object parseArray(String string) {
        System.out.print("array: ");
        System.out.println(string);
        return null;
    }

    private static Object parseEncryptedString(String string) {
        System.out.print("encrypted string: ");
        System.out.println(string);
        return null;
    }

    private static Object parseString(String string) {
        System.out.print("string: ");
        System.out.println(string);
        return null;
    }

    private static Object parseValue(Object value) {
        String s = value.toString();
        String trimmedS = s.trim();
        if (trimmedS.isEmpty())
            return (String) null;

        for (ValuePatternParser valuePatternParser : valuePatternParsers)
            if (valuePatternParser.applyTest(trimmedS))
                return valuePatternParser.getParser().apply(trimmedS);

        throw new IllegalStateException();
    }


    public static ConfigurationProperties parse(InputStream inputStream)
        throws IOException
    {
        Properties properties = new Properties();
        properties.load(inputStream);
        Map<String, Object> map = new HashMap<>();
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = properties.get(key);
            map.put(key.toString(), parseValue(value.toString()));
        }

        return new ConfigurationProperties(map);
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
