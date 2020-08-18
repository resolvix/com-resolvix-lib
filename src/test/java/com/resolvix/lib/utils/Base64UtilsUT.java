package com.resolvix.lib.utils;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class Base64UtilsUT {

    private String string = "This is a test";

    private byte[] byteArray = string.getBytes();

    //
    //  RFC4648_BASIC
    //

    @Test
    public void toEncodedFromEncodedByteArrayRFC4648_BASIC() {
        assertThat(
            Base64Utils.fromEncodedByteArray(
                Base64Utils.toEncodedByteArray(byteArray, Base64Utils.EncodingScheme.RFC4648_BASIC),
                Base64Utils.EncodingScheme.RFC4648_BASIC),
            equalTo(byteArray));
    }

    @Test
    public void toEncodedFromEncodedStringRFC4648_BASIC() {
        assertThat(
            Base64Utils.fromEncodedString(
                Base64Utils.toEncodedString(byteArray, Base64Utils.EncodingScheme.RFC4648_BASIC),
                Base64Utils.EncodingScheme.RFC4648_BASIC),
            equalTo(byteArray));
    }

    //
    //  RFC4648_URL_FILE_SAFE
    //

    @Test
    public void toEncodedFromEncodedByteArrayRFC4648_URL_FILE_SAFE() {
        assertThat(
            Base64Utils.fromEncodedByteArray(
                Base64Utils.toEncodedByteArray(byteArray, Base64Utils.EncodingScheme.RFC4648_URL_FILE_SAFE),
                Base64Utils.EncodingScheme.RFC4648_URL_FILE_SAFE),
            equalTo(byteArray));
    }

    @Test
    public void toEncodedFromEncodedStringRFC4648_URL_FILE_SAFE() {
        assertThat(
            Base64Utils.fromEncodedString(
                Base64Utils.toEncodedString(byteArray, Base64Utils.EncodingScheme.RFC4648_URL_FILE_SAFE),
                Base64Utils.EncodingScheme.RFC4648_URL_FILE_SAFE),
            equalTo(byteArray));
    }

    //
    //  RFC2045_MIME
    //

    @Test
    public void toEncodedFromEncodedByteArrayRFC2045_MIME() {
        assertThat(
            Base64Utils.fromEncodedByteArray(
                Base64Utils.toEncodedByteArray(byteArray, Base64Utils.EncodingScheme.RFC2045_MIME),
                Base64Utils.EncodingScheme.RFC2045_MIME),
            equalTo(byteArray));
    }

    @Test
    public void toEncodedFromEncodedStringRFC2045_MIME() {
        assertThat(
            Base64Utils.fromEncodedString(
                Base64Utils.toEncodedString(byteArray, Base64Utils.EncodingScheme.RFC2045_MIME),
                Base64Utils.EncodingScheme.RFC2045_MIME),
            equalTo(byteArray));
    }
}
