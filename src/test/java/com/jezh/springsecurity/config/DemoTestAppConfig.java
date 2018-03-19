package com.jezh.springsecurity.config;

import com.jezh.springsecurity.util.ChainableUrlBasedViewResolver;
import com.jezh.springsecurity.util.TerminalViewResolver;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.jezh.springsecurity.util.LoggerSample.log;

@Configuration
// todo: to disable web context (ServletContext), I need to comment following annotations. Details by reference below.
// Either I get "2018-03-19 22:28:24,697 [main] WARN  org.springframework.context.support.AbstractApplicationContext.refresh:
// Exception encountered during context initialization - cancelling refresh attempt:
// org.springframework.beans.factory.BeanCreationException:
// Error creating bean with name 'resourceHandlerMapping' defined in class path resource
// [org/springframework/web/servlet/config/annotation/DelegatingWebMvcConfiguration.class]:
// Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException:
// Failed to instantiate [org.springframework.web.servlet.HandlerMapping]:
// Factory method 'resourceHandlerMapping' threw exception; nested exception is java.lang.IllegalStateException: No ServletContext set"

//@EnableWebMvc
//@ComponentScan("com.jezh.springsecurity")

@PropertySource("classpath:persistence-mysql.properties")
// http://forum.spring.io/forum/spring-projects/container/113785-missing-servlet-context-in-spring-tests
// "You are trying to instantatie web based beans outside of a web context for some beans this isn't going to work.
// If you want to test this you will need to fake (mock or stub) your web environment".
// ------------------------------------ and:
// @ComponentScan(basePackages = ...) on your dbconfig. Remove that line. You already have it in one of your other configs
// so its not needed and when you pull it in on your test case you are bringing in the web beans.
// ----------------- see also:
// https://jira.spring.io/browse/SPR-5243     and    https://jira.spring.io/browse/SPR-9799
public class DemoTestAppConfig {
    @Autowired
    private Environment env;
//    -------------------------------------------------------------------------------------- JDBC config

    @Bean
    public DataSource testDataSource() {
        // ------------------ create connection pool
        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

        // ------------------ set the jdbc driver
        try {
            securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e); // ???????????
        }
        // for sanity's sake log url and user just to make sure I'm reading the data
//        log.warn("jdbc.url = " + env.getProperty("jdbc.url"));
//        log.warn("jdbc.user = " + env.getProperty("jdbc.user"));

        // ------------------ set database connection props
        securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        securityDataSource.setUser(env.getProperty("jdbc.user"));
        securityDataSource.setPassword(env.getProperty("jdbc.password"));

        // ------------------ set connection pool properties
        String initialPoolSize = env.getProperty("connection.pool.initialPoolSize");
        String minPoolSize = env.getProperty("connection.pool.minPoolSize");
        String maxPoolSize = env.getProperty("connection.pool.maxPoolSize");
        String maxIdleTime = env.getProperty("connection.pool.maxIdleTime");
        if (initialPoolSize != null) {
            securityDataSource.setInitialPoolSize(Integer.parseInt(initialPoolSize));
        } else {
            log.error("missing connection.pool.initialPoolSize property");
            throw new RuntimeException();
        }
        if (minPoolSize != null) {
            securityDataSource.setMinPoolSize(Integer.parseInt(minPoolSize));
        } else {
            log.error("missing connection.pool.minPoolSize property");
            throw new RuntimeException();
        }
        if (maxPoolSize != null) {
            securityDataSource.setMaxPoolSize(Integer.parseInt(maxPoolSize));
        } else {
            log.error("missing connection.pool.maxPoolSize property");
            throw new RuntimeException();
        }
        if (maxIdleTime != null) {
            securityDataSource.setMaxIdleTime(Integer.parseInt(maxIdleTime));
        } else {
            log.error("missing connection.pool.maxIdleTime property");
            throw new RuntimeException();
        }
        return securityDataSource;
    }

}
