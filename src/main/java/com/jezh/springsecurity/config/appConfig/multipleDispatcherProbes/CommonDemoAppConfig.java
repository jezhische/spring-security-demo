package com.jezh.springsecurity.config.appConfig.multipleDispatcherProbes;

import com.jezh.springsecurity.util.viewResolvers.ChainableUrlBasedViewResolver;
import com.jezh.springsecurity.util.viewResolvers.TerminalViewResolver;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;

//import static com.jezh.springsecurity.util.LoggerSample.log;
//@Configuration
//@EnableWebMvc
//@ComponentScan("com.jezh.springsecurity")
//@PropertySource("classpath:persistence-mysql.properties")

public class CommonDemoAppConfig implements WebMvcConfigurer /*extends WebMvcConfigurerAdapter*/ {

    @Resource
    private Logger log;

    @Resource
    private Environment env; // hold the data that was read from the properties file

    @Bean
    public ViewResolver commonTerminalViewResolver() {
        InternalResourceViewResolver viewResolver = new TerminalViewResolver();
        viewResolver.setPrefix("WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setOrder(1);
        return viewResolver;
    }

    // solution: https://blog.frankel.ch/chaining-url-view-resolvers-in-spring-mvc/
    @Bean
    public ViewResolver commonCommonViewResolver() {
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
                "/static/html/test/", "classpath:static/", "classpath:static/bootstrap3.3.7/css/");
    }

}
