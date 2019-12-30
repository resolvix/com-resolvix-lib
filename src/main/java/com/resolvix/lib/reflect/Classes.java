package com.resolvix.lib.reflect;

import com.resolvix.lib.lang.JarFileClassLoader;
import com.resolvix.lib.lang.exception.JarFileClassRetrievalException;
import com.resolvix.lib.reflect.exception.ClassRetrievalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Classes
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Classes.class);

    private static final char DIRECTORY_SEPARATOR = '/';

    private static final char PACKAGE_SEPARATOR = '.';

    private static final char FILE_EXTENSION_SEPARATOR = '.';

    private static final String JAR_URL_PREFIX = "jar:";

    private static final String CLASS_FILE_EXTENSION = "class";

    private static final int CLASS_FILE_EXTENSION_LENGTH = CLASS_FILE_EXTENSION.length();

    private static final String URL_PROTOCOL_FILE = "file";

    private static final String URL_PROTOCOL_JAR = "jar";

    private static String toRelativePath(String packageName) {
        return packageName.replace('.', '/');
    }

    private static Class<?> loadClass(ClassLoader classLoader, String packageName, String className)
        throws ClassNotFoundException {
        LOGGER.trace("loadClass: packageName '{}', className '{}'", packageName, className);
        return loadClass(classLoader, packageName + PACKAGE_SEPARATOR + className);
    }

    private static Class<?> loadClass(ClassLoader classLoader, String canonicalName)
        throws ClassNotFoundException {
        LOGGER.trace("loadClass: canonicalName '{}'", canonicalName);
        return classLoader.loadClass(canonicalName);
    }

    private static String getExtension(String fileName) {
        int index = fileName.indexOf(FILE_EXTENSION_SEPARATOR);
        if (index == -1)
            return null;

        return fileName.substring(index + 1);
    }

    /**
     *
     * @param classLoader
     * @param classes
     * @param packageName
     * @param resource
     */
    private static void processDirectoryResource(
            ClassLoader classLoader,
            List<Class<?>> classes,
            String packageName,
            File resource)
    {
        LOGGER.debug("Reading Directory '{}'", resource);

        //
        //  1.  Get the list of the files referenced by the resource.
        //
        String[] fileNames = resource.list();
        if (fileNames == null)
            throw new IllegalStateException();

        //
        //  2.  For each file referenced by the resource -
        //
        //      (a) if the file has a '.class' extension, load the class
        //          and append it to the list of classes; otherwise, treat
        //          it as a sub-resource and process it recursively if it
        //          is a directory.
        //
        for (String fileName: fileNames) {
            String extension = getExtension(fileName);
            if (extension == null) {
                File subresource = new File(resource, fileName);
                if (subresource.isDirectory()) {
                    processDirectoryResource(
                            classLoader,
                            classes,
                            packageName
                                + PACKAGE_SEPARATOR
                                + fileName,
                            subresource);
                }
            } else {
                switch (extension) {
                    case CLASS_FILE_EXTENSION:
                        File subresource = new File(resource, fileName);
                        if (subresource.isFile()) {
                            String className = fileName.substring(0, fileName.length() - CLASS_FILE_EXTENSION_LENGTH - 1);
                            try {
                                classes.add(
                                    loadClass(classLoader, packageName, className));
                            } catch (ClassNotFoundException e) {
                                LOGGER.debug(
                                    "processResource: unexpected 'ClassNotFoundException' raised "
                                        + "when attempting to load '{}.{}'",
                                    packageName, className);
                            }
                        }

                        break;

                    default:
                        break;
                }
            }
        }
    }

    private static String toJarPath(String resourcePath) {
        return resourcePath
            .replaceFirst("[.]jar[!].*", ".jar")
            .replaceFirst("file:", "");
    }

    /**
     * Enumerates, for a given JAR file, all of the classes contained in
     * that JAR file.
     *
     * @param jarFile the {@link File} object containing the name of the
     *  JAR file
     * @return packagePath the package for which the relevant classes are
     *  to be enumerated
     */
    public static List<Class<?>> enumerate(JarFile jarFile, String packagePath)
    {
        LOGGER.debug("processJarFileResource: reading JAR file {}.", jarFile.getName());

        JarFileClassLoader classLoader = new JarFileClassLoader(jarFile);

        //
        //  1.  Enumerate the entries in the JAR file.
        //
        Enumeration<JarEntry> jarEntries = jarFile.entries();

        //
        //  2.  Iterate through the entries and, where they refer to
        //      class files, load the class and append it to the list
        //      of classes.
        //
        List<Class<?>> classes = new ArrayList<>();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName();
            int i = entryName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
            if (i >= 0) {
                String fileExtension = entryName.substring(i + 1);
                switch (fileExtension) {
                    case CLASS_FILE_EXTENSION:
                        String packageClass = entryName.substring(0, i)
                                .replace(DIRECTORY_SEPARATOR, PACKAGE_SEPARATOR);
                        if (packageClass.startsWith(packagePath)) {
                            try {
                                Class<?> clazz = classLoader.loadClass(packageClass);
                                if (clazz == null) {
                                    LOGGER.debug("processJarFileResource: unexpected"
                                            + " failure to load {}.", packageClass);
                                    throw new ClassRetrievalException(
                                            new IllegalStateException());
                                }

                                classes.add(clazz);
                            } catch (ClassNotFoundException cnfe) {
                                LOGGER.debug("processJarFileResource: unexpected"
                                        + " 'ClassNotFoundException' on attempting to"
                                        + " load {}.", packageClass, cnfe);
                                throw new ClassRetrievalException(cnfe);
                            } catch (JarFileClassRetrievalException jfcre) {
                                LOGGER.debug("processJarFileResource: unexpected"
                                        + " 'JarFileClassRetrievalException' on "
                                        + " attempting to load {}.", packageClass, jfcre);
                                throw new ClassRetrievalException(jfcre);
                            }
                        }

                        break;
                }
            }
        }

        return classes;
    }

    private static File toFile(URL url) {
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Enumerates, for a given JAR file, all of the classes contained in
     * that JAR file.
     *
     * @param file the {@link File} object containing the name of the
     *  JAR file
     * @return the list of {@link Class} objects contained in the JAR
     *  file
     * @throws IOException
     */
    public static List<Class<?>> enumerate(File file, String packagePath)
            throws IOException {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);
            return enumerate(jarFile, packagePath);
        } finally {
            if (jarFile != null)
                jarFile.close();
        }
    }

    /**
     * Enumerates, for a given base package, all of the classes contained in
     * that base package that are available on the class path.
     *
     * @param basePackage the base package name
     * @return the list of {@link Class} objects contained in the package
     * @throws IOException
     */
    public static List<Class<?>> enumerate(String basePackage)
        throws IOException
    {
        ClassLoader classLoader = new ClassLoader() { };

        String relativePath = toRelativePath(basePackage);

        Enumeration<URL> urlEnumeration = classLoader.getResources(relativePath);

        List<Class<?>> classes = new ArrayList<>();
        while (urlEnumeration.hasMoreElements()) {
            URL url = urlEnumeration.nextElement();

            LOGGER.debug("enumerate: url '{}'", url.toString());

            File file = toFile(url);
            switch (url.getProtocol()) {
                case URL_PROTOCOL_FILE:
                    processDirectoryResource(
                            classLoader,
                            classes,
                            basePackage,
                            file);
                    break;

                case URL_PROTOCOL_JAR:
                    classes.addAll(
                            enumerate(file, basePackage));
                    break;
            }
        }

        return classes;
    }
}

