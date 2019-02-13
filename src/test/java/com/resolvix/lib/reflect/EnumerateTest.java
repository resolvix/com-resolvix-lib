package com.resolvix.lib.reflect;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import com.resolvix.lib.reflect.Enumerate;
import org.junit.Test;

import java.util.List;

public class EnumerateTest {

    private static final String TEST_PACKAGE = "com.resolvix.lib.reflect";

    @Test
    public void getClassesForPackage() {

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        //
        //  Package.getPackage is deprecated since JDK version 9; the
        //  preferred approach to obtaining a reference to a package
        //  is indirectly via a relevant {@link ClassLoader}, as in -
        //
        //      classLoader.getDefinedPackage();
        //
        Package pkg = classLoader.getDefinedPackage(TEST_PACKAGE);
        List<Class<?>> classes = Enumerate.getClassesForPackage(pkg);

        assertThat(
            classes,
            hasItem(equalTo(EnumerateTest.class)));
    }

}
