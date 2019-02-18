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

    private static final char PACKAGE_SEPARATOR = '.';

    private static final String JAR_URL_PREFIX = "jar:";

    private static final String CLASS_FILE_EXTENSION = ".class";

    private static final int CLASS_FILE_EXTENSION_LENGTH = CLASS_FILE_EXTENSION.length();

    private static String packageNameToRelativePath(String packageName) {
        return packageName.replace('.', '/');
    }

    private static Class<?> loadClass(String packageName, String className)
        throws ClassNotFoundException {
        LOGGER.trace("loadClass: packageName '{}', className '{}'", packageName, className);
        return loadClass(packageName + PACKAGE_SEPARATOR + className);
    }

    private static Class<?> loadClass(String canonicalName)
        throws ClassNotFoundException {
        LOGGER.trace("loadClass: canonicalName '{}'", canonicalName);
        return Class.forName(canonicalName);
        /*}
        catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "Unexpected ClassNotFoundException loading class '"
                    + canonicalName
                    + "'"
            );
        }*/
    }

    private static void processResource(
        List<Class<?>> classes,
        String packageName,
        File resource
    ) {
        LOGGER.debug("Reading Directory '{}'", resource);

        //
        //  1.  Get the list of the files referenced by the resource.
        //
        String[] files = resource.list();
        if (files == null)
            throw new IllegalStateException();

        //
        //  2.  For each file referenced by the resource -
        //
        for (String file: files) {
            String fileName = file;

            //
            //  (a) if the file has a '.class' extension, load the class
            //      and append it to the list of classes; otherwise, treat
            //      it as a sub-resource and process it recursively if it
            //      is a directory.
            //
            if (fileName.endsWith(CLASS_FILE_EXTENSION)) {
                String className = fileName.substring(0, fileName.length() - CLASS_FILE_EXTENSION_LENGTH);
                try {
                    classes.add(
                        loadClass(packageName, className));
                } catch (ClassNotFoundException e) {
                    LOGGER.debug("processResource: unexpected 'ClassNotFoundException' raised "
                        + "when attempting to load '{}.{}'", packageName, className);
                }
            } else {
                File subresource = new File(resource, fileName);
                if (subresource.isDirectory()) {
                    processResource(
                        classes,
                        packageName
                            + '.'
                            + fileName,
                        subresource
                    );
                }
            }
        }
    }

    private static String resourcePathToJarPath(String resourcePath) {
        return resourcePath
            .replaceFirst("[.]jar[!].*", ".jar")
            .replaceFirst("file:", "");
    }

    private static void processJarFile(
        URL resourceUrl,
        String packageName,
        List<Class<?>> classes
    ) throws IOException {
        String relativePath = packageNameToRelativePath(packageName);
        String jarPath = resourcePathToJarPath(resourceUrl.getPath());

        LOGGER.trace("processJarFile: reading JAR file '{}'", jarPath);

        JarFile jarFile = null;

        try {

            //
            //  1.  Open the jar file given by {@code jarPath}
            //
            jarFile = new JarFile(jarPath);

            //
            //  2.  Enumerate the entries within the jar file
            //
            Enumeration<JarEntry> jarEntries = jarFile.entries();

            //
            //  3.  Iterate through the entries and, where they refer to
            //      class files, load the class and append it to the list
            //      of classes
            //
            while(jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                //entry.getAttributes().
                String entryName = jarEntry.getName();
                String className = null;
                if (entryName.endsWith(CLASS_FILE_EXTENSION)) {
                    if (entryName.startsWith(relativePath) && entryName.length() > (relativePath.length() + "/".length())) {
                        className = entryName.replace('/', '.')
                            .replace('\\', '.')
                            .replace(CLASS_FILE_EXTENSION, "");
                    }
                }

                LOGGER.trace("processJarFile: jarEntry '{}' => class '{}'", entryName, className);

                if (className != null) {
                    try {
                        classes.add(loadClass(className));
                    } catch (ClassNotFoundException e) {
                        LOGGER.debug("processJarFile: unexpected 'ClassNotFoundException' raised "
                            + "when attempting to load '{}'", className);
                    }
                }
            }
        } finally {
            //
            //  4.  If the jar file was successfully opened, close it on
            //      processing completion.
            //
            if (jarFile != null)
                jarFile.close();
        }
    }

//    /**
//     *
//     * @param pkg
//     * @return
//     */
//    public static List<Class<?>> getClassesForPackage(Package pkg)
//        throws IOException
//    {
//        List<Class<?>> classes = new ArrayList<>();
//
//        String packageName = pkg.getName();
//        String relativePath = packageNameToRelativePath(packageName);
//
//        // Get a File object for the package
//        URL resourceUrl = ClassLoader.getSystemClassLoader().getResource(relativePath);
//        if (resourceUrl == null)
//            throw new RuntimeException("Unexpected problem: No resource for " + relativePath);
//
//        LOGGER.debug("Package: '{}' becomes Resource: '{}'", packageName, resourceUrl.toString());
//
//        if(resourceUrl.toString().startsWith(JAR_URL_PREFIX))
//            processJarFile(resourceUrl, packageName, classes);
//        else
//            processResource(
//                classes,
//                packageName,
//                new File(resourceUrl.getPath()));
//
//        return classes;
//    }

    /**
     * Enumerates, for a given base package, all of the classes contained by
     * the base package.
     *
     * @param basePackage the base package name
     *
     * @return a list of {@link Class} objects contained by package
     *
     * @throws IOException
     */
    public static List<Class<?>> getClasses(String basePackage)
        throws IOException
    {
        String relativePath = packageNameToRelativePath(basePackage);

        Enumeration<URL> urlEnumeration = ClassLoader.getSystemClassLoader().getResources(relativePath);

        List<Class<?>> classes = new ArrayList<>();

        while (urlEnumeration.hasMoreElements()) {
            URL url = urlEnumeration.nextElement();

            try {
                File resource = new File(url.toURI());
                processResource(
                    classes,
                    basePackage,
                    resource);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        return classes;
    }
}

