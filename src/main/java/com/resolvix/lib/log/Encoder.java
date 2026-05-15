package com.resolvix.lib.log;

public class Encoder {

    private Encoder() { }

    public static Encoder getEncoder() {
        return new Encoder();
    }

    public String encode(String s) {
        return s;
    }
}
