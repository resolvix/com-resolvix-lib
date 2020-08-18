package com.resolvix.lib.utils;

import java.util.Base64;
import java.util.function.Supplier;

public class Base64Utils {

    public enum EncodingScheme {

        RFC4648_BASIC(Base64::getEncoder, Base64::getDecoder),

        RFC4648_URL_FILE_SAFE(Base64::getUrlEncoder, Base64::getUrlDecoder),

        RFC2045_MIME(Base64::getMimeEncoder, Base64::getMimeDecoder);

        private Supplier<Base64.Encoder> encoderSupplier;

        private Supplier<Base64.Decoder> decoderSupplier;

        EncodingScheme(
            Supplier<Base64.Encoder> encoderSupplier,
            Supplier<Base64.Decoder> decoderSupplier) {
            this.encoderSupplier = encoderSupplier;
            this.decoderSupplier = decoderSupplier;
        }

        private Base64.Encoder getEncoder() {
            return encoderSupplier.get();
        }

        private Base64.Decoder getDecoder() {
            return decoderSupplier.get();
        }
    }

    private Base64Utils() {
        //
    }

    public static byte[] toEncodedByteArray(byte[] bytes, EncodingScheme encodingScheme) {
        return encodingScheme.getEncoder().encode(bytes);
    }

    public static String toEncodedString(byte[] bytes, EncodingScheme encodingScheme) {
        return encodingScheme.getEncoder().encodeToString(bytes);
    }

    public static byte[] fromEncodedByteArray(byte[] bytes, EncodingScheme encodingScheme) {
        return encodingScheme.getDecoder().decode(bytes);
    }

    public static byte[] fromEncodedString(String string, EncodingScheme encodingScheme) {
        return encodingScheme.getDecoder().decode(string);
    }
}
