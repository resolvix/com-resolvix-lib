package com.resolvix.lib.reflect;

import com.resolvix.lib.reflect.jar.JarFileTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class EnumerateTest {

    private static final String TEST_PACKAGE = "com.resolvix.lib.reflect.jar";

    private static void createJarFile() throws Exception {

        FileOutputStream fileOutputStream = new FileOutputStream("temporaryJarFile.jar");

        //
        //  1.  Create the manifest.
        //

        Manifest manifest = new Manifest();
        Attributes mainAttributes = manifest.getMainAttributes();
        mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        mainAttributes.put(Attributes.Name.MAIN_CLASS, "");

        //
        //  2.  Create the JAR output stream.
        //
        JarOutputStream jarOutputStream = new JarOutputStream(fileOutputStream, manifest);

        //
        //  3.  Add the class {@link JarFileTest} to the JAR output stream.
        //

        String jarFileTestPath = "com.resolvix.lib.reflect.jar.JarFileTest"
                .replace('.', '/')
                    + ".class";

        URL jarFileTestURL = ClassLoader.getSystemClassLoader().getResource(jarFileTestPath);
        File jarFileTest = new File(jarFileTestURL.toURI());
        FileInputStream fileInputStream = new FileInputStream(jarFileTest);
        jarOutputStream.putNextEntry(
            new ZipEntry(jarFileTestURL.getPath()));

        int readCount;

        byte[] buffer = new byte[4096];
        int bufferSize = buffer.length;

        while ((readCount = fileInputStream.read(buffer, 0, bufferSize)) >= 0) {
            if (readCount > 0) {
                jarOutputStream.write(buffer, 0, readCount);
            }
        }

        fileInputStream.close();

        //
        //  4.  Close the JAR output stream
        //

        jarOutputStream.close();
    }

    private static void deleteJarFile() throws Exception {
        Files.deleteIfExists(FileSystems.getDefault()
                .getPath("temporaryJarFile.jar"));
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        createJarFile();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        deleteJarFile();
    }

    @Test
    public void getClassesFromPackage() throws IOException {
        List<Class<?>> classes = Enumerate.getClasses(TEST_PACKAGE);
        assertThat(
                classes,
                containsInAnyOrder(
                    equalTo(JarFileTest.class)));
    }

    @Test
    public void getClassesFromFile() throws IOException {
        List<Class<?>> classes = Enumerate.getClasses(
                new File("temporaryJarFile.jar"));
        assertThat(
                classes,
                notNullValue());
    }
}
