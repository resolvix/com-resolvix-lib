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
        LOGGER.debug("Reading Directory '" + directory + "'");

        //
        // Get the list of the files contained in the package
        //
        String[] files = directory.list();
        if (files == null)
            throw new IllegalStateException();

        for (int i = 0; i < files.length; i++) {
            String fileName = files[i];
            String className = null;

            //
            // we are only interested in .class files
            //
            if (fileName.endsWith(CLASS_FILE_EXTENSION)) {
                //
                // removes the .class extension
                //
                className = packageName
                    + '.'
                    + fileName.substring(0, fileName.length() - 6);
            }

            LOGGER.debug(
                "FileName '"
                    + fileName
                    + "'  =>  class '"
                    + className
                    + "'"
            );

            if (className != null)
                classes.add(loadClass(className));

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

    /**
     *
     * @param resourceUrl
     * @param packageName
     * @param classes
     */
    private static void processJarFile(
        URL resourceUrl,
        String packageName,
        List<Class<?>> classes
    ) {
        String relPath = packageName.replace('.', '/');
        String resPath = resourceUrl.getPath();
        String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");

        LOGGER.debug("Reading JAR file: '" + jarPath + "'");

        JarFile jarFile;

        try {
            jarFile = new JarFile(jarPath);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
        }

        Enumeration<JarEntry> jarEntries = jarFile.entries();

        while(jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            //entry.getAttributes().
            String entryName = jarEntry.getName();
            String className = null;
            if (entryName.endsWith(CLASS_FILE_EXTENSION)) {
                if (entryName.startsWith(relPath) && entryName.length() > (relPath.length() + "/".length())) {
                    className = entryName.replace('/', '.')
                        .replace('\\', '.')
                        .replace(CLASS_FILE_EXTENSION, "");
                }
            }

            LOGGER.debug("JarEntry '" + entryName + "'  =>  class '" + className + "'");

            if (className != null) {
                classes.add(loadClass(className));
            }
        }
    }

    /**
     *
     * @param pkg
     * @return
     */
    public static List<Class<?>> getClassesForPackage(
        Package pkg
    ) {
        List<Class<?>> classes = new ArrayList<>();

        String packageName = pkg.getName();
        String relPath = packageName.replace('.', '/');

        // Get a File object for the package
        URL resourceUrl = ClassLoader.getSystemClassLoader().getResource(relPath);
        if (resourceUrl == null) {
            throw new RuntimeException("Unexpected problem: No resource for " + relPath);
        }

        LOGGER.debug(
            "Package: '"
                + packageName
                + "' becomes Resource: '"
                + resourceUrl.toString()
                + "'"
        );

        resourceUrl.getPath();
        if(resourceUrl.toString().startsWith(JAR_URL_PREFIX)) {
            processJarFile(resourceUrl, packageName, classes);
        } else {
            processDirectory(new File(resourceUrl.getPath()), packageName, classes);
        }

        return classes;
    }
}

