package com.jezh.springsecurity.persistenceTest;

import com.jezh.springsecurity.baseTest.BaseIntegrationRealDBConfigTest;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Enumeration;

import static com.jezh.springsecurity.util.LoggerSample.log;
import static org.junit.Assert.assertNotNull;

public class TestDbConnectionIntegrationSpringDB extends BaseIntegrationRealDBConfigTest {

// поскольку в BaseIntegrationRealDBConfigTest указано, что @ContextConfiguration(classes = DemoAppConfig.class), я могу
// заново не конфигурировать dataSource и проч., а просто взять готовые бины
    @Autowired
    private DataSource testDataSource;

    @Autowired
    private Environment env;

    private String url, user, password, driver,
            initialPoolSize, minPoolSize, maxPoolSize, maxIdleTime;
    private String username, passwd, authority;
    private int enabled;
    private Connection conn;
    private Statement stat;
    private ResultSet usersRS, authoritiesRS;

    @BeforeClass
    public static void init() throws Exception {
        log.trace("there are drivers enable: ");
        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
        while (driverEnumeration.hasMoreElements()) log.debug(driverEnumeration.nextElement());
    }

    @Before
    public void setUp() throws Exception {
        url = env.getProperty("jdbc.url");
        user = env.getProperty("jdbc.user");
        password = env.getProperty("jdbc.password");
        driver = env.getProperty("jdbc.driver");
        initialPoolSize = env.getProperty("connection.pool.initialPoolSize");
        minPoolSize = env.getProperty("connection.pool.minPoolSize");
        maxPoolSize = env.getProperty("connection.pool.maxPoolSize");
        maxIdleTime = env.getProperty("connection.pool.maxIdleTime");
    }

    @After
    public void tearDown() throws Exception {
        if (conn != null) conn.close();
    }

    @Test
    public void printProperties() {
        log.trace("properties from src/main/resources/persistence-mysql.properties: ");
        log.info(String.format("url = %s, %n%-92s user = %s, %n%-92s password = %s, %n%-92s driver = %s, " +
                        "%n%-92s initialPoolSize = %s, %n%-92s minPoolSize = %s, %n%-92s maxPoolSize = %s, " +
                        "%n%-92s maxIdleTime = %s", url, " ", user, " ", password, " ", driver,
                " ", initialPoolSize, " ", minPoolSize, " ", maxPoolSize, " ", maxIdleTime));
    }

    @Test
    public void testPropertiesPresence() {
        assertNotNull(url);
        assertNotNull(user);
        assertNotNull(password);
        assertNotNull(driver);
        assertNotNull(initialPoolSize);
        assertNotNull(minPoolSize);
        assertNotNull(maxPoolSize);
        assertNotNull(maxIdleTime);
    }

    @Test
    public void testDbConnection() throws Exception {
        conn = DriverManager.getConnection(url, user, password);
        log.warn("DriverManager.getConnection(url, user, password) conn.isValid = " + conn.isValid(0));
    }

    @Test
    public void testDataSource() throws Exception {
        conn = testDataSource.getConnection();
        log.warn("ComboPooledDataSource dataSource.getConnection conn.isValid = " + conn.isValid(0));
        stat = conn.createStatement();
        usersRS = stat.executeQuery("SELECT * FROM usersecurity.users");
        log.error("users table content: ---------------------------------------------------------");
        while (usersRS.next()) {
            username = usersRS.getString("username");
            passwd = usersRS.getString("password");
            enabled = usersRS.getInt("enabled");
            log.info(username + " : " + passwd + " : " + enabled);
        }
        log.error("authorities table content: ---------------------------------------------------------");
        authoritiesRS = stat.executeQuery("SELECT * FROM usersecurity.authorities");
        while (authoritiesRS.next()) {
            username = authoritiesRS.getString("username");
            authority = authoritiesRS.getString("authority");
            log.info(username + " : " + authority);
        }
    }
}
