package com.resolvix.lib.lang;

import com.resolvix.lib.lang.exception.JarFileClassRetrievalException;
import com.resolvix.lib.reflect.Classes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarFileClassLoader
    extends ClassLoader
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Classes.class);

    private static final char DIRECTORY_SEPARATOR = '/';

    private static final char PACKAGE_SEPARATOR = '.';

    private static final char FILE_EXTENSION_SEPARATOR = '.';

    private static final String JAR_URL_PREFIX = "jar:";

    private static final String CLASS_FILE_EXTENSION = "class";

    private JarFile jarFile;

    public JarFileClassLoader(JarFile jarFile) {
        this.jarFile = jarFile;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String entryName = name.replace(PACKAGE_SEPARATOR, DIRECTORY_SEPARATOR)
                .concat(String.valueOf(FILE_EXTENSION_SEPARATOR))
                .concat(CLASS_FILE_EXTENSION);

        JarEntry jarEntry = jarFile.getJarEntry(entryName);
        if (jarEntry == null)
            throw new ClassNotFoundException(name);

        int size = (int) jarEntry.getSize();
        byte[] byteCode = new byte[size];

        try {
            InputStream inputStream = jarFile.getInputStream(jarEntry);
            int read = inputStream.read(byteCode, 0, size);
            if (read != size)
                throw new JarFileClassRetrievalException(
                        "Read operation yielded incomplete retrieval ("
                            + Integer.toString(read)
                            + " read, "
                            + Integer.toString(size)
                            + " class size).");
        } catch (IOException ioe) {
            throw new JarFileClassRetrievalException(
                    "JAR file read operation failure.", ioe);
        }

        return defineClass(name, byteCode, 0, size);
    }
}
