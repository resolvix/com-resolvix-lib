package com.resolvix.lib.pki;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

public class KeyStoreUtils {

    public static KeyStore fromInputStream(String keystoreType, InputStream inputStream, String password)
        throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException
    {
        KeyStore keyStore = KeyStore.getInstance(keystoreType);
        keyStore.load(inputStream, password.toCharArray());
        return keyStore;
    }

    public static KeyStore fromPath(String keystoreType, Path path, String password)
        throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException
    {
        InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ);
        return fromInputStream(keystoreType, inputStream, password);
    }

    public static KeyStore fromFile(String keystoreType, File file, String password)
        throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException
    {
        return fromPath(keystoreType, file.toPath(), password);
    }

    public static KeyStore fromResource(String keystoreType, String resourceName, String password)
        throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException
    {
        return fromInputStream(
            keystoreType,
            KeyStoreUtils.class.getClassLoader().getResourceAsStream(resourceName),
            password);
    }

    public static PrivateKey getPrivateKey(KeyStore keyStore, String alias, String password)
        throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException
    {
        KeyStore.ProtectionParameter passwordProtection
            = new KeyStore.PasswordProtection(password.toCharArray());
        KeyStore.Entry entry = keyStore.getEntry(alias, passwordProtection);
        return ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
    }
}
