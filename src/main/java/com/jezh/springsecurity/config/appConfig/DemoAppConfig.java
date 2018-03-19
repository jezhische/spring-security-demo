package com.jezh.springsecurity.config.appConfig;

import com.jezh.springsecurity.util.SmartInternalResourceViewResolver;
import com.jezh.springsecurity.util.TerminalViewResolver;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import com.jezh.springsecurity.util.ChainableUrlBasedViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

import static com.jezh.springsecurity.util.LoggerSample.log;
@Configuration
@EnableWebMvc
@ComponentScan("com.jezh.springsecurity")
@PropertySource("classpath:persistence-mysql.properties")

// https://dzone.com/articles/spring-mvc-and-java-based-configuration-1: пробуем implement WebMvcConfigurer:
// "Defines callback methods to customize the Java-based configuration for Spring MVC enabled via @EnableWebMvc".
// todo: NB: extends WebMvcConfigurerAdapter - is deprecated in Spring 5, use implement WebMvcConfigurer instead of it!


public class DemoAppConfig implements WebMvcConfigurer /*extends WebMvcConfigurerAdapter*/ {


// chaining view resolvers:    http://www.baeldung.com/spring-mvc-view-resolver-tutorial
// Spring InternalResourceViewResolver docs: " When chaining ViewResolvers, an InternalResourceViewResolver always needs
// to be last, as it will attempt to resolve any view name, no matter whether the underlying resource actually exists"
//    todo: hence, "InternalResourceViewResolver should have a higher order "

//    private final static Logger log = LogManager.getLogger();


    @Autowired
    private Environment env; // hold the data that was read from the properties file

    @Bean
    public ViewResolver terminalViewResolver() {
        InternalResourceViewResolver viewResolver = new TerminalViewResolver();
        viewResolver.setPrefix("WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setOrder(1);
        return viewResolver;
    }

    // solution: https://blog.frankel.ch/chaining-url-view-resolvers-in-spring-mvc/
    @Bean
    public ViewResolver commonViewResolver() {
        UrlBasedViewResolver viewResolver = new ChainableUrlBasedViewResolver();
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setOrder(0);
        return viewResolver;
    }
// <mvc:resources mapping="/static/**" location="/static/" .......:/>
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//  "**": "...See AntPathMatcher for more details on the syntax."
//  E.g., если есть файл /static/html/logo.html или /static/html/test/logo.html, то запрос к нему будет: /static/logo.html-->
        registry.addResourceHandler("/static/**").addResourceLocations("/static/", "/static/html/",
                "/static/html/test/", "classpath:static/", "classpath:static/bootstrap3.3.7/css/");
    }

//    -------------------------------------------------------------------------------------- JDBC config

    @Bean
    public DataSource securityDataSource() {
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
        log.warn("jdbc.url = " + env.getProperty("jdbc.url"));
        log.warn("jdbc.user = " + env.getProperty("jdbc.user"));

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
