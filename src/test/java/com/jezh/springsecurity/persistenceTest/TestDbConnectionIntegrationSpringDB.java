package com.jezh.springsecurity.persistenceTest;

import com.jezh.springsecurity.baseTest.BaseIntegrationRealDBConfigTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Enumeration;

//import static com.jezh.springsecurity.util.LoggerSample.log;
import static org.junit.Assert.assertNotNull;

public class TestDbConnectionIntegrationSpringDB extends BaseIntegrationRealDBConfigTest {

    private static final Logger log = LogManager.getLogger(com.jezh.springsecurity.util.LoggerSample.class);

// поскольку в BaseIntegrationRealDBConfigTest указано, что @ContextConfiguration(classes = DemoAppConfig.class), я мог
// бы заново не конфигурировать dataSource и проч., а просто взять готовые бины

// todo: однако, если я воспользуюсь готовым бином для dataSource из класса DemoAppConfig, он подтянет web context,
// а web context по каким-то рричинам не создается при тестированиии (его нужно мокать или стабить), поэтому выскочит
// соответствующая ошибка. Поэтому для BaseIntegrationRealDBConfigTest использую @ContextConfiguration(classes =
// DemoTestAppConfig.class), в котором не создаются бины, требующие web context (см. DemoTestAppConfig)
// todo: TOFIX: use mocking web context
//    @Autowired
//    private DataSource testDataSource;

    @Autowired
    private DataSource securityDataSource;


    @Autowired
    private Environment env;

    private String url, user, password, driver,
            initialPoolSize, minPoolSize, maxPoolSize, maxIdleTime;
    private String username, passwd, authority;
    private int enabled;
    private Connection conn;
    private Statement stat;
    private ResultSet usersRS, authoritiesRS;

//    @BeforeClass
//    public static void init() throws Exception {
//        log.trace("there are drivers enable: ");
//        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
//        while (driverEnumeration.hasMoreElements()) log.debug(driverEnumeration.nextElement());
//    }

    @Autowired
    ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
//        log.error("<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>> " + applicationContext.getStartupDate() +
//        applicationContext.containsBean("org.springframework.context.event.internalEventListenerFactory"));
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
        conn = securityDataSource.getConnection();
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
