package com.resolvix.lib.jndi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JndiUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JndiUtils.class);

    private static final String JAVA_NAMESPACE_PREFIX = "java:";

    private static final String JDNI_COMPONENT_ENVIRONMENT_NAMESPACE = "java:comp/env";

    private static final char JAVA_SEPARATOR = ':';

    private static final char CONTEXT_SEPARATOR = '/';

    private JndiUtils() {
        //
    }

    private static String getNamespace(String name) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (name.startsWith(JAVA_NAMESPACE_PREFIX)) {
            i = name.indexOf(CONTEXT_SEPARATOR);
            if (i >= 0) {
                sb.append(name.substring(0, i));
                i++;
            } else {
                sb.append(name);
            }
        } else {
            sb.append(JDNI_COMPONENT_ENVIRONMENT_NAMESPACE);
        }

        while (i >= 0 && i < name.length()) {
            int j = name.indexOf(CONTEXT_SEPARATOR, i);
            if (j > i) {
                sb.append(CONTEXT_SEPARATOR);
                sb.append(name.substring(i, j));
                i = ++j;
            } else break;
        }

        return sb.toString();
    }

    private static String getName(String name) {
        int i = name.lastIndexOf(CONTEXT_SEPARATOR);
        if (i >= 0)
            return name.substring(i + 1);
        return name;
    }

    private static Context getContext(String namespace) throws NamingException {
        Context context = new InitialContext();
        LOGGER.debug("InitialContext = {}", context);
        int i = 0, j = 0;
        i = namespace.indexOf(JAVA_SEPARATOR);
        j = namespace.indexOf(CONTEXT_SEPARATOR, i);
        while (j > i) {
            String tmp = namespace.substring(i + 1, j);
            context = (Context) context.lookup(tmp);
            i = j;
            j = namespace.indexOf(CONTEXT_SEPARATOR, i + 1);
        }

        if (i < namespace.length()) {
            String tmp = namespace.substring(i + 1);
            context = (Context) context.lookup(tmp);
        }

        return context;
    }

    public static <T> void bind(String compoundName, T t) throws NamingException {
        String namespace = getNamespace(compoundName);
        Context context = getContext(namespace);
        if (context != null) {
            String name = getName(compoundName);
            context.bind(name, t);
        }
    }

    public static <T> T lookup(String compoundName, Class<T> classT) throws NamingException {
        String namespace = getNamespace(compoundName);
        Context context = getContext(namespace);
        assert (context != null);
        String name = getName(compoundName);
        return (T) context.lookup(name);
    }
}
