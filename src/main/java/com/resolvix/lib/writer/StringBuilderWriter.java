package com.resolvix.lib.writer;

import java.io.IOException;
import java.io.Writer;

public class StringBuilderWriter
    extends Writer {
    private StringBuilder stringBuilder;

    public StringBuilderWriter() {
        this.stringBuilder = new StringBuilder();
    }

    @Override
    public void write(char[] cbuf, int off, int len)
        throws IOException {
        stringBuilder.append(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        //
    }

    @Override
    public void close() throws IOException {
        //
    }

    public String toString() {
        return stringBuilder.toString();
    }
}
