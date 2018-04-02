package com.jezh.springsecurity.persistenceTest;

import com.jezh.springsecurity.baseTest.BaseIntegrationTestConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

//import static com.jezh.springsecurity.util.LoggerSample.log;

public class TestRealDbConnection extends BaseIntegrationTestConfig {

// поскольку в BaseIntegrationTestConfig указано, что @ContextConfiguration(classes = DemoAppConfig.class), я могу
// заново не конфигурировать dataSource и проч., а просто взять готовые бины. Однако, конфигурация DemoAppConfig
// подтянет за собой также и web context (для создания и размещения ViewResolver и т.п. бинов). Чтобы не получить ошибку,
// аннотируем BaseIntegrationTestConfig как @WebAppConfiguration, автовайрим веб-контекст, а затем создаем
// MockMvc объект от веб-контекста как точку входа в мок-тесты. NB: при этом почему-то
// todo: получаем servletContext instanceof MockServletContext - почему???

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

//    @Autowired
//    ApplicationContext applicationContext;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
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
