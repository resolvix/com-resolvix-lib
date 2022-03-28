package com.resolvix.lib.utils;

class ToStringBuilder {

    private static final String NULL_PLACEHOLDER = "<null>";

    private static final String BOOLEAN_TRUE = "true";

    private static final String BOOLEAN_FALSE = "false";

    private final StringBuilder stringBuilder;

    private int valueCount = 0;

    <T> ToStringBuilder(T t) {
        StringBuilder tempStringBuilder = new StringBuilder();
        tempStringBuilder.append('[');
        this.stringBuilder = tempStringBuilder;
    }

    public void appendFieldSeparator(StringBuilder stringBuilder) {
        if (valueCount++ != 0)
            stringBuilder.append(", ");
    }

    /**
     * Appends a {@code boolean} value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param b the boolean
     * @return this
     */
    public ToStringBuilder append(String fieldName, boolean b) {
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        stringBuilder.append(b ? BOOLEAN_TRUE : BOOLEAN_FALSE);
        return this;
    }

    /**
     * Appends an {@code boolean} array value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param bs the boolean array
     * @return this
     */
    public ToStringBuilder append(String fieldName, boolean[] bs) {
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        if (bs != null) {
            int n = 0;
            stringBuilder.append('{');
            if (n < bs.length) {
                stringBuilder.append(' ');
                stringBuilder.append(bs[n++] ? BOOLEAN_TRUE : BOOLEAN_FALSE);
                while (n < bs.length) {
                    stringBuilder.append(", ");
                    stringBuilder.append(bs[n++] ? BOOLEAN_TRUE : BOOLEAN_FALSE);
                }
            }
            stringBuilder.append(' ');
            stringBuilder.append('}');
        } else {
            stringBuilder.append(NULL_PLACEHOLDER);
        }
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
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        stringBuilder.append(b);
        return this;
    }

