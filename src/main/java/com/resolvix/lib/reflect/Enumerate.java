package com.resolvix.lib.reflect;

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

public class Enumerate
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Enumerate.class);

    private static final char FILE_EXTENSION_SEPARATOR = '.';

    private static final char PACKAGE_SEPARATOR = '.';

    private static final String JAR_URL_PREFIX = "jar:";

    private static final String CLASS_FILE_EXTENSION = ".class";

    private static final int CLASS_FILE_EXTENSION_LENGTH = CLASS_FILE_EXTENSION.length();

    private static final String URL_PROTOCOL_FILE = "file";

    private static final String URL_PROTOCOL_JAR = "jar";

    private static String packageNameToRelativePath(String packageName) {
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

        return fileName.substring(index);
    }

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
                            String className = fileName.substring(0, fileName.length() - CLASS_FILE_EXTENSION_LENGTH);
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

    private static String resourcePathToJarPath(String resourcePath) {
        return resourcePath
            .replaceFirst("[.]jar[!].*", ".jar")
            .replaceFirst("file:", "");
    }

    private static void processJarEntry(
            ClassLoader classLoader,
            List<Class<?>> classes,
            String packagePath,
            JarEntry jarEntry)
    {
        String entryName = jarEntry.getName();
        String className = null;
        if (entryName.endsWith(CLASS_FILE_EXTENSION)) {
            if (entryName.startsWith(packagePath) && entryName.length() > (packagePath.length() + "/".length())) {
                className = entryName.replace('/', '.')
                    .replace('\\', '.')
                    .replace(CLASS_FILE_EXTENSION, "");
            }
        }

        LOGGER.trace("processJarFile: jarEntry '{}' => class '{}'", entryName, className);

        if (className != null) {
            try {
                classes.add(loadClass(classLoader, className));
            } catch (ClassNotFoundException e) {
                LOGGER.debug(
                    "processJarFile: unexpected 'ClassNotFoundException' raised "
                        + "when attempting to load '{}'",
                    className);
            }
        }
    }

    private static void processJarFileResource(
            ClassLoader classLoader,
            List<Class<?>> classes,
            String packageName,
            JarFile jarFile)
        throws IOException
    {
        LOGGER.trace("processJarFileResource: reading JAR file '{}'", jarFile.getName());

        //
        //  1.  Enumerate the entries within the jar file.
        //
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        String packagePath = packageNameToRelativePath(packageName);

        //
        //  2.  Iterate through the entries and, where they refer to
        //      class files, load the class and append it to the list
        //      of classes.
        //
        while(jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            processJarEntry(
                    classLoader,
                    classes,
                    packagePath,
                    jarEntry);
        }

        //
        //  3.  If the jar file was successfully opened, close it on
        //      processing completion.
        //
        jarFile.close();
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
     * @param jarFile the {@link File} object containing the name of
     *  the JAR file
     * @return the list of {@link Class} objects contained in the JAR
     *  file
     * @throws IOException
     */
    public static List<Class<?>> getClasses(File jarFile)
        throws IOException
    {
        List<Class<?>> classes = new ArrayList<>();

        return classes;
    }

    /**
     * Enumerates, for a given base package, all of the classes contained in
     * that base package.
     *
     * @param basePackage the base package name
     * @return the list of {@link Class} objects contained in the package
     * @throws IOException
     */
    public static List<Class<?>> getClasses(String basePackage)
        throws IOException
    {
        String relativePath = packageNameToRelativePath(basePackage);

        Enumeration<URL> urlEnumeration = ClassLoader.getSystemClassLoader().getResources(relativePath);

        ClassLoader classLoader = new ClassLoader() { };

        List<Class<?>> classes = new ArrayList<>();

        while (urlEnumeration.hasMoreElements()) {
            URL url = urlEnumeration.nextElement();

            LOGGER.debug("getClasses: url '{}'", url.toString());

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
                    JarFile jarFile = new JarFile(file);
                    processJarFileResource(
                            classLoader,
                            classes,
                            basePackage,
                            jarFile);
                    break;
            }
        }

        return classes;
    }
}

