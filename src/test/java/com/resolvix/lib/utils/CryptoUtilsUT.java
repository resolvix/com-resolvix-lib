package com.resolvix.lib.utils;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.powermock.reflect.Whitebox;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CryptoUtilsUT {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void toMD5() {
        assertThat(
            CryptoUtils.toMD5("Hello World".getBytes()),
            equalTo("b10a8db164e0754105b7a99be72e3fe5"));
    }

    @Test
    public void toSHA1() {
        assertThat(
            CryptoUtils.toSHA1("Hello World".getBytes()),
            equalTo("0a4d55a8d778e5022fab701977c5d840bbc486d0"));
    }

    @Test
    public void toSHA256() {
        assertThat(
            CryptoUtils.toSHA256("Hello World".getBytes()),
            equalTo("a591a6d40bf420404a011733cfb7b190d62c65bf0bcda32b57b277d9ad9f146e"));
    }

    @Test
    public void toSHA512() {
        assertThat(
            CryptoUtils.toSHA512("Hello World".getBytes()),
            equalTo("2c74fd17edafd80e8447b0d46741ee243b7eb74dd2149a0ab1b9246fb30382f27e853d8585719e0e67cbda0daa8f51671064615d645ae27acb15bfb1447f459b"));
    }

    @Test @Ignore
    public void toDigest() throws Exception {
        expectedException.expect(IllegalStateException.class);
        Whitebox.invokeMethod(
            CryptoUtils.class, "toDigest",
            "Hello World".getBytes(), "XXX");
    }
}
