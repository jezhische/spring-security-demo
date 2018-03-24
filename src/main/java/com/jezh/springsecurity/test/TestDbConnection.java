package com.jezh.springsecurity.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;

//import static com.jezh.springsecurity.util.LoggerSample.log;

public class TestDbConnection {

    private final static Logger log = LogManager.getLogger(com.jezh.springsecurity.util.LoggerSample.class);

    public static void main(String[] args) throws IOException, SQLException, PropertyVetoException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/persistence-mysql.properties");
        props.load(fis);
// -------------------------------------------------- check if there are the necessary drivers
        log.warn(DriverManager.getDriver(props.getProperty("jdbc.url")));
        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
        while (driverEnumeration.hasMoreElements()) log.error(driverEnumeration.nextElement());
// -------------------------------------------------- check if I can get db connection
        String url = props.getProperty("jdbc.url"),
                user = props.getProperty("jdbc.user"),
                password = props.getProperty("jdbc.password");
        Connection conn = DriverManager.getConnection(url, user, password);
        log.warn("DriverManager.getConnection(url, user, password) conn.isValid = " + conn.isValid(0));
        conn.close();
        log.error("conn.isClosed = " + conn.isClosed());
// -------------------------------------------------- check if I can get db data with necessary data source
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(props.getProperty("jdbc.driver"));
        dataSource.setJdbcUrl(url); // database name
        dataSource.setUser(user); // table name
        dataSource.setPassword(password);
//        dataSource.setMaxStatements(180);
//        dataSource.setMaxStatementsPerConnection(10);
        dataSource.setMinPoolSize(Integer.parseInt(props.getProperty("connection.pool.minPoolSize")));
//        dataSource.setAcquireIncrement(1);
        dataSource.setMaxPoolSize(Integer.parseInt(props.getProperty("connection.pool.maxPoolSize")));
        dataSource.setMaxIdleTime(Integer.parseInt(props.getProperty("connection.pool.maxIdleTime")));
        dataSource.setInitialPoolSize(Integer.parseInt(props.getProperty("connection.pool.initialPoolSize")));

        conn = dataSource.getConnection();
        log.warn("ComboPooledDataSource dataSource.getConnection conn.isValid = " + conn.isValid(0));
        Statement stat = conn.createStatement();
        String username = null, passwd = null, authority = null;
        int enabled = 0;
        ResultSet usersRS = stat.executeQuery("SELECT * FROM usersecurity.users");
        log.error("users table content: ---------------------------------------------------------");
        while (usersRS.next()) {
            username = usersRS.getString("username");
            passwd = usersRS.getString("password");
            enabled = usersRS.getInt("enabled");
            log.info(username + " : " + passwd + " : " + enabled);
        }
        log.error("authorities table content: ---------------------------------------------------------");
        ResultSet authoritiesRS = stat.executeQuery("SELECT * FROM usersecurity.authorities");
        while (authoritiesRS.next()) {
            username = authoritiesRS.getString("username");
            authority = authoritiesRS.getString("authority");
            log.info(username + " : " + authority);
        }

        conn.close();
        log.debug("conn.isClosed = " + conn.isClosed());

// --------------------------------------------------
// --------------------------------------------------
// --------------------------------------------------
// --------------------------------------------------
// --------------------------------------------------
    }
}
