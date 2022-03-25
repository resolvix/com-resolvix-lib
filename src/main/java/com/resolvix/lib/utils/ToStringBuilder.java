package com.resolvix.lib.utils;

class ToStringBuilder {

    private StringBuilder stringBuilder;

    private int valueCount = 0;

    <T> ToStringBuilder(T t) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        this.stringBuilder = stringBuilder;
    }

    /**
     * Appends a {@code boolean} value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param b the boolean
     * @return this
     */
    public ToStringBuilder append(String fieldName, boolean b) {
        if (valueCount++ != 0)
            stringBuilder.append(", ");

        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        stringBuilder.append(b ? "true" : "false");
        return this;
    }

    /**
     * Appends a {@code byte} value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param b the byte
     * @return this
     */
    public ToStringBuilder append(String fieldName, byte b) {
        if (valueCount++ != 0)
            stringBuilder.append(", ");

        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        stringBuilder.append(b);
        return this;
    }

    /**
     * Appends a {@code char} value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param c the character
     * @return this
     */
    public ToStringBuilder append(String fieldName, char c) {
        if (valueCount++ != 0)
            stringBuilder.append(", ");

        stringBuilder.append(fieldName);
        stringBuilder.append(": '");
        stringBuilder.append(c);
        stringBuilder.append("'");
        return this;
    }

    /**
     * Append a {@code double} value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param d the double
     * @return this
     */
    public ToStringBuilder append(String fieldName, double d) {
        if (valueCount++ != 0)
            stringBuilder.append(", ");

        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        stringBuilder.append(d);
        stringBuilder.append('d');
        return this;
    }

    /**
     * Append a {@code float} value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param f the float
     * @return this
     */
    public ToStringBuilder append(String fieldName, float f) {
        if (valueCount++ != 0)
            stringBuilder.append(", ");

        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        stringBuilder.append(f);
        stringBuilder.append('f');
        return this;
    }

    /**
     * Appends an {@code int} value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param i the integer
     * @return this
     */
    public ToStringBuilder append(String fieldName, int i) {
        if (valueCount++ != 0)
            stringBuilder.append(", ");

        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        stringBuilder.append(i);
        return this;
    }

    /**
     * Appends a {@code long} value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param l the long
     * @return this
     */
    public ToStringBuilder append(String fieldName, long l) {
        if (valueCount++ != 0)
            stringBuilder.append(", ");

        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        stringBuilder.append(l);
        stringBuilder.append('l');
        return this;
    }

    /**
     * Appends a {@link String} value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param s the string
     * @return this
     */
    public ToStringBuilder append(String fieldName, String s) {
        if (valueCount++ != 0)
            stringBuilder.append(", ");

        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        if (s != null) {
            stringBuilder.append('"');
            stringBuilder.append(s);
            stringBuilder.append('"');
        } else {
            stringBuilder.append("<null>");
        }
        return this;
    }

    /**
     * Builds and returns the {@code toString}.
     *
     * @return the string
     */
    public String build() {
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}