package com.resolvix.lib.utils;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class EnumUtilsUT {

    private enum SampleEnum {

        SAMPLE_ENUM_VALUE;
    }

    @Test
    public void safeNameWhenEnumIsNull() {
        assertThat(EnumUtils.safeName(null), nullValue());
    }

    @Test
    public void safeNameWhenEnumIsNotNull() {
        assertThat(EnumUtils.safeName(
            SampleEnum.SAMPLE_ENUM_VALUE),
            equalTo("SAMPLE_ENUM_VALUE"));
    }

    @Test
    public void safeOrdinalWhenEnumIsNull() {
        assertThat(EnumUtils.safeOrdinal(null), nullValue());
    }

    @Test
    public void safeOrdinalWhenEnumIsNotNull() {
        assertThat(EnumUtils.safeOrdinal(
            SampleEnum.SAMPLE_ENUM_VALUE),
            equalTo(0));
    }

    private static String getName(SampleEnum sampleEnum) {
        return sampleEnum.name();
    }

    @Test
    public void safeMapWhenEnumIsNull() {
        assertThat(EnumUtils.safeMap(null, EnumUtilsUT::getName), nullValue());
    }

    @Test
    public void safeMapWhenEnumIsNotNull() {
        assertThat(EnumUtils.safeMap(
            SampleEnum.SAMPLE_ENUM_VALUE, EnumUtilsUT::getName),
            equalTo("SAMPLE_ENUM_VALUE"));
    }
}
