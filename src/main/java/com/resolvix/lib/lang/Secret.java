package com.resolvix.lib.lang;

import java.nio.CharBuffer;
import java.util.Objects;

public class Secret
    implements CharSequence
{
    private CharBuffer secret;

    private Secret(CharBuffer secret) {
        this.secret = secret;
    }

    public static Secret empty() {
        return new Secret(
            CharBuffer.wrap(new char[0]));
    }

    public static Secret of(CharBuffer secret) {
        return new Secret(secret);
    }

    public static Secret of(String string) {
        return new Secret(
            CharBuffer.wrap(string.toCharArray()));
    }

    public static Secret of(char[] chars) {
        return new Secret(
            CharBuffer.wrap(chars));
    }

    public static void wipe(Secret... secrets) {
        if (secrets == null)
            return;

        for (Secret secret : secrets)
            if (secret != null)
                secret.wipe();
    }

    @Override
    public char charAt(int index) {
        return secret.charAt(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Secret secret1 = (Secret) o;
        return Objects.equals(secret, secret1.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secret);
    }

    @Override
    public int length() {
        return secret.length();
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return new Secret(
            secret.subSequence(start, end));
    }

    public void wipe() {
        for (int i = 0; i < secret.length(); i++)
            secret.put(i, '\0');
    }
}
