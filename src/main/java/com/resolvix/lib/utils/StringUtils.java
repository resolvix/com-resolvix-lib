package com.resolvix.lib.utils;

public class StringUtils {

    private StringUtils() {
        //
    }

    public static String fill(int n, char c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++)
            sb.append(c);
        return sb.toString();
    }

    public static String padLeft(String string, int n, char c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n - string.length(); i++)
            sb.append(c);
        sb.append(string);
        return sb.toString();
    }

    public static String padRight(String string, int n, char c) {
        StringBuilder sb = new StringBuilder();
        sb.append(string);
        for (int i = 0; i < n - string.length(); i++)
            sb.append(c);
        return sb.toString();
    }
}
