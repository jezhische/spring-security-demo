package com.jezh.springsecurity.test.plainNoSpringTest;

//import com.mchange.v2.c3p0.ComboPooledDataSource;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.*;
//import java.util.Enumeration;
//import java.util.Properties;
//
//import static com.jezh.springsecurity.util.LoggerSample.log;
//import static org.junit.Assert.*;

public class TestDbConnection {
//    private static Properties props;
//    private static String url, user, password, driver,
//    initialPoolSize, minPoolSize, maxPoolSize, maxIdleTime;
//    private String username, passwd, authority;
//    private int enabled;
//    private static ComboPooledDataSource dataSource;
//    private Connection conn;
//    private Statement stat;
//    private ResultSet usersRS, authoritiesRS;
//
//    @BeforeClass
//    public static void init() throws Exception {
//// ----------------------------------------------------------------------------------------------------------------
//        log.trace("there are drivers enable: ");
//        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
//        while (driverEnumeration.hasMoreElements()) log.debug(driverEnumeration.nextElement());
//// ----------------------------------------------------------------------------------------------------------------
//        props = new Properties();
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream("src/main/resources/persistence-mysql.properties");
//            props.load(fis);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        url = props.getProperty("jdbc.url");
//        user = props.getProperty("jdbc.user");
//        password = props.getProperty("jdbc.password");
//        driver = props.getProperty("jdbc.driver");
//        initialPoolSize = props.getProperty("connection.pool.initialPoolSize");
//        minPoolSize = props.getProperty("connection.pool.minPoolSize");
//        maxPoolSize = props.getProperty("connection.pool.maxPoolSize");
//        maxIdleTime = props.getProperty("connection.pool.maxIdleTime");
//
//        log.trace("properties from src/main/resources/persistence-mysql.properties: ");
//        log.info(String.format("url = %s, %n%-92s user = %s, %n%-92s password = %s, %n%-92s driver = %s, " +
//                        "%n%-92s initialPoolSize = %s, %n%-92s minPoolSize = %s, %n%-92s maxPoolSize = %s, " +
//                        "%n%-92s maxIdleTime = %s", url, " ", user, " ", password, " ", driver,
//                " ", initialPoolSize, " ", minPoolSize, " ", maxPoolSize, " ", maxIdleTime));
//// ----------------------------------------------------------------------------------------------------------------
//        dataSource = new ComboPooledDataSource();
//        dataSource.setDriverClass(props.getProperty("jdbc.driver"));
//        dataSource.setJdbcUrl(url); // database name
//        dataSource.setUser(user);
//        dataSource.setPassword(password);
////        dataSource.setMaxStatements(180);
////        dataSource.setMaxStatementsPerConnection(10);
//        dataSource.setMinPoolSize(Integer.parseInt(props.getProperty("connection.pool.minPoolSize")));
////        dataSource.setAcquireIncrement(1);
//        dataSource.setMaxPoolSize(Integer.parseInt(props.getProperty("connection.pool.maxPoolSize")));
//        dataSource.setMaxIdleTime(Integer.parseInt(props.getProperty("connection.pool.maxIdleTime")));
//        dataSource.setInitialPoolSize(Integer.parseInt(props.getProperty("connection.pool.initialPoolSize")));
//// ----------------------------------------------------------------------------------------------------------------
//    }
//
//    @Before
//    public void setUp() throws Exception {
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        if (conn != null) conn.close();
//    }
//
//    @Test
//    public void testPropertiesPresence() {
//        assertNotNull(url);
//        assertNotNull(user);
//        assertNotNull(password);
//        assertNotNull(driver);
//        assertNotNull(initialPoolSize);
//        assertNotNull(minPoolSize);
//        assertNotNull(maxPoolSize);
//        assertNotNull(maxIdleTime);
//    }
//
//    @Test
//    public void testDbConnection() throws Exception {
//        conn = DriverManager.getConnection(url, user, password);
//        log.warn("DriverManager.getConnection(url, user, password) conn.isValid = " + conn.isValid(0));
//    }
//
//    @Test
//    public void testDataSource() throws Exception {
//        conn = dataSource.getConnection();
//        log.warn("ComboPooledDataSource dataSource.getConnection conn.isValid = " + conn.isValid(0));
//        stat = conn.createStatement();
//        usersRS = stat.executeQuery("SELECT * FROM usersecurity.users");
//        log.error("users table content: ---------------------------------------------------------");
//        while (usersRS.next()) {
//            username = usersRS.getString("username");
//            passwd = usersRS.getString("password");
//            enabled = usersRS.getInt("enabled");
//            log.info(username + " : " + passwd + " : " + enabled);
//        }
//        log.error("authorities table content: ---------------------------------------------------------");
//        authoritiesRS = stat.executeQuery("SELECT * FROM usersecurity.authorities");
//        while (authoritiesRS.next()) {
//            username = authoritiesRS.getString("username");
//            authority = authoritiesRS.getString("authority");
//            log.info(username + " : " + authority);
//        }
//    }
}
