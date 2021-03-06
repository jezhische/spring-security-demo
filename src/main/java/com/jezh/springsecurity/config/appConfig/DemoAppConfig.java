package com.jezh.springsecurity.config.appConfig;

import com.jezh.springsecurity.util.interceptors.TimeBasedAccessInterceptor;
import com.jezh.springsecurity.util.utils.Utils;
import com.jezh.springsecurity.util.viewResolvers.ChainableUrlBasedViewResolver;
import com.jezh.springsecurity.util.viewResolvers.TerminalViewResolver;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.Properties;

//import static com.jezh.springsecurity.util.LoggerSample.log;
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
    private Logger log;

    @Resource
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

//    ------------------------------------------------------------------------------------- ResourceHandlers config
// <mvc:resources mapping="/static/**" location="/static/" .......:/>
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//  "**": "...See AntPathMatcher for more details on the syntax."
//  E.g., если есть файл /static/html/logo.html или /static/html/test/logo.html, то запрос к нему будет: /static/logo.html-->
        registry.addResourceHandler("/static/**").addResourceLocations("/static/", "/static/html/",
                "/static/html/test/", "classpath:static/", "static/img/", "classpath:static/bootstrap3.3.7/css/");
    }

    //    -------------------------------------------------------------------------------------- unhandled requests config
//    https://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-configuration/
//    Этот метод, в принципе, не нужен, т.к. для Tomcat не нужно, чтобы the defaultServletName will need to be set explicitly
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
// DefaultServletHttpRequestHandler will try to autodetect the "default" Servlet name.
// DefaultServletHttpRequestHandler ...the last in the chain so that it will only execute when no other more specific
// mappings (i.e., to controllers) can be matched.
        configurer.enable();
    }

//    --------------------------------------------------------------------------------------- exceptionResolver config
//    https://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-configuration/
//    Здесь определяется, какую страницу показывать, если брошено исключение:
    @Bean
    public SimpleMappingExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put("net.petrikainulainen.spring.testmvc.todo.exception.TodoNotFoundException",
                "/WEB-INF/securityPgs/_404"); // handle with terminalViewResolver
        exceptionMappings.put("java.lang.Exception", "/WEB-INF/securityPgs/error/_500_error");
        exceptionMappings.put("java.lang.RuntimeException", "/WEB-INF/securityPgs/error/_500_error");
//        Set the mappings between exception class names and error view names:
        exceptionResolver.setExceptionMappings(exceptionMappings);

        exceptionResolver.setDefaultErrorView("WEB-INF/securityPgs/error/genericView");

        Properties statusCodes = new Properties();

        statusCodes.put("/WEB-INF/securityPgs/_404", "404");
        statusCodes.put("/WEB-INF/securityPgs/error/_500_error", "500");

        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }

//    -------------------------------------------------------------------------------------- interceptor config


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Пока что отконфигурирован так, чтобы перехват круглосуточно НЕ совершался
        registry.addInterceptor(new TimeBasedAccessInterceptor(0, 24));
    }


//    -------------------------------------------------------------------------------------- JDBC config

    @Bean
    public DataSource securityDataSource() {
        // ------------------ create connection pool
        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

        log.info("Start SecurityDataSource initialization--------------------------------------------");

        // ------------------ set the jdbc driver
        try {
            String jdbcDriver = env.getProperty("jdbc.driver");
            securityDataSource.setDriverClass(jdbcDriver);
            log.trace("jdbc: jdbc.driver is set to " + jdbcDriver);
        } catch (PropertyVetoException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e); // ???????????
        }
//        // for sanity's sake log url and user just to make sure I'm reading the data
//        log.warn("jdbc.url = " + env.getProperty("jdbc.url"));
//        log.warn("jdbc.user = " + env.getProperty("jdbc.user"));

        // ------------------ set database connection props
        String url = env.getProperty("jdbc.url");
        String user = env.getProperty("jdbc.user");
        String password = env.getProperty("jdbc.password");
        if (url != null && user != null && password != null) {
        securityDataSource.setJdbcUrl(url);
        securityDataSource.setUser(user);
        securityDataSource.setPassword(password);
            log.trace("jdbc: jdbc.url is set to " + url);
            log.trace("jdbc: jdbc.user is set to " + user);
            log.trace("jdbc: jdbc.password is set to " + password + " (secret hidden restricted forbidden info!)");

        } else {
            log.error(">>wrong db access credentials");
            throw new RuntimeException();
        }
// Вместо этой долгой писанины создал специальный метод в util
        // ------------------ set connection pool properties
//        String initialPoolSize = env.getProperty("connection.pool.initialPoolSize");
//        String minPoolSize = env.getProperty("connection.pool.minPoolSize");
//        String maxPoolSize = env.getProperty("connection.pool.maxPoolSize");
//        String maxIdleTime = env.getProperty("connection.pool.maxIdleTime");
//
//        if (initialPoolSize != null) {
//            try {
//                securityDataSource.setInitialPoolSize(Integer.parseInt(initialPoolSize));
//            } catch (NumberFormatException e) {
//                log.error(">>NumberFormatException thrown when parse initialPoolSize property");
//                throw new RuntimeException();
//            }
//        } else {
//            log.error(">>missing connection.pool.initialPoolSize property");
//            throw new RuntimeException();
//        }
//        if (minPoolSize != null) {
//            try {
//                securityDataSource.setMinPoolSize(Integer.parseInt(minPoolSize));
//            } catch (NumberFormatException e) {
//                log.error(">>NumberFormatException thrown when parse minPoolSize property");
//                throw new RuntimeException();
//            }
//        } else {
//            log.error(">>missing connection.pool.minPoolSize property");
//            throw new RuntimeException();
//        }
//        if (maxPoolSize != null) {
//            try {
//                securityDataSource.setMaxPoolSize(Integer.parseInt(maxPoolSize));
//            } catch (NumberFormatException e) {
//                log.error(">>NumberFormatException thrown when parse maxPoolSize property");
//                throw new RuntimeException();
//            }
//        } else {
//            log.error(">>missing connection.pool.maxPoolSize property");
//            throw new RuntimeException();
//        }
//        if (maxIdleTime != null) {
//            try {
//                securityDataSource.setMaxIdleTime(Integer.parseInt(maxIdleTime));
//            } catch (NumberFormatException e) {
//                log.error(">>NumberFormatException thrown when parse maxIdleTime property");
//                throw new RuntimeException();
//            }
//        } else {
//            log.error(">>missing connection.pool.maxIdleTime property");
//            throw new RuntimeException();
//        }

        // ------------------ set connection pool properties
        securityDataSource.setInitialPoolSize(Utils.parsePropsToInt(env, log, "connection.pool.initialPoolSize"));
        securityDataSource.setMinPoolSize(Utils.parsePropsToInt(env, log, "connection.pool.minPoolSize"));
        securityDataSource.setMaxPoolSize(Utils.parsePropsToInt(env, log, "connection.pool.maxPoolSize"));
        securityDataSource.setMaxIdleTime(Utils.parsePropsToInt(env, log, "connection.pool.maxIdleTime"));

        log.info("SecurityDataSource initialization finished--------------------------------------------");

        return securityDataSource;
    }

// --------------------------------------------------------------------- message source for validation registration form:
    // for VALIDATION I need the following pattern in the messages.properties: error type.model attribute name.field name
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        NB: not "i18/messages.properties"
        messageSource.addBasenames("i18/messages"); // vararg here, there may be multiple basenames with comma delimiter
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
