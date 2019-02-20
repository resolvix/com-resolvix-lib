package com.resolvix.lib.reflect;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import com.resolvix.lib.reflect.jar.JarFileTest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public class EnumerateTest {

    private static final String TEST_PACKAGE = "com.resolvix.lib.reflect";

//    @Test
//    public void getClassesForPackage() throws IOException {
//
//        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//
//        //
//        //  Package.getPackage is deprecated since JDK version 9; the
//        //  preferred approach to obtaining a reference to a package
//        //  is indirectly via a relevant {@link ClassLoader}, as in -
//        //
//        //      classLoader.getDefinedPackage();
//        //
//        Package pkg = classLoader.getDefinedPackage(TEST_PACKAGE);
//        List<Class<?>> classes = Enumerate.getClassesForPackage(pkg);
//
//        assertThat(
//            classes,
//            hasItem(equalTo(EnumerateTest.class)));
//    }


    private static void createJarFile() throws Exception {

//        Manifest manifest = new Manifest();
//
//        JarFile jarFile = new JarFile("newJarFile.jar");


        //JarOutputStream jarOutputStream = new JarOutputStream(jarFile, manifest);


        FileOutputStream fileOutputStream = new FileOutputStream("temporaryJarFile.jar");

        Manifest manifest = new Manifest();
        Attributes mainAttributes = manifest.getMainAttributes();

        mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        mainAttributes.put(Attributes.Name.MAIN_CLASS, "");
        //mainAttributes.put("Created-By", "Resolvix");

        JarOutputStream jarOutputStream = new JarOutputStream(fileOutputStream, manifest);

        String jarFileTestPath = "com.resolvix.lib.reflect.jar.JarFileTest".replace('.', '/') + ".class";

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
        jarOutputStream.close();
    }

    private static void deleteJarFile() {

    }

    //@BeforeClass
    public static void beforeClass() throws Exception {

        // create a jar file containing a single class, with the package
        //  name given by TEST_PACKAGE

//        ClassLoader x = ClassLoader.getSystemClassLoader();
//
//        File manifestFile = new File("manifest.mf");
//        FileWriter fileWriter = new FileWriter(manifestFile);
//
//        //try {
//            if (manifestFile.exists() && manifestFile.isFile())
//                manifestFile.length();
//
//            manifestFile.createNewFile();
//            fileWriter.write("Manifest-Version: 1.0\n");
//            fileWriter.write("Main-Class: \n");
//            fileWriter.write("Created-By: \n");
//            fileWriter.close();
//
//        //} catch ()

        createJarFile();

    }

    //@AfterClass
    public static void afterClass() {




    }

    @Test
    public void getClasses() throws IOException {

        List<Class<?>> classes = Enumerate.getClasses(TEST_PACKAGE);

        assertThat(classes, containsInAnyOrder(
                equalTo(Enumerate.class),
                equalTo(EnumerateTest.class)));
    }
}
