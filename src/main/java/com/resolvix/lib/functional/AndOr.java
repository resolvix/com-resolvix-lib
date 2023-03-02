package com.resolvix.lib.functional;

public class AndOr<A, B> {

    public static <A, B> AndOr<A, B> left(A a) {
        if (a == null)
            throw new IllegalArgumentException();

        return new AndOr<>(a, null);
    }

    public static <A, B> AndOr<A, B> right(B b) {
        if (b == null)
            throw new IllegalArgumentException();

        return new AndOr<>(null, b);
    }

    public static <A, B> AndOr<A, B> both(A a, B b) {
        if (a == null || b == null)
            throw new IllegalArgumentException();

        return new AndOr<>(a, b);
    }

    private A a;

    private B b;

    private AndOr(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public boolean hasLeft() {
        return (a != null);
    }

    public boolean hasOnlyLeft() {
        return (a != null && b == null);
    }

    public boolean hasRight() {
        return (b != null);
    }

    public boolean hasOnlyRight() {
        return (b != null && a == null);
    }

    public boolean hasBoth() {
        return (a != null && b != null);
    }

    public A left() {
        return a;
    }

    public B right() {
        return b;
    }
}

