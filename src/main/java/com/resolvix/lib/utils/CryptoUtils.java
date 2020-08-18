package com.resolvix.lib.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtils {

    private static final String MD5 = "MD5";

    private static final String SHA1 = "SHA-1";

    private static final String SHA256 = "SHA-256";

    private static final String SHA512 = "SHA-512";

    private CryptoUtils() {
        //
    }

    private static String toString(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (byte b : digest)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    private static String toDigest(byte[] bytes, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            return toString(messageDigest.digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String toMD5(byte[] bytes) {
        return toDigest(bytes, MD5);
    }

    public static String toSHA1(byte[] bytes) {
        return toDigest(bytes, SHA1);
    }

    public static String toSHA256(byte[] bytes) {
        return toDigest(bytes, SHA256);
    }

    public static String toSHA512(byte[] bytes) {
        return toDigest(bytes, SHA512);
    }
}
