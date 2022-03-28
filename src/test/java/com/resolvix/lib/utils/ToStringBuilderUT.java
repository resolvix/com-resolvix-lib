package com.resolvix.lib.utils;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ToStringBuilderUT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ToStringBuilder getToStringBuilder() {
        return new ToStringBuilder(this);
    }

    @Test
    public void appendBooleanTrueFalse() {
        assertThat(
            getToStringBuilder()
                .append("boolean_1", true)
                .append("boolean_2", false)
                .build(),
            equalTo("[boolean_1: true, boolean_2: false]"));
    }

    @Test
    public void appendBooleanArrayGivenNullReference() {
        assertThat(
            getToStringBuilder()
                .append("boolean_array_1", (boolean[]) null)
                .append("boolean_array_2", (boolean[]) null)
                .build(),
            equalTo("[boolean_array_1: <null>, boolean_array_2: <null>]"));
    }

    @Test
    public void appendBooleanArrayGivenEmptyArray() {
        assertThat(
            getToStringBuilder()
                .append("boolean_array_1", new boolean[] { })
                .append("boolean_array_2", new boolean[] { })
                .build(),
            equalTo("[boolean_array_1: { }, boolean_array_2: { }]"));
    }

    @Test
    public void appendBooleanArrayGivenOneElement() {
        assertThat(
            getToStringBuilder()
                .append("boolean_array_1", new boolean[] { true })
                .append("boolean_array_2", new boolean[] { false })
                .build(),
            equalTo("[boolean_array_1: { true }, boolean_array_2: { false }]"));
    }

    @Test
    public void appendBooleanArrayGivenMultipleElements() {
        assertThat(
            getToStringBuilder()
                .append("boolean_array_1", new boolean[] { true, false })
                .append("boolean_array_2", new boolean[] { false, true })
                .build(),
            equalTo("[boolean_array_1: { true, false }, boolean_array_2: { false, true }]"));
    }

    @Test
    public void appendByte() {
        assertThat(
            getToStringBuilder()
                .append("minByte", Byte.MIN_VALUE)
                .append("maxByte", Byte.MAX_VALUE)
                .build(),
            equalTo("[minByte: -128, maxByte: 127]"));
    }

    @Test
    public void appendByteArrayGivenNullReference() {
        assertThat(
            getToStringBuilder()
                .append("byte_array_1", (byte[]) null)
                .append("byte_array_2", (byte[]) null)
                .build(),
            equalTo("[byte_array_1: <null>, byte_array_2: <null>]"));
    }

    @Test
    public void appendByteArrayGivenEmptyArray() {
        assertThat(
            getToStringBuilder()
                .append("byte_array_1", new byte[] { })
                .append("byte_array_2", new byte[] { })
                .build(),
            equalTo("[byte_array_1: { }, byte_array_2: { }]"));
    }

    @Test
    public void appendByteArrayGivenOneElement() {
        assertThat(
            getToStringBuilder()
                .append("byte_array_1", new byte[] { 1 })
                .append("byte_array_2", new byte[] { 2 })
                .build(),
            equalTo("[byte_array_1: { 1 }, byte_array_2: { 2 }]"));
    }

    @Test
    public void appendByteArrayGivenMultipleElements() {
        assertThat(
            getToStringBuilder()
                .append("byte_array_1", new byte[] { 1, 2 })
                .append("byte_array_2", new byte[] { 3, 4 })
                .build(),
            equalTo("[byte_array_1: { 1, 2 }, byte_array_2: { 3, 4 }]"));
    }

    @Test
    public void appendChar() {
        assertThat(
            getToStringBuilder()
                .append("char_1", 'a')
                .append("char_2", 'b')
                .build(),
            equalTo("[char_1: 'a', char_2: 'b']"));
    }

    @Test
    public void appendCharArrayGivenNullReference() {
        assertThat(
            getToStringBuilder()
                .append("char_array_1", (char[]) null)
                .append("char_array_2", (char[]) null)
                .build(),
            equalTo("[char_array_1: <null>, char_array_2: <null>]"));
    }

    @Test
    public void appendCharArrayGivenEmptyArray() {
        assertThat(
            getToStringBuilder()
                .append("char_array_1", new char[] { })
                .append("char_array_2", new char[] { })
                .build(),
            equalTo("[char_array_1: { }, char_array_2: { }]"));
    }

    @Test
    public void appendCharArrayGivenOneElement() {
        assertThat(
            getToStringBuilder()
                .append("char_array_1", new char[] { 'A' })
                .append("char_array_2", new char[] { 'B' })
                .build(),
            equalTo("[char_array_1: { 'A' }, char_array_2: { 'B' }]"));
    }

    @Test
    public void appendCharArrayGivenMultipleElements() {
        assertThat(
            getToStringBuilder()
                .append("char_array_1", new char[] { 'A', 'B' })
                .append("char_array_2", new char[] { 'C', 'D' })
                .build(),
            equalTo("[char_array_1: { 'A', 'B' }, char_array_2: { 'C', 'D' }]"));
    }

    @Test
    public void appendDouble() {
        assertThat(
            getToStringBuilder()
                .append("double_1", 1.0d)
                .append("double_2", 2.0d)
                .build(),
            equalTo("[double_1: 1.0d, double_2: 2.0d]"));
    }

    @Test
    public void appendDoubleArrayGivenNullReference() {
        assertThat(
            getToStringBuilder()
                .append("double_array_1", (double[]) null)
                .append("double_array_2", (double[]) null)
                .build(),
            equalTo("[double_array_1: <null>, double_array_2: <null>]"));
    }

    @Test
    public void appendDoubleArrayGivenEmptyArray() {
        assertThat(
            getToStringBuilder()
                .append("double_array_1", new double[] { })
                .append("double_array_2", new double[] { })
                .build(),
            equalTo("[double_array_1: { }, double_array_2: { }]"));
    }

    @Test
    public void appendDoubleArrayGivenOneElement() {
        assertThat(
            getToStringBuilder()
                .append("double_array_1", new double[] { 1.0d })
                .append("double_array_2", new double[] { 2.0d })
                .build(),
            equalTo("[double_array_1: { 1.0d }, double_array_2: { 2.0d }]"));
    }

    @Test
    public void appendDoubleArrayGivenMultipleElements() {
        assertThat(
            getToStringBuilder()
                .append("double_array_1", new double[] { 1.0d, 2.0d })
                .append("double_array_2", new double[] { 3.0d, 4.0d })
                .build(),
            equalTo("[double_array_1: { 1.0d, 2.0d }, double_array_2: { 3.0d, 4.0d }]"));
    }

    @Test
    public void appendFloat() {
        assertThat(
            getToStringBuilder()
                .append("float_1", 1.0f)
                .append("float_2", 2.0f)
                .build(),
            equalTo("[float_1: 1.0f, float_2: 2.0f]"));
    }

    @Test
    public void appendFloatArrayGivenNullReference() {
        assertThat(
            getToStringBuilder()
                .append("float_array_1", (float[]) null)
                .append("float_array_2", (float[]) null)
                .build(),
            equalTo("[float_array_1: <null>, float_array_2: <null>]"));
    }

    @Test
    public void appendFloatArrayGivenEmptyArray() {
        assertThat(
            getToStringBuilder()
                .append("float_array_1", new float[] { })
                .append("float_array_2", new float[] { })
                .build(),
            equalTo("[float_array_1: { }, float_array_2: { }]"));
    }

    @Test
    public void appendFloatArrayGivenOneElement() {
        assertThat(
            getToStringBuilder()
                .append("float_array_1", new float[] { 1.0f })
                .append("float_array_2", new float[] { 2.0f })
                .build(),
            equalTo("[float_array_1: { 1.0f }, float_array_2: { 2.0f }]"));
    }

    @Test
    public void appendFloatArrayGivenMultipleElements() {
        assertThat(
            getToStringBuilder()
                .append("float_array_1", new float[] { 1.0f, 2.0f })
                .append("float_array_2", new float[] { 3.0f, 4.0f })
                .build(),
            equalTo("[float_array_1: { 1.0f, 2.0f }, float_array_2: { 3.0f, 4.0f }]"));
    }

    @Test
    public void appendInt() {
        assertThat(
            getToStringBuilder()
                .append("int_1", 1)
                .append("int_2", 2)
                .build(),
            equalTo("[int_1: 1, int_2: 2]"));
    }

    @Test
    public void appendIntArrayGivenNullReference() {
        assertThat(
            getToStringBuilder()
                .append("int_array_1", (int[]) null)
                .append("int_array_2", (int[]) null)
                .build(),
            equalTo("[int_array_1: <null>, int_array_2: <null>]"));
    }

    @Test
    public void appendIntArrayGivenEmptyArray() {
        assertThat(
            getToStringBuilder()
                .append("int_array_1", new int[] { })
                .append("int_array_2", new int[] { })
                .build(),
            equalTo("[int_array_1: { }, int_array_2: { }]"));
    }

    @Test
    public void appendIntArrayGivenOneElement() {
        assertThat(
            getToStringBuilder()
                .append("int_array_1", new int[] { 1 })
                .append("int_array_2", new int[] { 2 })
                .build(),
            equalTo("[int_array_1: { 1 }, int_array_2: { 2 }]"));
    }

    @Test
    public void appendIntArrayGivenMultipleElements() {
        assertThat(
            getToStringBuilder()
                .append("int_array_1", new int[] { 1, 2 })
                .append("int_array_2", new int[] { 3, 4 })
                .build(),
            equalTo("[int_array_1: { 1, 2 }, int_array_2: { 3, 4 }]"));
    }

    @Test
    public void appendLong() {
        assertThat(
            getToStringBuilder()
                .append("long_1", 1L)
                .append("long_2", 2L)
                .build(),
            equalTo("[long_1: 1L, long_2: 2L]"));
    }

    @Test
    public void appendLongArrayGivenNullReference() {
        assertThat(
            getToStringBuilder()
                .append("long_array_1", (long[]) null)
                .append("long_array_2", (long[]) null)
                .build(),
            equalTo("[long_array_1: <null>, long_array_2: <null>]"));
    }

    @Test
    public void appendLongArrayGivenEmptyArray() {
        assertThat(
            getToStringBuilder()
                .append("long_array_1", new long[] { })
                .append("long_array_2", new long[] { })
                .build(),
            equalTo("[long_array_1: { }, long_array_2: { }]"));
    }

    @Test
    public void appendLongArrayGivenOneElement() {
        assertThat(
            getToStringBuilder()
                .append("long_array_1", new long[] { 1L })
                .append("long_array_2", new long[] { 2L })
                .build(),
            equalTo("[long_array_1: { 1L }, long_array_2: { 2L }]"));
    }

    @Test
    public void appendLongArrayGivenMultipleElements() {
        assertThat(
            getToStringBuilder()
                .append("long_array_1", new long[] { 1L, 2L })
                .append("long_array_2", new long[] { 3L, 4L })
                .build(),
            equalTo("[long_array_1: { 1L, 2L }, long_array_2: { 3L, 4L }]"));
    }

    @Test
    public void appendObjectGivenNullValue() {
        assertThat(
            getToStringBuilder()
                .append("Object", (Object) null)
                .build(),
            equalTo("[Object: <null>]"));
    }

    @Test
    public void appendObjectGivenNonNullValue() {
        assertThat(
            getToStringBuilder()
                .append("String_1", new String("<string>"))
                .append("BigDecimal_1", new BigDecimal("1").setScale(1, RoundingMode.UNNECESSARY))
                .build(),
            equalTo("[String_1: \"<string>\", BigDecimal_1: [1.0]]"));
    }

    @Test
    public void appendObjectArrayGivenNullReference() {
        assertThat(
            getToStringBuilder()
                .append("object_array_1", (Object[]) null)
                .append("object_array_2", (Object[]) null)
                .build(),
            equalTo("[object_array_1: <null>, object_array_2: <null>]"));
    }

    @Test
    public void appendObjectArrayGivenEmptyArray() {
        assertThat(
            getToStringBuilder()
                .append("object_array_1", new Object[] { })
                .append("object_array_2", new Object[] { })
                .build(),
            equalTo("[object_array_1: { }, object_array_2: { }]"));
    }

    @Test
    public void appendObjectArrayGivenOneElement() {
        assertThat(
            getToStringBuilder()
                .append("object_array_1", new Object[] { new String("<string>") })
                .append("object_array_2", new Object[] { new BigDecimal(1).setScale(1, RoundingMode.UNNECESSARY) })
                .build(),
            equalTo("[object_array_1: { [<string>] }, object_array_2: { [1.0] }]"));
    }

    @Test
    public void appendObjectArrayGivenMultipleElements() {
        assertThat(
            getToStringBuilder()
                .append("object_array_1", new Object[]{ new String("<string>"), new BigDecimal(1).setScale(1, RoundingMode.UNNECESSARY)})
                .append("object_array_2", new Object[]{ new BigDecimal(2).setScale(1, RoundingMode.UNNECESSARY), new String("<string>")})
                .build(),
            equalTo("[object_array_1: { [<string>], [1.0] }, object_array_2: { [2.0], [<string>] }]"));
    }

    @Test
    public void appendShort() {
        assertThat(
            getToStringBuilder()
                .append("short_1", (short) 1)
                .append("short_2", (short) 2)
                .build(),
            equalTo("[short_1: 1, short_2: 2]"));
    }

    @Test
    public void appendShortArrayGivenNullReference() {
        assertThat(
            getToStringBuilder()
                .append("short_array_1", (short[]) null)
                .append("short_array_2", (short[]) null)
                .build(),
            equalTo("[short_array_1: <null>, short_array_2: <null>]"));
    }

    @Test
    public void appendShortArrayGivenEmptyArray() {
        assertThat(
            getToStringBuilder()
                .append("short_array_1", new short[] { })
                .append("short_array_2", new short[] { })
                .build(),
            equalTo("[short_array_1: { }, short_array_2: { }]"));
    }

    @Test
    public void appendShortArrayGivenOneElement() {
        assertThat(
            getToStringBuilder()
                .append("short_array_1", new short[] { 1 })
                .append("short_array_2", new short[] { 2 })
                .build(),
            equalTo("[short_array_1: { 1 }, short_array_2: { 2 }]"));
    }

    @Test
    public void appendShortArrayGivenMultipleElements() {
        assertThat(
            getToStringBuilder()
                .append("short_array_1", new short[] { 1, 2 })
                .append("short_array_2", new short[] { 3, 4 })
                .build(),
            equalTo("[short_array_1: { 1, 2 }, short_array_2: { 3, 4 }]"));
    }

    @Test
    public void appendStringGivenNullValue() {
        assertThat(
            getToStringBuilder()
                .append("String", (String) null)
                .build(),
            equalTo("[String: <null>]"));
    }

    @Test
    public void appendStringGivenNonNullValue() {
        assertThat(
            getToStringBuilder()
                .append("String_1", "<value1>")
                .append("String_2", "<value2>")
                .build(),
            equalTo("[String_1: \"<value1>\", String_2: \"<value2>\"]"));
    }

    @Test
    public void appendStringArrayGivenNullReference() {
        assertThat(
            getToStringBuilder()
                .append("string_array_1", (String[]) null)
                .append("string_array_2", (String[]) null)
                .build(),
            equalTo("[string_array_1: <null>, string_array_2: <null>]"));
    }

    @Test
    public void appendStringArrayGivenEmptyArray() {
        assertThat(
            getToStringBuilder()
                .append("string_array_1", new String[] { })
                .append("string_array_2", new String[] { })
                .build(),
            equalTo("[string_array_1: { }, string_array_2: { }]"));
    }

    @Test
    public void appendStringArrayGivenOneElement() {
        assertThat(
            getToStringBuilder()
                .append("string_array_1", new String[] { "<value1>" })
                .append("string_array_2", new String[] { "<value2>" })
                .build(),
            equalTo("[string_array_1: { \"<value1>\" }, string_array_2: { \"<value2>\" }]"));
    }

    @Test
    public void appendStringArrayGivenMultipleElements() {
        assertThat(
            getToStringBuilder()
                .append("string_array_1", new String[] { "<value1>", "<value2>" })
                .append("string_array_2", new String[] { "<value3>", "<value4>" })
                .build(),
            equalTo("[string_array_1: { \"<value1>\", \"<value2>\" }, string_array_2: { \"<value3>\", \"<value4>\" }]"));
    }
}