package com.resolvix.lib.utils;

import com.resolvix.lib.writer.StringBuilderWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

public class InputStreamUtils {

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private static final int EOF = -1;

    private InputStreamUtils() {
        //
    }

    private static void copy(final Reader inputStream, final Writer outputStream, char[] buffer)
        throws IOException
    {
        int bufferLength = buffer.length;
        int i;
        while ((i = inputStream.read(buffer, 0, bufferLength)) != EOF)
            outputStream.write(buffer, 0, i);
    }

    public static String toString(InputStream inputStream, Charset charset)
        throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        BufferedWriter bufferedWriter = new BufferedWriter(stringBuilderWriter);
        copy(bufferedReader, bufferedWriter, new char[DEFAULT_BUFFER_SIZE]);
        bufferedWriter.flush();
        bufferedWriter.close();
        return stringBuilderWriter.toString();
    }
}
