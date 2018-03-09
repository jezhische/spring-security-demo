package com.jezh.springsecurity.config.appConfig;

import com.jezh.springsecurity.util.SmartInternalResourceViewResolver;
import com.jezh.springsecurity.util.TerminalViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import com.jezh.springsecurity.util.ChainableUrlBasedViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.ServletContext;

@Configuration
@EnableWebMvc
@ComponentScan("com.jezh.springsecurity")
// https://dzone.com/articles/spring-mvc-and-java-based-configuration-1: пробуем implement WebMvcConfigurer:
// "Defines callback methods to customize the Java-based configuration for Spring MVC enabled via @EnableWebMvc".
// todo: NB: extends WebMvcConfigurerAdapter - is deprecated in Spring 5, use implement WebMvcConfigurer instead of it!
public class DemoAppConfig implements WebMvcConfigurer {
// chaining view resolvers:    http://www.baeldung.com/spring-mvc-view-resolver-tutorial
// Spring InternalResourceViewResolver docs: " When chaining ViewResolvers, an InternalResourceViewResolver always needs
// to be last, as it will attempt to resolve any view name, no matter whether the underlying resource actually exists"
//    todo: hence, "InternalResourceViewResolver should have a higher order "

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

}
