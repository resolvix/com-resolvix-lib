package com.resolvix.lib.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Enumerate
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Enumerate.class);

    private static final String JAR_URL_PREFIX = "jar:";

    private static final String CLASS_FILE_EXTENSION = ".class";

    private static final int CLASS_FILE_EXTENSION_LENGTH = CLASS_FILE_EXTENSION.length();

    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "Unexpected ClassNotFoundException loading class '"
                    + className
                    + "'"
            );
        }
    }

    private static void processDirectory(
        File directory,
        String packageName,
        List<Class<?>> classes
    ) {
        LOGGER.debug("Reading Directory '{}'", directory);

        //
        // Get the list of the files contained in the package
        //
        String[] files = directory.list();
        if (files == null)
            throw new IllegalStateException();

        for (int i = 0; i < files.length; i++) {
            String fileName = files[i];

            //
            //  Ignore files that do not have a .class extension: we are only
            //  interested in Java class files.
            //
            if (fileName.endsWith(CLASS_FILE_EXTENSION)) {

                //
                //  Remove the .class extension.
                //
                String className = packageName
                    + '.'
                    + fileName.substring(0, fileName.length() - CLASS_FILE_EXTENSION_LENGTH);

                LOGGER.debug("FileName '{}' => class '{}'", fileName, className);

                classes.add(loadClass(className));
            } else {

                File subDirectory = new File(directory, fileName);
                if (subDirectory.isDirectory()) {
                    processDirectory(
                        subDirectory,
                        packageName
                            + '.'
                            + fileName,
                        classes
                    );
                }
            }
        }
    }

    private static String packageNameToRelativePath(String packageName) {
        return packageName.replace('.', '/');
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

        LOGGER.debug("Reading JAR file: '{}'", jarPath);

        JarFile jarFile = null;

        try {

            //
            //  1.  Open the jar file given by {@code jarPath};
            //
            jarFile = new JarFile(jarPath);

            //
            //  2.  Enumerate the entries within the jar file;
            //
            Enumeration<JarEntry> jarEntries = jarFile.entries();

            //
            //  3.  Iterate through the entries and, where they refer to
            //      class files, load the class and append it to the list
            //      of classes;
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

                LOGGER.debug("JarEntry '{}' => class '{}'", entryName, className);

                if (className != null) {
                    classes.add(loadClass(className));
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

    /**
     *
     * @param pkg
     * @return
     */
    public static List<Class<?>> getClassesForPackage(Package pkg)
        throws IOException
    {
        List<Class<?>> classes = new ArrayList<>();

        String packageName = pkg.getName();
        String relPath = packageName.replace('.', '/');

        // Get a File object for the package
        URL resourceUrl = ClassLoader.getSystemClassLoader().getResource(relPath);
        if (resourceUrl == null)
            throw new RuntimeException("Unexpected problem: No resource for " + relPath);

        LOGGER.debug("Package: '{}' becomes Resource: '{}'", packageName, resourceUrl.toString());

        if(resourceUrl.toString().startsWith(JAR_URL_PREFIX))
            processJarFile(resourceUrl, packageName, classes);
        else
            processDirectory(new File(resourceUrl.getPath()), packageName, classes);

        return classes;
    }


    public static List<Class<?>> enumerate(String basePackage)
        throws IOException
    {
        String relativePath = basePackage.replace('.', '/');

        Enumeration<URL> urlEnumeration = ClassLoader.getSystemClassLoader().getResources(relativePath);

        List<Class<?>> classes = new ArrayList<>();

        while (urlEnumeration.hasMoreElements()) {
            URL url = urlEnumeration.nextElement();
            processDirectory(new File(url.getPath()), basePackage, classes);
        }

        return classes;
    }
}

