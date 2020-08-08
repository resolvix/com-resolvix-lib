package com.resolvix.lib.jndi;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.sql.DataSource;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class JndiUtilsUT {

    private static final Logger LOGGER = LoggerFactory.getLogger(JndiUtilsUT.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context rootContext = new InitialContext();
        Context compContext = rootContext.createSubcontext("comp");
        Context envContext = compContext.createSubcontext("env");
        envContext.bind("testValue", true);
        Context jdbcContext = envContext.createSubcontext("jdbc");
        LOGGER.debug("InitialContext = {}", rootContext);
    }

    @Before
    public void before() throws Exception {

    }

    @After
    public void after() throws Exception {

    }

    //
    //  getNamespace
    //

    @Test
    public void getNamespaceGivenJndiNamingEnvironmentOnly() throws Exception {
        String namespace = Whitebox.invokeMethod(
            JndiUtils.class, "getNamespace", "java:comp");
        assertThat(namespace, equalTo("java:comp"));
    }

    @Test
    public void getNamespaceGivenJndiNamingEnvironmentOnlyX() throws Exception {
        String namespace = Whitebox.invokeMethod(
            JndiUtils.class, "getNamespace", "java:comp/env");
        assertThat(namespace, equalTo("java:comp"));
    }

    @Test
    public void getNamespaceGivenFullyQualifiedJndiName() throws Exception {
        String namespace = Whitebox.invokeMethod(
            JndiUtils.class, "getNamespace", "java:comp/env/jdbc/TEST");
        assertThat(namespace, equalTo("java:comp/env/jdbc"));
    }

    @Test
    public void getNamespaceGivenRelativeJndiName() throws Exception {
        String namespace = Whitebox.invokeMethod(
            JndiUtils.class, "getNamespace", "jdbc/TEST");
        assertThat(namespace, equalTo("java:comp/env/jdbc"));
    }

    //
    //  getName
    //

    @Test
    public void getNameGivenFullyQualifiedJndiName() throws Exception {
        String name = Whitebox.invokeMethod(
            JndiUtils.class, "getName", "java:com/env/jdbc/TEST");
        assertThat(name, equalTo("TEST"));
    }

    @Test
    public void getNameGivenRelativeJndiNamespaceName() throws Exception {
        String name = Whitebox.invokeMethod(
            JndiUtils.class, "getName", "jdbc/TEST");
        assertThat(name, equalTo("TEST"));
    }

    @Test
    public void getNameGivenRelativeJndiName() throws Exception {
        String name = Whitebox.invokeMethod(
            JndiUtils.class, "getName", "TEST");
        assertThat(name, equalTo("TEST"));
    }

    //
    //  getContext
    //

    @Test
    public void getContextCompEnv() throws Exception {
        Context context = Whitebox.invokeMethod(
            JndiUtils.class, "getContext", "java:comp/env");
        assertThat(context, instanceOf(Context.class));
        assertThat(context.getNameInNamespace(), equalTo("comp.env"));
    }

    @Test
    public void getContextCompEnvJdbc() throws Exception {
        Context context = Whitebox.invokeMethod(
            JndiUtils.class, "getContext", "java:comp/env/jdbc");
        assertThat(context, instanceOf(Context.class));
        assertThat(context.getNameInNamespace(), equalTo("comp.env.jdbc"));
    }

    @Test
    public void getContextCompEnvJdbcFailure() throws Exception {
        expectedException.expect(NameNotFoundException.class);
        Context context = Whitebox.invokeMethod(
            JndiUtils.class, "getContext", "java:comp/env/jdbc/failure");
        assertThat(context, instanceOf(Context.class));
    }

    //
    //  bind
    //

    @Test
    public void bindCompEnvJdbcDataSource() throws Exception {
        DataSource dataSource = mock(DataSource.class);
        JndiUtils.bind("java:comp/env/jdbc/dataSource", dataSource);
        assertThat(
            JndiUtils.lookup("java:comp/env/jdbc/dataSource", DataSource.class),
            sameInstance(dataSource));
    }

    //
    //  lookup
    //

    @Test
    public void lookupCompEnvTestValue() throws Exception {
        assertThat(
            JndiUtils.lookup("java:comp/env/testValue", boolean.class),
            equalTo(true));
    }

    @Test
    public void lookupCompEnvNotPresentValue() throws Exception {
        expectedException.expect(NameNotFoundException.class);
        assertThat(
            JndiUtils.lookup("java:comp/env/notPresentValue", boolean.class),
            nullValue());
    }
}