    /**
     * Appends an {@code byte} array value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param bs the byte array
     * @return this
     */
    public ToStringBuilder append(String fieldName, byte[] bs) {
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        if (bs != null) {
            int n = 0;
            stringBuilder.append('{');
            if (n < bs.length) {
                stringBuilder.append(' ');
                stringBuilder.append(bs[n++]);
                while (n < bs.length) {
                    stringBuilder.append(", ");
                    stringBuilder.append(bs[n++]);
                }
            }
            stringBuilder.append(' ');
            stringBuilder.append('}');
        } else {
            stringBuilder.append(NULL_PLACEHOLDER);
        }
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
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": '");
        stringBuilder.append(c);
        stringBuilder.append("'");
        return this;
    }

    /**
     * Appends an {@code char} array value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param cs the char array
     * @return this
     */
    public ToStringBuilder append(String fieldName, char[] cs) {
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        if (cs != null) {
            int n = 0;
            stringBuilder.append('{');
            if (n < cs.length) {
                stringBuilder.append(' ');
                stringBuilder.append('\'');
                stringBuilder.append(cs[n++]);
                stringBuilder.append('\'');
                while (n < cs.length) {
                    stringBuilder.append(", ");
                    stringBuilder.append('\'');
                    stringBuilder.append(cs[n++]);
                    stringBuilder.append('\'');
                }
            }
            stringBuilder.append(' ');
            stringBuilder.append('}');
        } else {
            stringBuilder.append(NULL_PLACEHOLDER);
        }
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
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        stringBuilder.append(d);
        stringBuilder.append('d');
        return this;
    }

    /**
     * Appends an {@code double} array value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param ds the double array
     * @return this
     */
    public ToStringBuilder append(String fieldName, double[] ds) {
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        if (ds != null) {
            int n = 0;
            stringBuilder.append('{');
            if (n < ds.length) {
                stringBuilder.append(' ');
                stringBuilder.append(ds[n++]);
                stringBuilder.append('d');
                while (n < ds.length) {
                    stringBuilder.append(", ");
                    stringBuilder.append(ds[n++]);
                    stringBuilder.append('d');
                }
            }
            stringBuilder.append(' ');
            stringBuilder.append('}');
        } else {
            stringBuilder.append(NULL_PLACEHOLDER);
        }
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
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        stringBuilder.append(f);
        stringBuilder.append('f');
        return this;
    }

    /**
     * Appends an {@code float} array value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param fs the float array
     * @return this
     */
    public ToStringBuilder append(String fieldName, float[] fs) {
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        if (fs != null) {
            int n = 0;
            stringBuilder.append('{');
            if (n < fs.length) {
                stringBuilder.append(' ');
                stringBuilder.append(fs[n++]);
                stringBuilder.append('f');
                while (n < fs.length) {
                    stringBuilder.append(", ");
                    stringBuilder.append(fs[n++]);
                    stringBuilder.append('f');
                }
            }
            stringBuilder.append(' ');
            stringBuilder.append('}');
        } else {
            stringBuilder.append(NULL_PLACEHOLDER);
        }
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
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        stringBuilder.append(i);
        return this;
    }

    /**
     * Appends an {@code int} array value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param is the array of integers
     * @return this
     */
    public ToStringBuilder append(String fieldName, int[] is) {
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        if (is != null) {
            int n = 0;
            stringBuilder.append('{');
            if (n < is.length) {
                stringBuilder.append(' ');
                stringBuilder.append(is[n++]);
                while (n < is.length) {
                    stringBuilder.append(", ");
                    stringBuilder.append(is[n++]);
                }
            }
            stringBuilder.append(' ');
            stringBuilder.append('}');
        } else {
            stringBuilder.append(NULL_PLACEHOLDER);
        }
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
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        stringBuilder.append(l);
        stringBuilder.append('L');
        return this;
    }

    /**
     * Appends an {@code long} array value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param ls the array of longs
     * @return this
     */
    public ToStringBuilder append(String fieldName, long[] ls) {
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        if (ls != null) {
            int n = 0;
            stringBuilder.append('{');
            if (n < ls.length) {
                stringBuilder.append(' ');
                stringBuilder.append(ls[n++]);
                stringBuilder.append('L');
                while (n < ls.length) {
                    stringBuilder.append(", ");
                    stringBuilder.append(ls[n++]);
                    stringBuilder.append('L');
                }
            }
            stringBuilder.append(' ');
            stringBuilder.append('}');
        } else {
            stringBuilder.append(NULL_PLACEHOLDER);
        }
        return this;
    }

    /**
     * Appends a {@link Object} value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param o the {@link Object}
     * @return this
     */
    public ToStringBuilder append(String fieldName, Object o) {
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        if (o != null) {
            stringBuilder.append('[');
            stringBuilder.append(o.toString());
            stringBuilder.append(']');
        } else {
            stringBuilder.append(NULL_PLACEHOLDER);
        }

        return this;
    }

    /**
     * Appends an {@code object} array value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param os the object array
     * @return this
     */
    public ToStringBuilder append(String fieldName, Object[] os) {
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        if (os != null) {
            int n = 0;
            stringBuilder.append('{');
            if (n < os.length) {
                stringBuilder.append(' ');
                stringBuilder.append('[');
                stringBuilder.append(os[n++].toString());
                stringBuilder.append(']');
                while (n < os.length) {
                    stringBuilder.append(", ");
                    stringBuilder.append('[');
                    stringBuilder.append(os[n++].toString());
                    stringBuilder.append(']');
                }
            }
            stringBuilder.append(' ');
            stringBuilder.append('}');
        } else {
            stringBuilder.append(NULL_PLACEHOLDER);
        }
        return this;
    }

    /**
     * Appends an {@code short} value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param s the short
     * @return this
     */
    public ToStringBuilder append(String fieldName, short s) {
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        stringBuilder.append(s);
        return this;
    }

    /**
     * Appends an {@code short} array value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param ss the array of longs
     * @return this
     */
    public ToStringBuilder append(String fieldName, short[] ss) {
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        if (ss != null) {
            int n = 0;
            stringBuilder.append('{');
            if (n < ss.length) {
                stringBuilder.append(' ');
                stringBuilder.append(ss[n++]);
                while (n < ss.length) {
                    stringBuilder.append(", ");
                    stringBuilder.append(ss[n++]);
                }
            }
            stringBuilder.append(' ');
            stringBuilder.append('}');
        } else {
            stringBuilder.append(NULL_PLACEHOLDER);
        }
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
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        if (s != null) {
            stringBuilder.append('"');
            stringBuilder.append(s);
            stringBuilder.append('"');
        } else {
            stringBuilder.append(NULL_PLACEHOLDER);
        }
        return this;
    }

    /**
     * Appends a {@link String} array value to the {@code toString}.
     *
     * @param fieldName the field name
     * @param ss the string array
     * @return this
     */
    public ToStringBuilder append(String fieldName, String[] ss) {
        appendFieldSeparator(stringBuilder);
        stringBuilder.append(fieldName);
        stringBuilder.append(": ");
        if (ss != null) {
            int n = 0;
            stringBuilder.append('{');
            if (n < ss.length) {
                stringBuilder.append(' ');
                stringBuilder.append('"');
                stringBuilder.append(ss[n++]);
                stringBuilder.append('"');
                while (n < ss.length) {
                    stringBuilder.append(", ");
                    stringBuilder.append('"');
                    stringBuilder.append(ss[n++]);
                    stringBuilder.append('"');
                }
            }
            stringBuilder.append(' ');
            stringBuilder.append('}');
        } else {
            stringBuilder.append(NULL_PLACEHOLDER);
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